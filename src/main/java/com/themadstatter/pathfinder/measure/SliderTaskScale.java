package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;

public class SliderTaskScale extends AbstractPairwiseTaskScale {
    private final JSlider scale;

    public SliderTaskScale(int min, int max, int majorTickSpacing, int minorTickSpacing, boolean paintTicks, boolean paintLabels) {
        setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0;
        gbc.weightx = 0;
        add(new JLabel("Unrelated"), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        scale = new JSlider(min, max);
        scale.setMajorTickSpacing(majorTickSpacing);
        scale.setMinorTickSpacing(minorTickSpacing);
        scale.setPaintTicks(paintTicks);
        scale.setPaintLabels(paintLabels);
        add(scale, gbc);

        gbc.gridx = 2;
        gbc.weightx = 0;
        add(new JLabel("Related"), gbc);
    }

    @Override
    public Float getProximity() {
        return (float) scale.getValue();
    }

    @Override
    public void clearProximity() {
        int min = scale.getMinimum();
        int max = scale.getMaximum();
        int middle = (min + max) / 2;
        scale.setValue(middle);
    }
}
