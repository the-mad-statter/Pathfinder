package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.Arc2D;
import java.util.ArrayList;

public class TargetTaskScale extends JPanel {
    private final JLabel centerLabel = new JLabel();
    private final ArrayList<DraggableLabel> termLabels = new ArrayList<>();
    private final ArrayList<JLabel> circleLabels = new ArrayList<>();

    public TargetTaskScale() {
        setLayout(null);

        add(centerLabel);

        for (int i = 1; i <= 9; i++) {
            JLabel label = new JLabel(String.valueOf(i));
            circleLabels.add(label);
            add(label);
        }

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                super.componentResized(e);
                for (DraggableLabel label: termLabels)
                    setLabelLocation(label);
            }
        });
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Enable anti-aliasing
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int centerX = getWidth() / 2;
        int centerY = getHeight() / 2;
        int maxRadius = getMaxRadius();

        centerLabel.setSize(centerLabel.getPreferredSize());
        centerLabel.setLocation(centerX - centerLabel.getWidth() / 2, centerY - centerLabel.getHeight() / 2);

        for (int i = 0; i < 9; i++) {
            int radius = maxRadius * (9 - i) / 9;
            int diameter = radius * 2;
            int topLeftX = centerX - radius;
            int topLeftY = centerY - radius;

            JLabel label = circleLabels.get(i);
            label.setSize(label.getPreferredSize());
            int labelX = centerX - label.getWidth() / 2;
            int labelY = topLeftY - label.getHeight() / 2;
            label.setLocation(labelX, labelY);

            // Compute angle span to skip under the label
            double labelArcAngle = (label.getWidth() / (double) diameter) * 360.0;
            double startAngle1 = 90 + labelArcAngle / 2;
            double extent1 = 360 - labelArcAngle;

            // Draw arc avoiding the label
            Arc2D arc = new Arc2D.Double(topLeftX, topLeftY, diameter, diameter,
                    startAngle1, extent1, Arc2D.OPEN);
            g2.draw(arc);
        }
    }

    public int getMaxRadius() {
        return Math.min(getWidth(), getHeight()) / 2 - 30;
    }

    public void clearLabels() {
        for (DraggableLabel label : termLabels) remove(label);
        termLabels.clear();
        revalidate();
        repaint();
    }

    public void addLabel(String text, boolean isCenter) {
        if (isCenter) {
            centerLabel.setText(text);
        } else {
            DraggableLabel label = new DraggableLabel(text);
            termLabels.add(label);
            add(label);

            SwingUtilities.invokeLater(() -> setLabelLocation(label));
        }
    }

    private void setLabelLocation(DraggableLabel label) {
        int width = getWidth();
        int height = getHeight();
        int centerX = width / 2;
        int centerY = height / 2;
        int maxRadius = getMaxRadius();
        int x = (int) (Math.random() * width);
        int y = (int) (Math.random() * height);
        while(!isPointInCircle(x, y, centerX, centerY, maxRadius)) {
            x = (int) (Math.random() * width);
            y = (int) (Math.random() * height);
        }
        label.setLocation(x, y);
    }

    private DraggableLabel getLabel(String text) {
        for (DraggableLabel label : termLabels) {
            if (label.getText().equals(text)) {
                return label;
            }
        }
        return null;
    }

    private float getPointDistance(int x1, int y1, int x2, int y2) {
        int a = x2 - x1;
        int b = y2 - y1;
        return (float) Math.sqrt(Math.pow(a, 2) + Math.pow(b, 2));
    }

    private boolean isPointInCircle(int pointX, int pointY, int centerX, int centerY, int radius) {
        return getPointDistance(pointX, pointY, centerX, centerY) <= radius;
    }

    public float getProximity(String text) {
        DraggableLabel label = getLabel(text);
        int m = getMaxRadius();
        assert label != null;
        float r = getPointDistance(label.getX(), label.getY(), getWidth() / 2, getHeight() / 2);
        return (float) Math.ceil(9 * (1 - r / m));
    }
}
