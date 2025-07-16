package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

import java.time.Duration;
import java.time.Instant;

public class ComboBoxTaskController implements TaskController {

    public ComboBoxTaskController(ProximityModel model, ComboBoxTaskView view, Runnable onTaskComplete) {
        view.addButtonListener(_ -> {
            if (view.allProximitiesNotNull()) {
                model.setDuration(Duration.between(model.getInstant(), Instant.now()).toMillis());

                for (int row = 0; row < model.getTerms().length; row++) {
                    for (int col = 0; col < row; col++) {
                        float proximity = view.getProximity(row, col);
                        model.setProximity(row, col, proximity);
                    }
                }

                onTaskComplete.run();
            }
        });
    }
}
