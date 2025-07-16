package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

public class RadioButtonApp extends AbstractTaskApp {

    @Override
    protected String getDefaultTitle() { return "Pathfinder Radio Button Task"; }

    @Override
    protected String getSettingsFile() { return "radiobutton.json"; }

    @Override
    protected TaskView createTaskView(Settings settings) {
        RadioButtonTaskScale radioButtonTaskScale = new RadioButtonTaskScale();
        return new PairwiseTaskView(radioButtonTaskScale);
    }

    @Override
    protected TaskController createTaskController(ProximityModel model, TaskView view, Runnable onFinish) {
        return new PairwiseTaskController(model, (PairwiseTaskView) view, onFinish);
    }

    @Override
    protected String getIconResource() {
        return "/img/radiobutton.png";
    }

    public static void main(String[] args) {
        new RadioButtonApp().run();
    }
}
