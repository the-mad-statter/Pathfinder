package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class ComboBoxTaskView extends JPanel implements TaskView {
    private final ComboBoxTaskScale scale;
    private final JButton button;

    public ComboBoxTaskView(String[] terms) {
        setLayout(new BorderLayout());

        this.scale = new ComboBoxTaskScale(terms);
        add(new JScrollPane(scale), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        button = new JButton("Submit");
        buttonPanel.add(button);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public Float getProximity(int row, int col) {
        return scale.getProximity(row, col);
    }

    public boolean allProximitiesNotNull() {
        return scale.allProximitiesNotNull();
    }
}
