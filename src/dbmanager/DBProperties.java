package dbmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class DBProperties {

	Properties defaultProps, appProps;
	boolean saveDefProps, saveAppProps;
	String defPropsFileName, appPropsFileName;
	OutputStream output;
	InputStream input;
	String DBs_location;

	public DBProperties() {
		super();
		// TODO Auto-generated constructor stub

		defaultProps = new Properties();
		appProps = new Properties();
		saveDefProps = false; // Are they
		saveAppProps = false; // changed?
		defPropsFileName = "dbdefault.properties"; // path to default props file
		appPropsFileName = "dbmanager.properties"; // path to modified props
													// file
		output = null;
		input = null;
		DBs_location = "";

		readProperties();

	}

	public void readProperties() {

		try {

			// Reading properties, the defaults and the last modified by
			// application
			input = new FileInputStream(DBManager.pathToSettings + "\\" + defPropsFileName);
			defaultProps.load(input);

			appProps = new Properties(defaultProps); // Copy def props into app
														// props
			input = new FileInputStream(DBManager.pathToSettings + "\\" + appPropsFileName);
			appProps.load(input);
			input.close();

			/*
			 * Databases general location
			 * 
			 * 
			 * /* System.out.println(prop.getProperty("database"));
			 * System.out.println(prop.getProperty("dbuser"));
			 * System.out.println(prop.getProperty("dbpassword"));
			 * 
			 * System.setProperty("derby.system.home", sketchPath("test"));
			 * 
			 * Base.locateSketchbookFolder();
			 * Base.getSketchbookFolder().getAbsolutePath();
			 * System.out.println("sketchPath: " + pathToSketch);
			 * 
			 * DBInterProc getPath = new DBInterProc(); pathToSketch =
			 * getPath.getSketchPath("data"); System.out.println("sketchPath: "
			 * + pathToSketch);
			 */

		} catch (IOException ex) {
			saveDefProps = true;
			saveAppProps = true;
			saveProperties();

		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					// e.printStackTrace();
				}
			}
		}
	}

	public void saveProperties() {

		if (saveDefProps || saveAppProps) {
			try {
				if (saveAppProps) {
					output = new FileOutputStream(DBManager.pathToSettings + "\\" + appPropsFileName);
					appProps.store(output, "---Current Properties of DB Manager---");
				}

				if (saveDefProps) {
					output = new FileOutputStream(DBManager.pathToSettings + "\\" + defPropsFileName);
					defaultProps.store(output, "---Default Properties of DB Manager---");
				}

				output.close();

				/*
				 * prop.setProperty("database", "localhost");
				 * prop.setProperty("dbuser", "mkyong");
				 * prop.setProperty("dbpassword", "password");
				 */

			} catch (IOException io) {
				io.printStackTrace();
			} finally {
				if (output != null) {
					try {
						output.close();
					} catch (IOException e) {
						// e.printStackTrace();
					}
				}
			}
		}

		saveDefProps = false;
		saveAppProps = false;

	}

	public String getDBProperty(String key) {
		String value = "";

		value = appProps.getProperty(key);

		if (value == null || value.isEmpty()) {
			switch (key) {
			case "DBs_location":
				value = System.getProperty("derby.system.home");

				if (value == null || value.isEmpty()) {
					value = System.getenv("derby_home");
				}
				if (value == null || value.isEmpty()) {
					value = System.getProperty("user.dir");
				}

				if (value == null || value.isEmpty()) {
					System.out.println("Failed property search: " + key);
					value = "";
					DBs_location = value;
				} else {
					DBs_location = value;
					appProps.setProperty("DBs_location", DBs_location);
					defaultProps.setProperty("DBs_location", DBs_location);
					saveAppProps = true;
					saveDefProps = true;
					saveProperties();
				}
				break;

			default:
				value = "";
			}
		}

		return value;
	}

	public void setDBProperty(String key, String value) {
		appProps.setProperty(key, value);
		saveAppProps = true;
	}

	public void removeProp(String key) {
		appProps.remove(key);
		saveAppProps = true;
		defaultProps.remove(key);
		saveDefProps = true;
	}

	/*
	 * Base.locateSketchbookFolder(); String pathToSketch =
	 * Base.getSketchbookFolder().getAbsolutePath();
	 * System.out.println("sketchPath: " + pathToSketch);
	 * 
	 * DBInterProc getPath = new DBInterProc(); pathToSketch =
	 * getPath.getSketchPath("data"); System.out.println("sketchPath: " +
	 * pathToSketch);
	 */
}