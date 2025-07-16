package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class TargetTaskView extends JPanel implements TaskView {
    private final TargetTaskScale scale;
    private final JButton button;

    public TargetTaskView() {
        setLayout(new BorderLayout());

        this.scale = new TargetTaskScale();
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

    public Float getProximity(String term) {
        return scale.getProximity(term);
    }

    public void addLabel(String text, boolean isCenter) {
        scale.addLabel(text, isCenter);
    }

    public void clearLabels() {
        scale.clearLabels();
    }
}
