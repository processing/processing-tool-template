package dbmanager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

import com.oracle.webservices.internal.api.message.PropertySet.Property;

import processing.app.Base;

public class DBProperties {

	Properties defaultProps, appProps;
	boolean saveDefProps, saveAppProps;
	String defPropsFileName, appPropsFileName;
	OutputStream output;
	InputStream input;
	String pathToSettings;
	String javaDB_install;
	String DBs_location;

	public DBProperties() {
		super();
		// TODO Auto-generated constructor stub

		defaultProps = null; // path to default props file
		appProps = null; // path to modified props file
		saveDefProps = false; // Are they
		saveAppProps = false; // changed?
		defPropsFileName = "dbdefault.properties";
		appPropsFileName = "dbmanager.properties";
		output = null;
		input = null;
		pathToSettings = "";
		javaDB_install = "";
		DBs_location = "";

		readProperties();

	}

	public void readProperties() {

		try {

			pathToSettings = Base.getSettingsFolder().getAbsolutePath();
			// System.out.println("pathToSettings: " + pathToSettings);

			// Reading properties, the defaults and the last modified by
			// application
			input = new FileInputStream(pathToSettings + defPropsFileName);
			defaultProps.load(input);

			appProps = new Properties(defaultProps); // Copy def props into app
														// props
			input = new FileInputStream(pathToSettings + appPropsFileName);
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
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public void saveProperties() {

		if (saveDefProps || saveAppProps) {
			try {
				pathToSettings = Base.getSettingsFolder().getAbsolutePath();

				if (saveAppProps) {
					output = new FileOutputStream(pathToSettings + appPropsFileName);
					appProps.store(output, "---Current Properties of DB Manager---");
				}

				if (saveDefProps) {
					output = new FileOutputStream(pathToSettings + defPropsFileName);
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
						e.printStackTrace();
					}
				}
			}
		}
	}

	public String getDBProperty(String key) {
		return appProps.getProperty(key);
	}

	public void setDBProperty(String key, String value) {
		appProps.setProperty(key, value);
		saveAppProps = true;
	}

	public String searchAnywhere(String key) {
		String value;

		value = appProps.getProperty(key);

		if (value == null || value == "") {
			switch (key) {
			case "javaDB_install":
				javaDB_install = System.getProperty("derby_install");
				if (javaDB_install == null || javaDB_install == "") {
					throw new IllegalArgumentException("Failed property search: " + key);
				}
				break;
			case "DBs_location":
				DBs_location = System.getProperty("derby_home");
				if (DBs_location == null || DBs_location == "") {
					DBs_location = System.getProperty("derby.system.home");
				}

				if (DBs_location == null || DBs_location == "") {
					throw new IllegalArgumentException("Failed property search: " + key);
				}
				break;

			default:
				throw new IllegalArgumentException("Failed property search: " + key);
			}
		}

		return value;
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