package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentEvent;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class RadioButtonTaskScale extends AbstractPairwiseTaskScale {
    private final List<JRadioButton> radioButtons = new ArrayList<>();
    private final List<Integer> hashPositions = new ArrayList<>();
    private final JPanel linePanel;
    private final JPanel radioPanel;

    private static final int ARROW_MARGIN = 30;
    private static final int ARROW_HEAD_WIDTH = 10;
    private static final int LABEL_MARGIN = 60;

    private static ButtonGroup group;

    public RadioButtonTaskScale() {
        setLayout(new BorderLayout());

        // --- Top line panel with arrows and hash marks ---
        linePanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);

                int y = getHeight() / 2;
                int lineStartX = ARROW_MARGIN;
                int lineEndX = getWidth() - ARROW_MARGIN;

                // Draw extended line from arrow to arrow
                g.setColor(Color.BLACK);
                g.drawLine(lineStartX, y, lineEndX, y);

                // Draw arrows
                drawArrow(g, lineStartX, y, lineStartX + ARROW_HEAD_WIDTH, y - 5, lineStartX + ARROW_HEAD_WIDTH, y + 5); // left
                drawArrow(g, lineEndX, y, lineEndX - ARROW_HEAD_WIDTH, y - 5, lineEndX - ARROW_HEAD_WIDTH, y + 5); // right

                // Draw hash marks (just inside arrow tips)
                for (int x : hashPositions) {
                    g.drawLine(x, y - 5, x, y + 5);
                }
            }

            @Override
            public Dimension getPreferredSize() {
                return new Dimension(600, 40);
            }

            private void drawArrow(Graphics g, int x1, int y1, int x2, int y2, int x3, int y3) {
                int[] xPoints = {x1, x2, x3};
                int[] yPoints = {y1, y2, y3};
                g.fillPolygon(xPoints, yPoints, 3);
            }
        };

        // --- Line labels (Unrelated/Related) ---
        JPanel lineWithLabels = new JPanel(new BorderLayout());
        lineWithLabels.add(new JLabel("Unrelated"), BorderLayout.WEST);
        lineWithLabels.add(linePanel, BorderLayout.CENTER);
        lineWithLabels.add(new JLabel("Related  "), BorderLayout.EAST);

        // --- Radio panel with exact layout ---
        radioPanel = new JPanel(null);
        radioPanel.setPreferredSize(new Dimension(600, 80));
        group = new ButtonGroup();
        int numButtons = 9;

        for (int i = 1; i <= numButtons; i++) {
            JRadioButton rb = new JRadioButton("<html><center>" + i + "</center></html>");
            rb.setHorizontalTextPosition(SwingConstants.CENTER);
            rb.setVerticalTextPosition(SwingConstants.BOTTOM);
            rb.setHorizontalAlignment(SwingConstants.CENTER);
            rb.setSize(80, 40);
            radioButtons.add(rb);
            group.add(rb);
            radioPanel.add(rb);

            String s = Integer.toString(i);
            String actionMapKey = String.format("SELECT_SCALE_BUTTON_%s", s);
            getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(s), actionMapKey);
            int radioButtonsIndex = i - 1;
            getActionMap().put(actionMapKey, new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    radioButtons.get(radioButtonsIndex).setSelected(true);
                }
            });
        }

        add(lineWithLabels, BorderLayout.NORTH);
        add(radioPanel, BorderLayout.CENTER);

        // Layout buttons and marks after visible
        SwingUtilities.invokeLater(() -> {
            layoutRadioButtons();
            computeHashPositions();
            linePanel.repaint();
        });

        radioPanel.addComponentListener(new java.awt.event.ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                layoutRadioButtons();
                computeHashPositions();
                linePanel.repaint();
            }
        });
    }

    private void layoutRadioButtons() {
        int panelWidth = radioPanel.getWidth();
        int startX = LABEL_MARGIN + ARROW_MARGIN + ARROW_HEAD_WIDTH;
        int endX = panelWidth - LABEL_MARGIN - ARROW_MARGIN - ARROW_HEAD_WIDTH;

        int usableWidth = endX - startX;
        int numButtons = radioButtons.size();
        int spacing = usableWidth / (numButtons - 1);
        int y = 10;

        for (int i = 0; i < numButtons; i++) {
            JRadioButton rb = radioButtons.get(i);
            int rbWidth = rb.getPreferredSize().width;
            int x = startX + i * spacing - rbWidth / 2;
            rb.setBounds(x, y, rbWidth, 40);
        }
    }

    private void computeHashPositions() {
        hashPositions.clear();

        for (JRadioButton rb : radioButtons) {
            Point center = new Point(rb.getWidth() / 2, rb.getHeight() / 2);
            SwingUtilities.convertPointToScreen(center, rb);
            SwingUtilities.convertPointFromScreen(center, linePanel);
            hashPositions.add(center.x);
        }
    }

    @Override
    public Float getProximity() {
        Float proximity = null;

        for (Enumeration<AbstractButton> buttons = group.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();
            if (button.isSelected()) {
                proximity = Float.parseFloat(button.getText().replaceAll("</?html>|</?center>", ""));
                break;
            }
        }

        return proximity;
    }

    @Override
    public void clearProximity() {
        group.clearSelection();
    }
}
