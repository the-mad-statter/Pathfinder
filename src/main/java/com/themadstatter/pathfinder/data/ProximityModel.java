package com.themadstatter.pathfinder.data;

import com.google.gson.Gson;

import java.text.DecimalFormat;
import java.time.Instant;

/**
 * This class represents proximity Model as used in
 * <a href="https://research-collective.com/PFWeb/index.php">Pathfinder Network Analysis software</a>.
 */
public class ProximityModel {
    private String id;
    private Instant instant;
    private long duration;
    private final String[] terms;
    private final ProximityModelType proximityModelType;
    private final int decimals;
    private final DecimalFormat decimalFormat;
    private final float min;
    private final float max;
    private final ProximityModelStructure proximityModelStructure;
    private final Float[][] proximities;

    public ProximityModel(ProximityModel model) {
        this.id = model.id;
        this.instant = model.instant;
        this.duration = model.duration;
        this.terms = model.terms;
        this.proximityModelType = model.proximityModelType;
        this.decimals = model.decimals;
        this.decimalFormat = model.decimalFormat;
        this.min = model.min;
        this.max = model.max;
        this.proximityModelStructure = model.proximityModelStructure;
        this.proximities = model.proximities;
    }

    /**
     * Constructor for a ProximityModel object
     * @param terms labels for network nodes
     * @param proximityModelType proximity data model type (i.e., similarities or dissimilarities)
     * @param decimals level of proximity data precision
     * @param min minimum proximity data value allowed
     * @param max maximum proximity data value allowed
     * @param proximityModelStructure proximity data model structure (i.e., lower triangle, matrix, etc...)
     */
    public ProximityModel(
            String[] terms,
            ProximityModelType proximityModelType,
            int decimals,
            float min,
            float max,
            ProximityModelStructure proximityModelStructure
    ) {
        this.terms = terms;
        this.proximityModelType = proximityModelType;
        
        this.decimals = decimals;
        StringBuilder sb = new StringBuilder();
        sb.append("0");
        if (decimals > 0)
            sb.append(".");
        sb.append("0".repeat(Math.max(0, decimals)));
        this.decimalFormat = new DecimalFormat(sb.toString());

        this.min = min;
        this.max = max;
        this.proximityModelStructure = proximityModelStructure;
        this.proximities = new Float[terms.length][terms.length];
    }

    /**
     * Set the instant at which the proximities data collection task commenced
     * @param instant instant at which the proximities data collection task commenced
     */
    public void setInstant(Instant instant) {
        this.instant = instant;
    }

    /**
     * Get id value
     * @return proximity data identification string
     */
    public String getID() {
        return id;
    }

    /**
     * Set id value
     * @param id proximity data identification string
     */
    public void setID(String id) {
        this.id = id;
    }

    /**
     * Set proximities data collection task duration
     * @param duration milliseconds during proximities data collection task
     */
    public void setDuration(long duration) {
      this.duration = duration;
    }

    /**
     * Testbed
     * @param args command line arguments
     */
    public static void main(String[] args) {
        String id = "123456789";
        Instant instant = Instant.now();
        long duration = 123;
        String[] terms = {"\"a\"", "b", "c"};
        ProximityModelType proximityModelType = ProximityModelType.SIMILARITIES;
        int decimals = 0;
        float min = 1f;
        float max = 9f;
        ProximityModelStructure proximityModelStructure = ProximityModelStructure.LOWER_TRIANGLE;

        ProximityModel proximityModel = new ProximityModel(
                terms,
                proximityModelType,
                decimals,
                min,
                max,
                proximityModelStructure
        );

        proximityModel.setID(id);

        proximityModel.setInstant(instant);

        for (int row = 0; row < terms.length; row++) {
            for (int col = 0; col < row; col++) {
                proximityModel.setProximity(row, col, row * 10 + col + 0.1235f);
            }
        }
        
        proximityModel.setDuration(duration);

        System.out.println(proximityModel);
    }

    public float getMin() { return min; }

    public float getMax() { return max; }

    /**
     * Get terms
     * @return string array of all terms
     */
    public String[] getTerms() {
        return terms;
    }

    /**
     * Get a single term by its index
     * @param i index of the term
     * @return string term
     */
    public String getTerm(int i) {
        return terms[i];
    }

    /**
     * Get the start time of the proximities data collection task
     * @return instant at which the proximities data collection task commenced
     */
    public Instant getInstant() {
        return instant;
    }

    /**
     * Set a single value in the proximities
     * @param row proximities row index
     * @param col proximities column index
     * @param proximity datum value to set
     */
    public void setProximity(int row, int col, float proximity) {
        proximities[row][col] = proximity;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("DATA:  ");
        sb.append(id);

        Gson gson = new Gson();
        ProximityModelMetadata metadata = new ProximityModelMetadata(instant.toString(), duration, terms);
        sb.append("    ");
        sb.append(gson.toJson(metadata));
        
        sb.append("\n");

        sb.append(proximityModelType);
        sb.append("\n");

        sb.append(terms.length);
        sb.append(" items");
        sb.append("\n");

        sb.append(decimals);
        sb.append(" decimals");
        sb.append("\n");

        sb.append(decimalFormat.format(min));
        sb.append(" min");
        sb.append("\n");

        sb.append(decimalFormat.format(max));
        sb.append(" max");
        sb.append("\n");

        sb.append(proximityModelStructure);
        sb.append("\n");

        int rowInitValue = 0;
        if (proximityModelStructure == ProximityModelStructure.LOWER_TRIANGLE)
            rowInitValue = 1;
        for (int row = rowInitValue; row < terms.length; row++) {
            for (int col = 0; col < terms.length; col++) {
                if (proximities[row][col] != null) {
                    sb.append(" ");
                    sb.append(decimalFormat.format(proximities[row][col]));
                }
            }
            sb.append("\n");
        }

        return sb.toString();
    }

    public int getCombinationCount() {
        return terms.length * (terms.length - 1) / 2;
    }

    public Integer indexOfTerm(String term) {
        for (int i = 0; i < terms.length; i++)
            if (term.equals(terms[i]))
                return i;
        return null;
    }
}