/**
 * Manages Derby server.
 *
 * ##copyright##
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General
 * Public License along with this library; if not, write to the
 * Free Software Foundation, Inc., 59 Temple Place, Suite 330,
 * Boston, MA  02111-1307  USA
 *
 * @author   ##author##
 * @modified ##date##
 * @version  ##tool.prettyVersion##
 */

package dbmanager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;

import processing.app.Base;
import processing.app.tools.Tool;

// when creating a tool, the name of the main class which implements Tool must
// be the same as the value defined for project.name in your build.properties

public class DBManager implements Tool {
	private final static Logger LOGGER = Logger.getLogger(DBManager.class.getName());
	Base base;
	DBGUIFrame frame;
	public static String javaDBInstall;
	public static String pathToSettings;
	public static DBProperties propsDB; // Propiedades de la DB
	public static Statement stmt;
	public static ArrayList<String> DBList;
	public static TreeModel dataModel;
	public static String pathToSketchbook;
	public static String defaultPathToDBs;
	private Connection con;

	// Preferences: Server active when initiates DBManager?
	boolean ActivateServerAtManagerStart;

	@Override
	public String getMenuTitle() {
		return "##tool.name##";
	}

	@Override
	public void init(Base base) {

		// Store a reference to the Processing application itself
		this.base = base;

	}

	@Override
	public void run() {
		LOGGER.entering(getClass().getName(), "doIt");

		Base.locateSketchbookFolder();
		String pathToSketchbook = Base.getSketchbookFolder().getAbsolutePath();
		System.out.println("pathToSketchbook = " + pathToSketchbook);
		defaultPathToDBs = new String(pathToSketchbook);

		processing.app.ui.Editor editor = base.getActiveEditor();
		// editor.setText("Deleted your code. What now?");
		System.out.println("editor.getsketch: " + editor.getSketch().getMainFilePath());

		if (frame == null) {

			LOGGER.log(Level.SEVERE, "Error entering if (frame)");

			System.out.println("Frame creation");
			// System.getProperties().list(System.out);

			try {
				pathToSettings = Base.getSettingsFolder().getAbsolutePath();
				System.out.println("pathToSettings: " + pathToSettings);
				propsDB = new DBProperties();

				// Reads in the DBMANAGER system database the initial data such
				// as
				// the list of databases managed. Connect in embedded mode.
				Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

				String pathToDBManager = "jdbc:derby:" + pathToSettings + "\\PROCESSYS_DBMANAGER";

				System.out.println("pathToDBManager: " + pathToDBManager);

				Connection con = new DBConnect().inicConnect(pathToDBManager);
				stmt = con.createStatement();

				// Reads the tables data and builds a list for the JTree

				DBList = new ArrayList<String>();
				DBList.add("Databases");
				String sql = "SELECT * from DBSYS_LIST";
				ResultSet rs = stmt.executeQuery(sql);
				while (rs.next()) {
					String value[] = { rs.getString("DBNAME") };
					DBList.add(value[0]);
				}

				Object hierarchy[] = DBList.toArray();
				DefaultMutableTreeNode root = processHierarchy(hierarchy);
				dataModel = new DefaultTreeModel(root);

				// DBList.toArray();

				frame = new DBGUIFrame();
				frame.setLocationRelativeTo(null);

			} catch (ClassNotFoundException ex) {
				System.out.println("Class not found " + ex);

			} catch (SQLException ex) {
				System.out.println("SQL Exception " + ex);
			} finally {
				// finally block used to close resources
				try {
					if (con != null)
						con.close();
				} catch (SQLException se) {
					se.printStackTrace();
				} // end finally try
			} // end try
		}
		frame.setVisible(true);

	}

	private DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
		DefaultMutableTreeNode node = new DefaultMutableTreeNode(hierarchy[0]);
		DefaultMutableTreeNode child;
		for (int i = 1; i < hierarchy.length; i++) {
			Object nodeSpecifier = hierarchy[i];
			if (nodeSpecifier instanceof Object[]) // Ie node with children
			{
				child = processHierarchy((Object[]) nodeSpecifier);
			} else {
				child = new DefaultMutableTreeNode(nodeSpecifier); // Ie Leaf
			}
			node.add(child);
		}
		return (node);
	}

	// Get the currently active Editor to run the Tool on it
	// Editor editor = base.getActiveEditor();
	// editor.setText("Deleted your code. What now?");

	// Fill in author.name, author.url, tool.prettyVersion and
	// project.prettyName in build.properties for them to be auto-replaced here.
	// System.out.println("Hello Tool. ##tool.name## ##tool.prettyVersion## by
	// ##author##");
}