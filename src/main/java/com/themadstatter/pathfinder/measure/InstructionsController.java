package com.themadstatter.pathfinder.measure;

public class InstructionsController {

    public InstructionsController(InstructionsModel instructionsModel, InstructionsView instructionsView, Runnable onInstructionsRead) {

        instructionsView.addButtonListener(_ -> onInstructionsRead.run());

        instructionsView.setInstructions(instructionsModel.instructions());
    }
}