package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

public class TargetApp extends AbstractTaskApp {

    @Override
    protected String getDefaultTitle() { return "Pathfinder Target Task"; }

    @Override
    protected String getSettingsFile() { return "target.json"; }

    @Override
    protected TaskView createTaskView(Settings settings) {
        return new TargetTaskView();
    }

    @Override
    protected TaskController createTaskController(ProximityModel model, TaskView view, Runnable onFinish) {
        return new TargetTaskController(model, (TargetTaskView) view, onFinish);
    }

    @Override
    protected String getIconResource() {
        return "/img/target.png";
    }

    public static void main(String[] args) {
        new TargetApp().run();
    }
}
