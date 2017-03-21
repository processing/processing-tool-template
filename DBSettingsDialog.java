package dbmanager;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

@SuppressWarnings("serial")
public class DBSettingsDialog extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField txtInstallation;
	private JTextField txtLocation;

	/**
	 * Create the dialog.
	 */
	public DBSettingsDialog() {
		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(DBSettingsDialog.class.getResource("/javax/swing/plaf/basic/icons/image-delayed.png")));
		setTitle("Java DB Settings");
		setBounds(100, 100, 537, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.NORTH);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 1.0, 0.0, 0.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JTextArea txtSpecify = new JTextArea();
			txtSpecify.setFont(new Font("Monospaced", Font.PLAIN, 11));
			txtSpecify.setLineWrap(true);
			txtSpecify.setText("Specify the folder where Java DB is installed and the folder "
					+ "where you will keep your databases. The database location folder will be used "
					+ "as the value of the derby.system.home property.");
			GridBagConstraints gbc_txtSpecify = new GridBagConstraints();
			gbc_txtSpecify.anchor = GridBagConstraints.NORTH;
			gbc_txtSpecify.gridwidth = 3;
			gbc_txtSpecify.insets = new Insets(0, 0, 0, 5);
			gbc_txtSpecify.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtSpecify.gridx = 0;
			gbc_txtSpecify.gridy = 0;
			contentPanel.add(txtSpecify, gbc_txtSpecify);
		}
		{
			JPanel panel = new JPanel();
			getContentPane().add(panel, BorderLayout.CENTER);
			GridBagLayout gbl_panel = new GridBagLayout();
			gbl_panel.columnWidths = new int[] { 145, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
			gbl_panel.rowHeights = new int[] { 30, 20, 0 };
			gbl_panel.columnWeights = new double[] { 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
					Double.MIN_VALUE };
			gbl_panel.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
			panel.setLayout(gbl_panel);
			{
				JLabel lblDbInstallation = new JLabel("Java DB Installation:");
				lblDbInstallation.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_lblDbInstallation = new GridBagConstraints();
				gbc_lblDbInstallation.insets = new Insets(0, 0, 5, 5);
				gbc_lblDbInstallation.gridx = 0;
				gbc_lblDbInstallation.gridy = 0;
				panel.add(lblDbInstallation, gbc_lblDbInstallation);
			}
			{
				txtInstallation = new JTextField();
				txtInstallation.setHorizontalAlignment(SwingConstants.LEFT);
				GridBagConstraints gbc_txtInstallation = new GridBagConstraints();
				gbc_txtInstallation.gridwidth = 9;
				gbc_txtInstallation.insets = new Insets(0, 0, 5, 5);
				gbc_txtInstallation.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtInstallation.gridx = 1;
				gbc_txtInstallation.gridy = 0;
				panel.add(txtInstallation, gbc_txtInstallation);
				txtInstallation.setColumns(10);
			}
			{
				JButton btnBrowse = new JButton("Browse...");
				btnBrowse.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						JFileChooser chooser = new JFileChooser();
						chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
						chooser.setCurrentDirectory(new java.io.File("."));
						int returnVal = chooser.showOpenDialog(DBSettingsDialog.this);
						if (returnVal == JFileChooser.APPROVE_OPTION) {
							System.out.println("You chose to open this file: " + chooser.getSelectedFile().getName());
						}

					}
				});
				GridBagConstraints gbc_btnBrowse = new GridBagConstraints();
				gbc_btnBrowse.insets = new Insets(0, 0, 5, 0);
				gbc_btnBrowse.gridx = 10;
				gbc_btnBrowse.gridy = 0;
				panel.add(btnBrowse, gbc_btnBrowse);
			}
			{
				JLabel lblDBLocation = new JLabel("Database Location:\r\n");
				GridBagConstraints gbc_lblDBLocation = new GridBagConstraints();
				gbc_lblDBLocation.anchor = GridBagConstraints.EAST;
				gbc_lblDBLocation.insets = new Insets(0, 0, 0, 5);
				gbc_lblDBLocation.gridx = 0;
				gbc_lblDBLocation.gridy = 1;
				panel.add(lblDBLocation, gbc_lblDBLocation);
			}
			{
				txtLocation = new JTextField();
				GridBagConstraints gbc_txtLocation = new GridBagConstraints();
				gbc_txtLocation.gridwidth = 9;
				gbc_txtLocation.insets = new Insets(0, 0, 0, 5);
				gbc_txtLocation.fill = GridBagConstraints.HORIZONTAL;
				gbc_txtLocation.gridx = 1;
				gbc_txtLocation.gridy = 1;
				panel.add(txtLocation, gbc_txtLocation);
				txtLocation.setColumns(10);
			}
			{
				JButton btnBrowse_1 = new JButton("Browse...");
				btnBrowse_1.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
					}
				});
				GridBagConstraints gbc_btnBrowse_1 = new GridBagConstraints();
				gbc_btnBrowse_1.gridx = 10;
				gbc_btnBrowse_1.gridy = 1;
				panel.add(btnBrowse_1, gbc_btnBrowse_1);
			}
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
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

}
