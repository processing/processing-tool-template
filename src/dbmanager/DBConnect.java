package dbmanager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.derby.drda.NetworkServerControl;

public class DBConnect {
	Connection conn;
	private static NetworkServerControl server;

	public static void inicServer() {
		try {

			server = new NetworkServerControl();
			server.start(null);

			System.out.println("Server is starting at 'localhost:1527'");
			server.ping(); // if ping fails throws an exception
			System.out.println("Derby Network Server now running");
			// String mySysInfo = server.getSysinfo();
			// System.out.println("\bSysInfo\n" + mySysInfo);

			// String myRuntimeInfo = server.getRuntimeInfo();
			// System.out.println("\bRunTimeInfo\n" + myRuntimeInfo);

			// System.out.println("\bProperties\n");
			// Properties p = server.getCurrentProperties();
			// p.list(System.out);
			// System.out.println("derby.drda.host =" +
			// p.getProperty("derby.drda.host"));

		} catch (Exception f) {
			f.printStackTrace();
		}
	}

	// Open our system database. If it doesn't exists it is recreated.
	public Connection inicConnect(String cadConnect) {

		try {
			// Now it reads the Processing DB System Definition database.
			conn = DriverManager.getConnection(cadConnect);
		} catch (SQLException ex) {
			try {
				conn = DriverManager.getConnection(cadConnect + ";create=true");
				System.out.println("SysTable creation in the catch");
				createSysTable(cadConnect);
				return conn;
			} catch (Exception ey) {
				ey.printStackTrace();
				return null;
			}
		}

		try {
			// Check that DBSYS_LIST table exists
			conn.setAutoCommit(false);
			DatabaseMetaData dmd = conn.getMetaData();
			ResultSet rs = dmd.getTables(null, "APP", "DBSYS_LIST", null);
			if (!rs.next()) {
				System.out.println("SysTable creation when reswasnull" + toString());
				createSysTable(cadConnect);
				return conn;
			} else {

				System.out.println("SysTable exists detected." + rs.toString());
			}

		} catch (SQLException ex) {
			System.out.println("SysTable detection failed.");
			ex.printStackTrace();
			return null;
		}

		return conn;

	}

	public void createSysTable(String cadConnect) {

		System.out.println("Entering createSysTable.");

		try {
			if (conn == null) {
				conn = DriverManager.getConnection(cadConnect);
			}

			System.out.println("SCHEMA: " + conn.getSchema());

			// Recreate initial system table
			DBManager.stmt = conn.createStatement();
			// This creates a System DB master table containing a list of
			// the tables managed by DBManager.

			System.out.println("SYS Table creation.");

			String query = "CREATE TABLE DBSYS_LIST (DBNAME VARCHAR(30) NOT NULL,"
					+ "	USERD VARCHAR(30) NOT NULL,	PWD VARCHAR(30),"
					+ "	DESCRIPTION VARCHAR(255), FILEPATH VARCHAR(255) NOT NULL)";

			int result = DBManager.stmt.executeUpdate(query);
			System.out.println("Query Create Table: " + query + " Resultado: " + result);

			query = " CREATE UNIQUE INDEX INDEX1 ON DBSYS_LIST(DBNAME ASC)";

			result = DBManager.stmt.executeUpdate(query);
			System.out.println("Query Create Index: " + query + " Resultado: " + result);

			query = "ALTER TABLE DBSYS_LIST ADD CONSTRAINT INDEX1 PRIMARY KEY(DBNAME)";

			result = DBManager.stmt.executeUpdate(query);
			System.out.println("Query ALTER TABLE: " + query + " Resultado: " + result);

			String absolutePathToSys = DBManager.pathToSettings + "\\PROCESSYS_DBMANAGER";
			query = "INSERT INTO DBSYS_LIST VALUES ('PROCESSYS_DBMANAGER', '', '', 'Contains the list of databases.', "
					+ "'" + absolutePathToSys + "')";
			result = DBManager.stmt.executeUpdate(query);
			System.out.println("Query INSERT DATA: " + query + " Resultado: " + result);


			
			// stmt.executeUpdate(query);

			// Reading SQL Files

			// ArrayList<String> queries;
			// ... get queries from sql file .................
			/*
			 * String pathname = DBManager.pathToSettings +
			 * "\\CreateDBSYS_LIST.sql";
			 * 
			 * SQLReader sqlread = new SQLReader(); queries =
			 * sqlread.createQueries(pathname);
			 * 
			 * int[] RSList = new int[queries.size()]; // for (String query :
			 * queries_01) { for (int i = 0; i < queries.size(); i++) { String
			 * query = queries.get(i); RSList[i] = (stmt.executeUpdate(query));
			 * System.out.println("Query " + i + ": " + query + " Resultado: " +
			 * RSList[i]); }
			 * 
			 */

		/*	try {
				ResultSet tmpRS = RSList_01.get(0);
				if (!tmpRS.isClosed()) {
					if (tmpRS.next())
						System.out.println("Query 1: id = " + tmpRS.getInt("id"));
				}

			} catch (SQLException e) { // TODO Auto-generated catch block
				e.printStackTrace();
			}
*/
		} catch (Exception ey) {
			System.out.println("Error when table creation.");
			ey.printStackTrace();
		}

	}

}
