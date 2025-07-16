package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class PairwiseTaskView extends JPanel implements TaskView {
    protected AbstractPairwiseTaskScale scale;
    protected JLabel topTerm;
    protected JLabel bottomTerm;
    protected JLabel progressMessageLabel;
    protected JButton nextButton;

    public PairwiseTaskView(AbstractPairwiseTaskScale scale) {
        setLayout(new BorderLayout());

        this.scale = scale;
        scale.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        add(scale, BorderLayout.NORTH);

        JPanel termsPanel = new JPanel(new GridLayout(2, 1));
        add(termsPanel, BorderLayout.CENTER);

        JPanel buttonAndMessagePanel = new JPanel(new GridLayout(2, 1));
        add(buttonAndMessagePanel, BorderLayout.SOUTH);

        JPanel topTermPanel = new JPanel(new GridBagLayout());
        termsPanel.add(topTermPanel);
        JPanel bottomTermPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        termsPanel.add(bottomTermPanel);

        JPanel nextButtonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonAndMessagePanel.add(nextButtonPanel);
        JPanel progressMessagePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        progressMessagePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 10));
        buttonAndMessagePanel.add(progressMessagePanel);

        topTerm = new JLabel("Term 1");
        bottomTerm = new JLabel("Term 2");

        Font defaultFont = UIManager.getFont("Label.font");
        Font resizedFont = defaultFont.deriveFont(18f);
        topTerm.setFont(resizedFont);
        bottomTerm.setFont(resizedFont);

        topTermPanel.add(topTerm);
        bottomTermPanel.add(bottomTerm);

        nextButton = new JButton("Next");

        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("SPACE"), "CLICK_NEXT_BUTTON");
        getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("ENTER"), "CLICK_NEXT_BUTTON");
        getActionMap().put("CLICK_NEXT_BUTTON", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nextButton.doClick();
            }
        });

        nextButtonPanel.add(nextButton);

        progressMessageLabel = new JLabel("X out of X completed");
        progressMessagePanel.add(progressMessageLabel);
        progressMessageLabel.setHorizontalAlignment(JLabel.RIGHT);

        setVisible(true);
    }

    public void setTerms(String[] terms) {
        if (Math.random() < 0.5) {
            topTerm.setText(terms[0]);
            bottomTerm.setText(terms[1]);
        } else {
            topTerm.setText(terms[1]);
            bottomTerm.setText(terms[0]);
        }
    }

    public void setProgressMessage(int i, int n) {
        progressMessageLabel.setText(String.format("%d out of %d completed", i, n));
    }

    public void addButtonListener(ActionListener listener) {
        nextButton.addActionListener(listener);
    }

    public Float getProximity() { return scale.getProximity(); }

    public void clearProximity() {
        scale.clearProximity();
    }
}
