package dbmanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

@SuppressWarnings("serial")
public class DBCreationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDBName;
	private JTextField txtNbuser;
	private JTextField txtPassword;
	private JTextField txtDescription;
	private JTextField txtDBLocation;

	/**
	 * Create the dialog.
	 */
	public DBCreationDialog() {
		jbInit();
	}

	private void jbInit() {
		setModal(true);
		setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		setTitle("Java DB Database Creation");
		setBounds(100, 100, 599, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 150, 100, 100, 0 };
		gbl_contentPanel.rowHeights = new int[] { 26, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 1.0, 1.0, 0.0 };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblDatabaseName = new JLabel("Database Name: ");
			GridBagConstraints gbc_lblDatabaseName = new GridBagConstraints();
			gbc_lblDatabaseName.anchor = GridBagConstraints.EAST;
			gbc_lblDatabaseName.insets = new Insets(0, 0, 5, 5);
			gbc_lblDatabaseName.gridx = 0;
			gbc_lblDatabaseName.gridy = 1;
			contentPanel.add(lblDatabaseName, gbc_lblDatabaseName);
		}
		{
			txtDBName = new JTextField();
			GridBagConstraints gbc_txtDBName = new GridBagConstraints();
			gbc_txtDBName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDBName.gridwidth = 2;
			gbc_txtDBName.insets = new Insets(0, 0, 5, 0);
			gbc_txtDBName.anchor = GridBagConstraints.NORTHWEST;
			gbc_txtDBName.gridx = 1;
			gbc_txtDBName.gridy = 1;
			contentPanel.add(txtDBName, gbc_txtDBName);
			txtDBName.setColumns(10);
		}
		{
			JLabel lblUserName = new JLabel("User Name: ");
			GridBagConstraints gbc_lblUserName = new GridBagConstraints();
			gbc_lblUserName.anchor = GridBagConstraints.EAST;
			gbc_lblUserName.insets = new Insets(0, 0, 5, 5);
			gbc_lblUserName.gridx = 0;
			gbc_lblUserName.gridy = 2;
			contentPanel.add(lblUserName, gbc_lblUserName);
		}
		{
			txtNbuser = new JTextField();
			GridBagConstraints gbc_txtNbuser = new GridBagConstraints();
			gbc_txtNbuser.gridwidth = 2;
			gbc_txtNbuser.insets = new Insets(0, 0, 5, 5);
			gbc_txtNbuser.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtNbuser.gridx = 1;
			gbc_txtNbuser.gridy = 2;
			contentPanel.add(txtNbuser, gbc_txtNbuser);
			txtNbuser.setColumns(10);
		}
		{
			JLabel lblPassword = new JLabel("Password: ");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.EAST;
			gbc_lblPassword.insets = new Insets(0, 0, 5, 5);
			gbc_lblPassword.gridx = 0;
			gbc_lblPassword.gridy = 3;
			contentPanel.add(lblPassword, gbc_lblPassword);
		}
		{
			txtPassword = new JTextField();
			GridBagConstraints gbc_txtPassword = new GridBagConstraints();
			gbc_txtPassword.gridwidth = 2;
			gbc_txtPassword.insets = new Insets(0, 0, 5, 5);
			gbc_txtPassword.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtPassword.gridx = 1;
			gbc_txtPassword.gridy = 3;
			contentPanel.add(txtPassword, gbc_txtPassword);
			txtPassword.setColumns(10);
		}
		{
			JLabel lblDescription = new JLabel("Description: ");
			GridBagConstraints gbc_lblDescription = new GridBagConstraints();
			gbc_lblDescription.anchor = GridBagConstraints.EAST;
			gbc_lblDescription.insets = new Insets(0, 0, 5, 5);
			gbc_lblDescription.gridx = 0;
			gbc_lblDescription.gridy = 4;
			contentPanel.add(lblDescription, gbc_lblDescription);
		}
		{

			txtDescription = new JTextField();
			GridBagConstraints gbc_txtDescription = new GridBagConstraints();
			gbc_txtDescription.gridwidth = 2;
			gbc_txtDescription.insets = new Insets(0, 0, 5, 5);
			gbc_txtDescription.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDescription.gridx = 1;
			gbc_txtDescription.gridy = 4;
			contentPanel.add(txtDescription, gbc_txtDescription);
			txtDescription.setColumns(10);

		}
		{
			JLabel lblDatabaseLocation = new JLabel("Database Location: ");
			GridBagConstraints gbc_lblDatabaseLocation = new GridBagConstraints();
			gbc_lblDatabaseLocation.anchor = GridBagConstraints.EAST;
			gbc_lblDatabaseLocation.insets = new Insets(0, 0, 0, 5);
			gbc_lblDatabaseLocation.gridx = 0;
			gbc_lblDatabaseLocation.gridy = 5;
			contentPanel.add(lblDatabaseLocation, gbc_lblDatabaseLocation);
		}
		{
			txtDBLocation = new JTextField();
			txtDBLocation.setHorizontalAlignment(SwingConstants.LEFT);
			GridBagConstraints gbc_txtDBLocation = new GridBagConstraints();
			gbc_txtDBLocation.insets = new Insets(0, 0, 0, 5);
			gbc_txtDBLocation.gridwidth = 2;
			gbc_txtDBLocation.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDBLocation.gridx = 1;
			gbc_txtDBLocation.gridy = 5;
			contentPanel.add(txtDBLocation, gbc_txtDBLocation);
			txtDBLocation.setColumns(10);
		}
		JButton button = new JButton("Browse...");
		button.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				JFileChooser chooser = new JFileChooser();
				chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				chooser.setCurrentDirectory(new java.io.File("."));
				int returnVal = chooser.showOpenDialog(DBCreationDialog.this);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					// System.out.println("You chose to open this file:
					// " + chooser.getSelectedFile().getName());
					txtDBLocation.setText(chooser.getSelectedFile().getName());
				}

			}
		});
		GridBagConstraints gbc_button = new GridBagConstraints();
		gbc_button.gridx = 3;
		gbc_button.gridy = 5;
		contentPanel.add(button, gbc_button);
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {

						// Connect to a not registered database. If it does not
						// exists then creates it.
						// Checks that the new database is not yet in the
						// databases tree.
						String newDBPath = txtDBLocation.getText() + "\\" + txtDBName.getText();
						System.out.println("newDBPath: " + newDBPath);

						if (DBManager.DBList.contains(newDBPath)) {
							JOptionPane.showMessageDialog(contentPanel,
									"That database already exists \n and it is already registered.");
						} else {

							Connection con = null;

							try {
								System.out.println("try sin create ");
								Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
								con = DriverManager.getConnection("jdbc:derby:" + newDBPath);
							} catch (SQLException ex) {
								try {
									System.out.println("try con create ");
									Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
									con = DriverManager.getConnection("jdbc:derby:" + newDBPath + ";create=true");
								} catch (Exception g) {
									System.out.println("falla create ");
									g.printStackTrace();
								}
							} catch (ClassNotFoundException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (!DBManager.DBList.contains(newDBPath)) {

								try {

									String absolutePathToSys = "jdbc:derby:" + DBManager.pathToSettings
											+ "\\PROCESSYS_DBMANAGER";

									System.out.println("absolutePathToSys = " + absolutePathToSys);

									con = DriverManager.getConnection(absolutePathToSys);
									DBManager.stmt = con.createStatement();
									String insert = "INSERT INTO DBSYS_LIST (DBNAME, USERD, PWD, DESCRIPTION, FILEPATH) "
											+ "VALUES ('" + txtDBName.getText() + "', '" + txtNbuser.getText() + "', '"
											+ txtPassword.getText() + "', '" + txtDescription.getText() + "', '"
											+ txtDBLocation.getText() + "')";
									System.out.println("Query INSERT DATA: " + insert);
									int result = DBManager.stmt.executeUpdate(insert);

									DBManager.DBList.add(newDBPath);
									Object hierarchy[] = DBManager.DBList.toArray();
									DefaultMutableTreeNode root = DBGUIFrame.processHierarchy(hierarchy);
									DBManager.dataModel = new DefaultTreeModel(root);

								} catch (Exception ey) {
									System.out.println("Error when table creation.");
									ey.printStackTrace();
								}

							}

							setVisible(false);
							dispose();
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	/**
	 * @return the txtDBName
	 */
	public JTextField getTxtDBName() {
		return txtDBName;
	}

	/**
	 * @return the txtNbuser
	 */
	public JTextField getTxtNbuser() {
		return txtNbuser;
	}

	/**
	 * @return the txtPassword
	 */
	public JTextField getTxtPassword() {
		return txtPassword;
	}

	/**
	 * @return the txtDescription
	 */
	public JTextField getTxtDescription() {
		return txtDescription;
	}

	/**
	 * @return the txtDBLocation
	 */
	public JTextField getTxtDBLocation() {
		return txtDBLocation;
	}

}
