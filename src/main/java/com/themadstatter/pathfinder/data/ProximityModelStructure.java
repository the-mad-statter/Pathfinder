package com.themadstatter.pathfinder.data;

public enum ProximityModelStructure {
  MATRIX("matrix"),
  UPPER_TRIANGLE("upper triangle"),
  LOWER_TRIANGLE("lower triangle"),
  LIST("list");

  private final String displayName;
  
  ProximityModelStructure(String displayName) {
    this.displayName = displayName;
  }
  
  @Override public String toString() {
    return displayName;
  }
}
