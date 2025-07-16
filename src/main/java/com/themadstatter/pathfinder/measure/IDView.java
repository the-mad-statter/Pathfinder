package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class IDView extends JPanel {
    private final JLabel label;
    private final JTextField input;
    private final JButton button;
    private String firstID = null;

    public IDView() {
        setLayout(new BorderLayout());

        label = new JLabel("Enter your ID:");
        input = new JTextField(15);
        button = new JButton("Submit");

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "CLICK_BUTTON");
        getActionMap().put("CLICK_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });

        JPanel inputPanel = new JPanel(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5);
        inputPanel.add(label, gbc);
        gbc.gridx = 1;
        inputPanel.add(input, gbc);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(button);

        add(inputPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        focusInput();
    }

    public void focusInput() {
        input.requestFocusInWindow();
    }

    public void addButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public String getInputFieldText() {
        return input.getText().trim();
    }

    public String getFirstEntry() {
        return firstID;
    }

    public void setFirstID(String s) {
        firstID = s;
    }

    public void clearFirstID() {
        firstID = null;
    }

    public void clearInput() {
        input.setText("");
    }

    public void setLabelText(String text) {
        label.setText(text);
    }
}
