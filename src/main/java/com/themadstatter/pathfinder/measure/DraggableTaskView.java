package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class DraggableTaskView extends JPanel implements TaskView {
    private final DraggableTaskScale scale;
    private final JButton button;


    public DraggableTaskView() {
        setLayout(new BorderLayout());

        scale = new DraggableTaskScale();
        add(scale, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        button = new JButton("Submit");
        button.setToolTipText("Click only when finished");
        buttonPanel.add(button);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void addButtonListener(ActionListener listener) {
        button.addActionListener(listener);
    }

    public void addLabel(String text) {
        scale.addLabel(text);
    }

    public void randomlySetLabelLocations() {
        scale.randomlySetLabelLocations();
    }

    public DraggableLabel getLabel(String text) {
        return scale.getLabel(text);
    }
}
