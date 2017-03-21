package dbmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Enumeration;
import java.util.Properties;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

import org.apache.derby.drda.NetworkServerControl;

import processing.app.Base;

public class DBGUIFrame extends JFrame {

	private JPanel contentPane;
	private NetworkServerControl server;
	private Connection con;
	
	/**
	 * Create the frame.
	 */
	public DBGUIFrame() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 685, 413);
		setTitle("Java DB Manager");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnServer = new JMenu("Server");
		menuBar.add(mnServer);

		JMenu mnConnect = new JMenu("Connect");
		mnServer.add(mnConnect);

		JMenuItem mntmStartServer = new JMenuItem("Start Server");
		mntmStartServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				
				
				//Parameters window that user must agree
				try {
					DBSettingsDialog dialog = new DBSettingsDialog();
					
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}

				try {

					server = new NetworkServerControl();
					server.start(null);

					System.out.println("Server is starting at 'localhost:1527'");
					server.ping(); // if ping fails throws an exception
					System.out.println("Derby Network Server now running");
					String mySysInfo = server.getSysinfo();
					System.out.println("\bSysInfo\n" + mySysInfo);

					String myRuntimeInfo = server.getRuntimeInfo();
					System.out.println("\bRunTimeInfo\n" + myRuntimeInfo);

					System.out.println("\bProperties\n");
					Properties p = server.getCurrentProperties();
					p.list(System.out);
					System.out.println("derby.drda.host" + p.getProperty("derby.drda.host"));

					// Display databases list
					listDBs();

					// Display drivers
					printDrivers();
					/*
					 * // Properties System.out.println("Properties");
					 * NetworkServerControl server = new NetworkServerControl();
					 * Properties p = server.getCurrentProperties();
					 * p.list(System.out);
					 * System.out.println(p.getProperty("derby.drda.host"));
					 * 
					 * // Sysinfo System.out.println("Sysinfo\n" +
					 * server.getSysinfo());
					 * 
					 * System.out.println("RuntimeInfo\n" +
					 * server.getRuntimeInfo());
					 */
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});

		mnConnect.add(mntmStartServer);

		JMenuItem mntmStopServer = new JMenuItem("Stop Server");
		mntmStopServer.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					server.shutdown();
					System.out.println("Server is stopping.");
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mnConnect.add(mntmStopServer);

		JMenuItem mntmCreateDatabase = new JMenuItem("Create Database...");
		mntmCreateDatabase.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DBCreationDialog dialog = new DBCreationDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mnConnect.add(mntmCreateDatabase);

		JMenuItem mntmSettings = new JMenuItem("Settings...");
		mntmSettings.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					DBSettingsDialog dialog = new DBSettingsDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);
				} catch (Exception f) {
					f.printStackTrace();
				}
			}
		});
		mnConnect.add(mntmSettings);

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}
		});
		mntmExit.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mntmExit);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTree DBtree = new JTree();
		DBtree.setModel(new DefaultTreeModel(new DefaultMutableTreeNode("Databases") {
			{

			}
		}));
		DBtree.setEnabled(true);
		DBtree.setEditable(false);
		DBtree.setShowsRootHandles(true);
		DBtree.setRootVisible(true);
		contentPane.add(DBtree, BorderLayout.WEST);

		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.SOUTH);
	}

	protected void listDBs() {
		// TODO Auto-generated method stub

		// We create a PROCESSYS_DBList database that serves as our Processing
		// System Database
		// to maintain a list of databases.

		String pathToData = Base.getSketchbookFolder().getAbsolutePath();
		System.out.println("base1sketchPath: " + pathToData);

		// It is needed a class derived from PApplet to access Processing stuff.
		DBInterProc getPath = new DBInterProc();
		pathToData = getPath.getSketchPath();

		System.setProperty("derby.system.home", pathToData);
		System.out.println("current dataPath: " + System.getProperty("derby.system.home"));

		// Connecting to the Processing System database. If that does not
		// exists, we recreate
		// an initial empty database. Once started the system, the user can
		// change the data directory
		// to locate the correct one, or can recover a backup copy if there is
		// one.

		try {
			// con = DriverManager.getConnection("jdbc:derby:test;create=true");
			// // Connecting as embedded database.
			con = DriverManager.getConnection("jdbc:derby://localhost:1527/data;create=true"); // Connecting
																								// as
																								// server.
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void printDrivers() {
		// Lists all drivers registered with the server
		Enumeration<Driver> enumDrivers = java.sql.DriverManager.getDrivers();
		while (enumDrivers.hasMoreElements()) {

			Driver driver = enumDrivers.nextElement();

			System.out.println("JDBC Driver=" + driver.getClass().getName());

		}
	}
}
