package dbmanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class DBCreationDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtDBName;
	private JTextField txtNbuser;
	private JTextField txtPassword;
	private JTextField txtDBLocation;

	/**
	 * Create the dialog.
	 */
	public DBCreationDialog() {
		setType(Type.POPUP);
		setTitle("Java DB Database Creation");
		setBounds(100, 100, 599, 310);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] {150, 100, 100, 0};
		gbl_contentPanel.rowHeights = new int[]{26, 0, 0, 0, 0, 0};
		gbl_contentPanel.columnWeights = new double[]{0.0, 1.0, 1.0, 0.0};
		gbl_contentPanel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblDatabaseName = new JLabel("Database Name:");
			GridBagConstraints gbc_lblDatabaseName = new GridBagConstraints();
			gbc_lblDatabaseName.anchor = GridBagConstraints.WEST;
			gbc_lblDatabaseName.insets = new Insets(0, 0, 5, 5);
			gbc_lblDatabaseName.gridx = 0;
			gbc_lblDatabaseName.gridy = 1;
			contentPanel.add(lblDatabaseName, gbc_lblDatabaseName);
		}
		{
			txtDBName = new JTextField();
			GridBagConstraints gbc_txtDBName = new GridBagConstraints();
			gbc_txtDBName.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDBName.gridwidth = 3;
			gbc_txtDBName.insets = new Insets(0, 0, 5, 0);
			gbc_txtDBName.anchor = GridBagConstraints.NORTHWEST;
			gbc_txtDBName.gridx = 1;
			gbc_txtDBName.gridy = 1;
			contentPanel.add(txtDBName, gbc_txtDBName);
			txtDBName.setColumns(10);
		}
		{
			JLabel lblUserName = new JLabel("User Name:");
			GridBagConstraints gbc_lblUserName = new GridBagConstraints();
			gbc_lblUserName.anchor = GridBagConstraints.WEST;
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
			JLabel lblPassword = new JLabel("Password:");
			GridBagConstraints gbc_lblPassword = new GridBagConstraints();
			gbc_lblPassword.anchor = GridBagConstraints.WEST;
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
			JLabel lblDatabaseLocation = new JLabel("Database Location:");
			GridBagConstraints gbc_lblDatabaseLocation = new GridBagConstraints();
			gbc_lblDatabaseLocation.anchor = GridBagConstraints.WEST;
			gbc_lblDatabaseLocation.insets = new Insets(0, 0, 0, 5);
			gbc_lblDatabaseLocation.gridx = 0;
			gbc_lblDatabaseLocation.gridy = 4;
			contentPanel.add(lblDatabaseLocation, gbc_lblDatabaseLocation);
		}
		{
			txtDBLocation = new JTextField();
			GridBagConstraints gbc_txtDBLocation = new GridBagConstraints();
			gbc_txtDBLocation.insets = new Insets(0, 0, 0, 5);
			gbc_txtDBLocation.gridwidth = 2;
			gbc_txtDBLocation.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtDBLocation.gridx = 1;
			gbc_txtDBLocation.gridy = 4;
			contentPanel.add(txtDBLocation, gbc_txtDBLocation);
			txtDBLocation.setColumns(10);
		}
		{
			JButton btnSettings = new JButton("Settings...");
			btnSettings.addActionListener(new ActionListener() {
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
			GridBagConstraints gbc_btnSettings = new GridBagConstraints();
			gbc_btnSettings.gridx = 3;
			gbc_btnSettings.gridy = 4;
			contentPanel.add(btnSettings, gbc_btnSettings);
		}
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
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

}
