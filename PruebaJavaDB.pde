import java.sql.Statement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.DriverManager;
import org.apache.derby.drda.NetworkServerControl;

Connection con;
void setup() {
  try {
    // Tell derby where to put the database files
    NetworkServerControl server = new NetworkServerControl();
    server.start (null);
    System.setProperty("derby.system.home", sketchPath("test"));

    // tell java which database-driver to use
    // Class.forName("org.apache.derby.jdbc.EmbeddedDriver"); // DB integrada en la aplicación
    Class.forName("org.apache.derby.jdbc.ClientDriver");      // Aplicación cliente con DB en servidor.

    // create a database named test
    // con = DriverManager.getConnection("jdbc:derby:test;create=true"); // DB integrada en la aplicación.
    con = DriverManager.getConnection("jdbc:derby://localhost:1527/test;create=true"); // Aplicación cliente con DB en servidor.
    
    Statement stmt = con.createStatement();
    println("Inicio");
    try {
        // drop the foo table if it exists
        stmt.execute("drop table foo");
    } catch (Exception e ) {
    }
    // create a table named foo and insert two rows
    stmt.execute("create table foo ( id integer not null, name varchar(20))");
    stmt.executeUpdate("insert into foo values (0, 'fooclient')");
    stmt.executeUpdate("insert into foo values (1, 'barclient')");
  } 
  catch (Exception e ) {
    e.printStackTrace();
  }
  println("Fin");
  noLoop();
}

void draw() {
  
  println("Inicdraw");
  try {
    // select all rows from the foo table
    Statement stmt = con.createStatement();
    ResultSet rs = stmt.executeQuery("select * from foo");
    while( rs.next()) {
      // print the content of the "name" column on the console
      println(rs.getString("name"));
    }
  } 
  catch( Exception e ) {
    e.printStackTrace();
  }
  
  println("fin draw");
}