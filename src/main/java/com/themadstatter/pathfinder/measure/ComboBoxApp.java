package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

public class ComboBoxApp extends AbstractTaskApp {

    @Override
    protected String getDefaultTitle() { return "Pathfinder ComboBox Task"; }

    @Override
    protected String getSettingsFile() { return "combobox.json"; }

    @Override
    protected TaskView createTaskView(Settings settings) {
        return new ComboBoxTaskView(settings.terms);
    }

    @Override
    protected TaskController createTaskController(ProximityModel model, TaskView view, Runnable onFinish) {
        return new ComboBoxTaskController(model, (ComboBoxTaskView) view, onFinish);
    }

    @Override
    protected String getIconResource() {
        return "/img/combobox.png";
    }

    public static void main(String[] args) {
        new ComboBoxApp().run();
    }
}
