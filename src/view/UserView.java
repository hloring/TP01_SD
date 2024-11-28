package view;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionListener;

public class UserView {

    private JFrame frame;
    private JPanel mainPanel;
    private JLabel headerLabel;
    private JCheckBox checkbox1;
    private JCheckBox checkbox2;
    private JTextField textField;
    private JLabel ipDisplayLabel;
    private JButton pedraButton;
    private JButton papelButton;
    private JButton tesouraButton;

    public UserView() {
        createAndShowGUI();
    }

    private void createAndShowGUI() {
        frame = new JFrame("Minha aplicação que usa soquetes");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600); // Tamanho ajustado

        mainPanel = createMainPanel();
        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private JPanel createMainPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        headerLabel = createHeaderLabel();
        panel.add(headerLabel, BorderLayout.NORTH);

        JPanel inputPanel = createInputPanel();
        panel.add(inputPanel, BorderLayout.CENTER);

        JPanel buttonPanel = createButtonPanel();
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JLabel createHeaderLabel() {
        JLabel label = new JLabel("Minha aplicação que usa soquetes", SwingConstants.CENTER);
        label.setOpaque(true);
        label.setBackground(Color.GRAY);
        label.setPreferredSize(new Dimension(0, 60)); // 10% da altura
        return label;
    }

    private JPanel createInputPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setPreferredSize(new Dimension(0, 240)); // 40% da altura
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel tipoConexaoLabel = new JLabel("Tipo de conexão");
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(tipoConexaoLabel, gbc);

        checkbox1 = new JCheckBox("RMI");
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(checkbox1, gbc);

        JLabel ipConexaoLabel = new JLabel("IP para conexão");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        panel.add(ipConexaoLabel, gbc);

        textField = new JTextField(15);
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(textField, gbc);

        ipDisplayLabel = new JLabel("");
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(ipDisplayLabel, gbc);

        textField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateIpDisplay();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateIpDisplay();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateIpDisplay();
            }
        });

        return panel;
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setPreferredSize(new Dimension(0, 300)); // 50% da altura
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.BLACK),
                BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));

        pedraButton = new JButton("Pedra");
        papelButton = new JButton("Papel");
        tesouraButton = new JButton("Tesoura");

        panel.add(pedraButton);
        panel.add(papelButton);
        panel.add(tesouraButton);

        return panel;
    }

    private void updateIpDisplay() {
        ipDisplayLabel.setText("IP inserido: " + textField.getText());
    }

    // Métodos para adicionar ActionListeners aos botões
    public void addPedraButtonListener(ActionListener listener) {
        pedraButton.addActionListener(listener);
    }

    public void addPapelButtonListener(ActionListener listener) {
        papelButton.addActionListener(listener);
    }

    public void addTesouraButtonListener(ActionListener listener) {
        tesouraButton.addActionListener(listener);
    }

    // Métodos para acessar dados da view
    public String getTextFieldValue() {
        return textField.getText();
    }

    public boolean isCheckbox1Selected() {
        return checkbox1.isSelected();
    }

    public boolean isCheckbox2Selected() {
        return checkbox2.isSelected();
    }

    public void showAlert(String message) {
        JOptionPane.showMessageDialog(frame, message);
    }
}