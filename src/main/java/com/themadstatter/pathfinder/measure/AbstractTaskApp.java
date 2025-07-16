package com.themadstatter.pathfinder.measure;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.themadstatter.pathfinder.data.ProximityModel;
import com.themadstatter.pathfinder.data.ProximityModelStructure;
import com.themadstatter.pathfinder.data.ProximityModelType;
import com.themadstatter.pathfinder.data.ProximityModelWriter;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;
import java.time.Instant;

public abstract class AbstractTaskApp {
    protected abstract String getDefaultTitle();
    protected abstract String getSettingsFile();
    protected abstract TaskView createTaskView(Settings settings);
    protected abstract TaskController createTaskController(ProximityModel model, TaskView view, Runnable onFinish);
    protected abstract String getIconResource();

    public void run() {
        Logger LOGGER = LogManager.getLogger(getClass());

        Settings settings;
        try {
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(Settings.class, new SettingsDeserializer())
                    .create();
            settings = gson.fromJson(new FileReader(getSettingsFile()), Settings.class);
        } catch (Exception e) {
            LOGGER.error("Failed to load settings: {}", e.getMessage());
            JOptionPane.showMessageDialog(null, e.getMessage(), getDefaultTitle(), JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (settings.appTitle == null) {
            settings.appTitle = getDefaultTitle();
        }

        final Settings finalSettings = settings;

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame(finalSettings.appTitle);
            frame.setSize(finalSettings.width, finalSettings.height);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);

            try {
                URL iconResource = getClass().getResource(getIconResource());
                if (iconResource != null) {
                    BufferedImage iconImage = ImageIO.read(iconResource);
                    frame.setIconImage(iconImage);
                }
            } catch (Exception e) {
                LOGGER.warn("Failed to load icon: {}", e.getMessage());
            }

            JPanel cards = new JPanel(new CardLayout());

            ProximityModel model = new ProximityModel(
                    finalSettings.terms,
                    ProximityModelType.SIMILARITIES,
                    finalSettings.decimals,
                    finalSettings.min,
                    finalSettings.max,
                    ProximityModelStructure.LOWER_TRIANGLE
            );

            InstructionsModel instructionsModel = new InstructionsModel(String.join(System.lineSeparator(), finalSettings.instructions));
            InstructionsModel debriefingModel = new InstructionsModel(String.join(System.lineSeparator(), finalSettings.debriefing));

            IDView idView = new IDView();
            InstructionsView instructionsView = new InstructionsView();
            InstructionsView debriefingView = new InstructionsView();

            TaskView taskView = createTaskView(finalSettings);

            cards.add(idView, "ID");
            cards.add(instructionsView, "INSTRUCTIONS");
            cards.add((JComponent) taskView, "TASK");
            cards.add(debriefingView, "DEBRIEFING");

            new IDController(model, idView, () -> switchCard(cards, "INSTRUCTIONS"));
            new InstructionsController(instructionsModel, instructionsView, () -> {
                switchCard(cards, "TASK");
                model.setInstant(Instant.now());
                onTaskStart(taskView);
            });

            TaskController controller = createTaskController(model, taskView, () -> {
                LOGGER.debug("Proximity data collected:\n{}", model);
                ProximityModelWriter writer = new ProximityModelWriter(model);
                try {
                    Path p = writer.write();
                    LOGGER.info("Proximity file written: {}", p.getFileName());
                } catch (IOException e) {
                    String msg = "Failed to save file: " + e.getMessage();
                    LOGGER.error(msg);
                    JOptionPane.showMessageDialog(null, msg, finalSettings.appTitle, JOptionPane.ERROR_MESSAGE);
                }
                switchCard(cards, "DEBRIEFING");
            });

            new InstructionsController(debriefingModel, debriefingView, frame::dispose);

            frame.add(cards);
            frame.setVisible(true);
        });
    }

    protected void switchCard(JPanel panel, String cardName) {
        ((CardLayout) panel.getLayout()).show(panel, cardName);
    }

    // Optional: hook to run code before showing task view
    protected void onTaskStart(TaskView view) {
        // Default is no-op
    }
}
