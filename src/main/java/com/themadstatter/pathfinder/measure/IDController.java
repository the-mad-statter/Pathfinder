package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

public class IDController {
    private final ProximityModel model;
    private final IDView IDView;
    private final Runnable onIDRecorded;

    public IDController(ProximityModel model, IDView IDView, Runnable onIDRecorded) {
        this.model = model;
        this.IDView = IDView;
        this.onIDRecorded = onIDRecorded;

        this.IDView.addButtonListener(_ -> handleSubmission());
    }

    private void handleSubmission() {
        String input =  IDView.getInputFieldText();

        if (input.isEmpty()) {
            return;
        }

        if (IDView.getFirstEntry() == null) {
            IDView.setFirstID(input);
            IDView.clearInput();
            IDView.setLabelText("Re-enter your ID:");
            IDView.focusInput();
        } else {
            if (IDView.getFirstEntry().equals(input)) {
                model.setID(input);
                onIDRecorded.run();
            } else {
                IDView.clearFirstID();
                IDView.clearInput();
                IDView.setLabelText("IDs do not match. Please try again.");
                IDView.focusInput();
            }
        }
    }
}
