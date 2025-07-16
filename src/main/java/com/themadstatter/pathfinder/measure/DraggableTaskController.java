package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

import javax.swing.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.time.Duration;
import java.time.Instant;

public class DraggableTaskController implements TaskController {
    private final ProximityModel model;
    private final DraggableTaskView view;

    public DraggableTaskController(ProximityModel model, DraggableTaskView view, Runnable onTaskComplete) {
        this.model = model;
        this.view = view;

        for (String s : model.getTerms()) {
            view.addLabel(s);
        }

        view.addComponentListener(new ComponentAdapter() {
            public void componentShown(ComponentEvent e) {
                view.randomlySetLabelLocations();
            }
        });

        view.addButtonListener(_ -> {
            String[] options = {"Yes", "No"};
            int result = JOptionPane.showOptionDialog(
                    null,
                    "Do you want to continue?",
                    "Confirm",
                    JOptionPane.YES_NO_OPTION,
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    options,
                    options[1]
            );

            if (result != 0)
                return;

            model.setDuration(Duration.between(model.getInstant(), Instant.now()).toMillis());

            float maxDistance = (float) getMaxPairwiseLabelDistance();
            float minScale = model.getMin();
            float maxScale = model.getMax();

            for (int row = 0; row < model.getTerms().length; row++) {
                for (int col = 0; col < row; col++) {
                    String term1 = model.getTerm(row);
                    String term2 = model.getTerm(col);
                    float distance = (float) getPairwiseLabelDistance(term1, term2);
                    float proximity = mapDistanceToScale(distance, 0, maxDistance, minScale, maxScale, true);
                    model.setProximity(row, col, proximity);
                }
            }
            onTaskComplete.run();
        });
    }

    private double getPairwiseLabelDistance(String text1, String text2) {
        DraggableLabel label1 = view.getLabel(text1);
        int x1 = label1.getX() + label1.getWidth() / 2;
        int y1 = label1.getY() + label1.getHeight() / 2;
        DraggableLabel label2 = view.getLabel(text2);
        int x2 = label2.getX() + label2.getWidth() / 2;
        int y2 = label2.getY() + label2.getHeight() / 2;
        return Math.sqrt(Math.pow(x2 - x1, 2) + Math.pow(y2 - y1, 2));
    }

    private double getMaxPairwiseLabelDistance() {
        double max = 0;
        for (int row = 0; row < model.getTerms().length; row++) {
            for (int col = 0; col < row; col++) {
                double d = getPairwiseLabelDistance(model.getTerm(row), model.getTerm(col));
                if (d > max)
                    max = d;
            }
        }
        return max;
    }

    public static float mapDistanceToScale(
            float distance,
            float minDistance,
            float maxDistance,
            float minScale,
            float maxScale,
            boolean reverse
    ) {
        if (distance <= minDistance) return reverse ? maxScale : minScale;
        if (distance >= maxDistance) return reverse ? minScale : maxScale;

        float rangeScale = maxScale - minScale;
        float scaleValue = ((distance - minDistance) * rangeScale) / (maxDistance - minDistance);

        if (!reverse) {
            scaleValue = minScale + scaleValue;
        } else {
            scaleValue = maxScale - scaleValue;
        }

        return scaleValue;
    }
}
