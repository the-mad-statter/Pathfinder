package com.themadstatter.pathfinder.measure;

import javax.swing.*;

public abstract class AbstractPairwiseTaskScale extends JPanel {
    public abstract Float getProximity();

    public abstract void clearProximity();
}
