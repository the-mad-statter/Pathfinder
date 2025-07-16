package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.util.ArrayList;

public class DraggableTaskScale extends JPanel {
    private final ArrayList<DraggableLabel> labels;

    public DraggableTaskScale() {
        setLayout(null);

        labels = new ArrayList<>();
    }

    public void addLabel(String text) {
        DraggableLabel label = new DraggableLabel(text);
        labels.add(label);
        add(label);
    }

    public void randomlySetLabelLocations() {
        for (DraggableLabel label: labels) {
            int randomTaskPanelX = (int) (Math.random() * getWidth());
            int randomTaskPanelY = (int) (Math.random() * getHeight());
            label.setLocation(randomTaskPanelX, randomTaskPanelY);
        }
    }

    public DraggableLabel getLabel(String text) {
        for (DraggableLabel label : labels) {
            if (label.getText().equals(text)) {
                return label;
            }
        }
        return null;
    }
}
