package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InstructionsView extends JPanel {
    private final JTextArea instructionsArea;
    private final JButton button;

    public InstructionsView() {
        setLayout(new BorderLayout());

        JPanel instructionsPanel = new JPanel(new BorderLayout());
        add(instructionsPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        add(buttonPanel, BorderLayout.SOUTH);

        instructionsArea = new JTextArea();
        instructionsArea.setLineWrap(true);
        instructionsArea.setWrapStyleWord(true);
        instructionsArea.setEditable(false);
        instructionsArea.setBackground(getBackground());

        JScrollPane jsp = new JScrollPane(instructionsArea);
        jsp.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        instructionsPanel.add(jsp);

        button = new JButton("OK");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "CLICK_BUTTON");
        getActionMap().put("CLICK_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
        buttonPanel.add(button);
    }

    public void addButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public void setInstructions(String instructions) {
        instructionsArea.setText(instructions);
        instructionsArea.setCaretPosition(0);
    }
}
