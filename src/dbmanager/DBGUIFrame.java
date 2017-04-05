package dbmanager;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

import org.apache.derby.drda.NetworkServerControl;

public class DBGUIFrame extends JFrame {

	private JPanel contentPane;
	private JTree DBtree;
	private NetworkServerControl server;

	/**
	 * Create the frame.
	 */
	public DBGUIFrame() {

		// To shutting down an unique database from your application
		// DriverManager.getConnection( "jdbc:derby:sample;shutdown=true");

		/*
		 * Connection dbConnection =
		 * DriverManager.getConnection("jdbc:derby:MyDb;create=true");
		 * DatabaseMetaData dbmd = dbConnection.getMetaData(); ResultSet rs =
		 * dbmd.getTables(null, "MYSCHEMA", "MYTABLE", null); if(!rs.next()) {
		 * createMyTable(); }
		 */
		jbInit();
	}

	private void jbInit() {
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 685, 413);
		setTitle("Java DB Manager");

		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu mnServer = new JMenu("Server");
		menuBar.add(mnServer);

		JMenuItem mntmStartServer = new JMenuItem("Start Server");
		mnServer.add(mntmStartServer);
		mntmStartServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				DBConnect.inicServer();

			}

		});

		JMenuItem mntmStopServer = new JMenuItem("Stop Server");
		mnServer.add(mntmStopServer);
		mntmStopServer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				try {
					server.shutdown();
					System.out.println("Server is stopping.");
				} catch (Exception f) {
					f.printStackTrace();
				}
			}

		});

		JMenuItem mntmSettings = new JMenuItem("Settings...");
		mnServer.add(mntmSettings);
		mntmSettings.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DBSettingsDialog dialog = new DBSettingsDialog();

					// Parameters window that user must agree
					DBManager.javaDBInstall = System.getenv("DERBY_INSTALL");
					dialog.getTxtInstallation().setText(DBManager.javaDBInstall);

					String propLocate = DBManager.propsDB.getDBProperty("DBs_location");
					dialog.getTxtLocation().setText(propLocate);

					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);

					if (dialog != null) {

						// TxtInstallation will be ok if the connection works.

					}

				} catch (Exception f) {
					f.printStackTrace();
				}
			}

		});

		JMenuItem mntmExit = new JMenuItem("Exit");
		mntmExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				setVisible(false);
				dispose();
			}

		});

		JMenu mnDatabase = new JMenu("Database");
		menuBar.add(mnDatabase);

		JMenuItem mntmCreateDatabase = new JMenuItem("Create Database...");
		mnDatabase.add(mntmCreateDatabase);
		mntmCreateDatabase.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					DBCreationDialog dialog = new DBCreationDialog();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.getTxtDBLocation().setText(DBManager.defaultPathToDBs);
					dialog.setLocationRelativeTo(null);
					dialog.setVisible(true);

				} catch (Exception f) {
					f.printStackTrace();
				}
			}

		});
		mntmExit.setHorizontalAlignment(SwingConstants.LEFT);
		menuBar.add(mntmExit);

		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JTree DBtree = new JTree();
		DBtree.setModel(DBManager.dataModel);
		contentPane.add(DBtree, BorderLayout.WEST);

		JTextArea textArea = new JTextArea();
		contentPane.add(textArea, BorderLayout.SOUTH);
	}

	public static DefaultMutableTreeNode processHierarchy(Object[] hierarchy) {
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

}
