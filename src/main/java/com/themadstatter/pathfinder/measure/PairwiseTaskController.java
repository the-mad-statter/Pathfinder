package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;

public class PairwiseTaskController implements TaskController {
    protected final ProximityModel model;
    protected final PairwiseTaskView view;
    private final int[][] termPresentationOrder;
    private int currentPair = 0;

    public PairwiseTaskController(ProximityModel model, PairwiseTaskView view, Runnable onTaskComplete) {
        this.model = model;
        this.view = view;
        int totalPairs = model.getTerms().length * (model.getTerms().length - 1) / 2;
        this.termPresentationOrder = generateTermPresentationOrder(totalPairs);

        this.view.addButtonListener(_ -> {
            Float proximity = view.getProximity();
            if (currentPair < termPresentationOrder.length && proximity != null) {
                model.setProximity(
                        termPresentationOrder[currentPair][0],
                        termPresentationOrder[currentPair][1],
                        proximity
                );

                if (currentPair + 1 < termPresentationOrder.length) {
                    currentPair++;
                    updateTaskView();
                } else {
                    model.setDuration(Duration.between(model.getInstant(), Instant.now()).toMillis());
                    onTaskComplete.run();
                }
            }
        });

        updateTaskView();
    }

    private void updateTaskView() {
        view.clearProximity();

        view.setTerms(new String[]{
                model.getTerm(termPresentationOrder[currentPair][0]),
                model.getTerm(termPresentationOrder[currentPair][1])
        });

        view.setProgressMessage(currentPair, termPresentationOrder.length);
    }

    private int[][] generateTermPresentationOrder(int totalPairs) {
        int[][] order = new int[totalPairs][2];
        int i = 0;
        for (int r = 0; r < model.getTerms().length; r++) {
            for (int c = 0; c < r; c++) {
                order[i][0] = r;
                order[i][1] = c;
                i++;
            }
        }

        Random r = new Random();
        for (i = totalPairs - 1; i > 0; i--) {
            int j = r.nextInt(i + 1);
            int[] temp = order[i];
            order[i] = order[j];
            order[j] = temp;
        }

        return order;
    }
}
