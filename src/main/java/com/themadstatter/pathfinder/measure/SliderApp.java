package com.themadstatter.pathfinder.measure;

import com.themadstatter.pathfinder.data.ProximityModel;

public class SliderApp extends AbstractTaskApp {

    @Override
    protected String getDefaultTitle() { return "Pathfinder Slider Task"; }

    @Override
    protected String getSettingsFile() { return "slider.json"; }

    @Override
    protected TaskView createTaskView(Settings settings) {
        SliderTaskScale sliderTaskScale = new SliderTaskScale(
                settings.min,
                settings.max,
                settings.majorTickSpacing,
                settings.minorTickSpacing,
                settings.paintTicks,
                settings.paintLabels
        );
        return new PairwiseTaskView(sliderTaskScale);
    }

    @Override
    protected TaskController createTaskController(ProximityModel model, TaskView view, Runnable onFinish) {
        return new PairwiseTaskController(model, (PairwiseTaskView) view, onFinish);
    }

    @Override
    protected String getIconResource() {
        return "/img/slider.png";
    }

    public static void main(String[] args) {
        new SliderApp().run();
    }
}
