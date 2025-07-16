package com.themadstatter.pathfinder.data;

public enum ProximityModelType {
  SIMILARITIES("similarities"), 
  DISSIMILARITIES("dissimilarities"),
  PROBABILITIES("probabilities"),
  DISTANCES("distances");
    
  private final String displayName;
    
  ProximityModelType(String displayName) {
    this.displayName = displayName;
  }
  
  public String toString() {
    return displayName;
  }
}
