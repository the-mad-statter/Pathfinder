package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

public class DraggableApp extends AbstractTaskApp {

    @Override
    protected String getDefaultTitle() { return "Pathfinder Draggable Task"; }

    @Override
    protected String getSettingsFile() { return "draggable.json"; }

    @Override
    protected TaskView createTaskView(Settings settings) {
        return new DraggableTaskView();
    }

    @Override
    protected TaskController createTaskController(ProximityModel model, TaskView view, Runnable onFinish) {
        return new DraggableTaskController(model, (DraggableTaskView) view, onFinish);
    }

    @Override
    protected String getIconResource() {
        return "/img/draggable.png";
    }

    public static void main(String[] args) {
        new DraggableApp().run();
    }
}
