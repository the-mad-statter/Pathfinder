package com.themadstatter.pathfinder.data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ProximityModelWriter {
    private final ProximityModel model;

    public ProximityModelWriter(ProximityModel model) {
        this.model = model;
    }

    public Path write() throws IOException {
        Path p = Paths.get(model.getID() + ".prx");
        for(int i = 1; Files.exists(p); i++)
            p = Paths.get(model.getID() + i + ".prx");

        Files.writeString(p, model.toString());

        return p;
    }
}
