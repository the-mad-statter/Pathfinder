package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

import java.time.Duration;
import java.time.Instant;
import java.util.*;

public class TargetTaskController implements TaskController {
    private final ProximityModel model;
    private final TargetTaskView view;
    private int[] termPresentationOrder;
    private int centerTerm = 0;

    public TargetTaskController(ProximityModel model, TargetTaskView view, Runnable onTaskComplete) {
        this.model = model;
        this.view = view;

        initializeTermPresentationOrder();

        populateView();

        view.addButtonListener(_ -> {
            collectProximities();

            centerTerm = centerTerm + 1;

            if (centerTerm < termPresentationOrder.length - 1) {
                populateView();
                return;
            }

            model.setDuration(Duration.between(model.getInstant(), Instant.now()).toMillis());

            onTaskComplete.run();
        });
    }

    private void collectProximities() {
        for (int i = centerTerm + 1; i < termPresentationOrder.length; i++) {
            int row = termPresentationOrder[centerTerm];
            int col = termPresentationOrder[i];
            if (row < col) {
                int tmp = row;
                row = col;
                col = tmp;
            }
            String term = model.getTerm(termPresentationOrder[i]);
            model.setProximity(row, col, view.getProximity(term));
        }
    }

    private void initializeTermPresentationOrder() {
        int N = model.getTerms().length;
        termPresentationOrder = new int[N];
        for (int i = 0; i < N; i++)
            termPresentationOrder[i] = i;
        shuffle(termPresentationOrder);
    }

    private static void shuffle(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            // swap array[i] and array[j]
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private void populateView() {
        view.addLabel(model.getTerm(termPresentationOrder[centerTerm]), true);
        view.clearLabels();
        for (int i = centerTerm + 1; i < termPresentationOrder.length; i++)
            view.addLabel(model.getTerm(termPresentationOrder[i]), false);
    }
}
