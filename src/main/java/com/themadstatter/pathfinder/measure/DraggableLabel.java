package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DraggableLabel extends JLabel {
    private Point initialClick;

    public DraggableLabel(String text) {
        super(text);
        setOpaque(true);
        setSize(getPreferredSize());

        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                Container parent = getParent();
                if (parent == null || initialClick == null) return;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;
                int newX = getX() + xMoved;
                int newY = getY() + yMoved;

                if (parent instanceof TargetTaskScale targetPanel) {
                    Point clamped = clampToCircle(newX, newY, targetPanel);
                    setLocation(clamped);
                } else {
                    Point clamped = clampToBounds(newX, newY, parent);
                    setLocation(clamped);
                }
            }
        });
    }

    private Point clampToBounds(int x, int y, Container parent) {
        int maxX = parent.getWidth() - getWidth();
        int maxY = parent.getHeight() - getHeight();

        int clampedX = Math.max(0, Math.min(x, maxX));
        int clampedY = Math.max(0, Math.min(y, maxY));

        return new Point(clampedX, clampedY);
    }

    private Point clampToCircle(int x, int y, TargetTaskScale panel) {
        int centerX = panel.getWidth() / 2;
        int centerY = panel.getHeight() / 2;
        int radius = panel.getMaxRadius();

        // Label center based on proposed top-left corner
        int labelCenterX = x + getWidth() / 2;
        int labelCenterY = y + getHeight() / 2;

        double dx = labelCenterX - centerX;
        double dy = labelCenterY - centerY;
        double distance = Math.sqrt(dx * dx + dy * dy);

        if (distance <= radius) {
            return new Point(x, y);
        }

        // Project center back to circle edge
        double scale = radius / distance;
        int clampedCenterX = (int) (centerX + dx * scale);
        int clampedCenterY = (int) (centerY + dy * scale);

        int clampedX = clampedCenterX - getWidth() / 2;
        int clampedY = clampedCenterY - getHeight() / 2;

        return new Point(clampedX, clampedY);
    }
}
