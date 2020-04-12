package gui.Panels;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import java.awt.Color;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.awt.Font;
import javax.swing.JComboBox;

@SuppressWarnings("serial")
public class SettingsPanel extends JPanel {

	private JComboBox<String> langComboBox;
	private String[] languages = {"English", "Español", "Euskera"};
	
	/**
	 * Create the panel.
	 */
	public SettingsPanel() {
		setBackground(Color.WHITE);
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{30, 0, 0, 30, 101, 0, 0};
		gridBagLayout.rowHeights = new int[]{40, 40, 0, 0, 22, 0, 8, 0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JLabel lblLanguage = new JLabel("Language:");
		lblLanguage.setFont(new Font("Source Code Pro Black", Font.BOLD, 13));
		GridBagConstraints gbc_lblLanguage = new GridBagConstraints();
		gbc_lblLanguage.anchor = GridBagConstraints.WEST;
		gbc_lblLanguage.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguage.gridx = 1;
		gbc_lblLanguage.gridy = 2;
		add(lblLanguage, gbc_lblLanguage);
		
		JLabel lblLanguageDesc = new JLabel("Select a language to set as default:");
		lblLanguageDesc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblLanguageDesc = new GridBagConstraints();
		gbc_lblLanguageDesc.anchor = GridBagConstraints.WEST;
		gbc_lblLanguageDesc.gridwidth = 3;
		gbc_lblLanguageDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblLanguageDesc.gridx = 1;
		gbc_lblLanguageDesc.gridy = 3;
		add(lblLanguageDesc, gbc_lblLanguageDesc);
		
		langComboBox = new JComboBox<String>(languages);
		GridBagConstraints gbc_langComboBox = new GridBagConstraints();
		gbc_langComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_langComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_langComboBox.gridx = 4;
		gbc_langComboBox.gridy = 3;
		add(langComboBox, gbc_langComboBox);
		langComboBox.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				String language = (String)langComboBox.getSelectedItem();
				if(language.equals("English")) {
					Locale.setDefault(new Locale("en"));
				}
				else if (language.equals("Español")) {
					Locale.setDefault(new Locale("es"));
				}
				else if (language.equals("Euskera")) {
					Locale.setDefault(new Locale("eus"));
				}
			}
		});
		
		JLabel lblColor = new JLabel("Color:");
		lblColor.setFont(new Font("Source Code Pro Black", Font.BOLD, 13));
		GridBagConstraints gbc_lblColor = new GridBagConstraints();
		gbc_lblColor.anchor = GridBagConstraints.WEST;
		gbc_lblColor.insets = new Insets(0, 0, 5, 5);
		gbc_lblColor.gridx = 1;
		gbc_lblColor.gridy = 5;
		add(lblColor, gbc_lblColor);
		
		JLabel lblColorDesc = new JLabel("Select a theme:\r\n");
		lblColorDesc.setFont(new Font("Tahoma", Font.PLAIN, 11));
		GridBagConstraints gbc_lblColorDesc = new GridBagConstraints();
		gbc_lblColorDesc.anchor = GridBagConstraints.WEST;
		gbc_lblColorDesc.gridwidth = 3;
		gbc_lblColorDesc.insets = new Insets(0, 0, 5, 5);
		gbc_lblColorDesc.gridx = 1;
		gbc_lblColorDesc.gridy = 6;
		add(lblColorDesc, gbc_lblColorDesc);
		
		JComboBox<String> colorComboBox = new JComboBox<String> ();
		GridBagConstraints gbc_colorComboBox = new GridBagConstraints();
		gbc_colorComboBox.insets = new Insets(0, 0, 5, 5);
		gbc_colorComboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_colorComboBox.gridx = 4;
		gbc_colorComboBox.gridy = 6;
		add(colorComboBox, gbc_colorComboBox);
		
	}

	
}
