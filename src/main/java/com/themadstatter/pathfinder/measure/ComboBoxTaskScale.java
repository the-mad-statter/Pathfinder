package com.themadstatter.pathfinder.measure;

import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class ComboBoxTaskScale extends JPanel {
    private final int[] term_order;
    private final JComboBox<String>[][] boxes;

    @SuppressWarnings("unchecked")
    public ComboBoxTaskScale(String[] terms) {
        ToolTipManager.sharedInstance().setDismissDelay(10000);
        ToolTipManager.sharedInstance().setInitialDelay(0);

        term_order = initSequence(terms.length);
        shuffleArray(term_order);

        boxes = new JComboBox[terms.length][terms.length];

        setLayout(new GridLayout(terms.length + 1, terms.length + 1));

        // Top-left corner (empty)
        add(new JPanel());

        // Column headers (rotated)
        for (int j : term_order) {
            JPanel panel = new JPanel(new GridBagLayout());

            JLabel label = new JLabel(terms[j]);
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setVerticalAlignment(SwingConstants.BOTTOM);
            label.setUI(new VerticalLabelUI(false));
            panel.add(label);

            panel.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));

            add(panel);
        }

        // Main grid
        for (int row = 0; row < term_order.length; row++) {
            // Row header
            JPanel rowHeader = new JPanel(new GridBagLayout());
            rowHeader.add(new JLabel(terms[term_order[row]]));
            if (row % 2 == 0) rowHeader.setBackground(Color.LIGHT_GRAY);
            add(rowHeader);

            for (int col = 0; col < terms.length; col++) {
                JPanel cell = new JPanel(new GridBagLayout());

                if (row % 2 == 0) cell.setBackground(Color.LIGHT_GRAY);

                cell.setBorder(BorderFactory.createMatteBorder(0, 1, 0, 0, Color.BLACK));

                if (col < row) {
                    JComboBox<String> box = initBox(terms, row, col);
                    boxes[row][col] = box;
                    cell.add(box);
                }

                add(cell);
            }
        }
    }

    private JComboBox<String> initBox(String[] terms, int row, int col) {
        JComboBox<String> box = new JComboBox<>();
        box.addItem("-");
        for (int i = 1; i <= 9; i++) {
            box.addItem(String.valueOf(i));
        }
        String html = String.format("<html>%s<br/><br/>%s</html>", terms[term_order[row]], terms[term_order[col]]);
        box.setToolTipText(html);
        box.setMaximumRowCount(1 + 9);
        return box;
    }

    private static void shuffleArray(int[] array) {
        Random rand = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1); // random index from 0 to i
            // Swap array[i] with array[j]
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    private static int[] initSequence(int to) {
        int[] x = new int[to];
        for (int i = 0; i < to; i++)
            x[i] = i;
        return x;
    }

    private static int indexOf(int[] array, int target) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == target) {
                return i;
            }
        }
        return -1;
    }

    public Float getProximity(int row, int col) {
        int displayRow = indexOf(term_order, row);
        int displayCol = indexOf(term_order, col);

        // Ensure row > col
        if (displayRow < displayCol) {
            int temporaryRow = displayRow;
            displayRow = displayCol;
            displayCol = temporaryRow;
        }

        JComboBox<String> box = boxes[displayRow][displayCol];
        if (box != null && box.getSelectedIndex() != 0) {
            String s = (String) box.getSelectedItem();
            assert s != null;
            return Float.valueOf(s);
        } else {
            return null;
        }
    }

    public boolean allProximitiesNotNull() {
        for(int row = 0; row < term_order.length; row++)
            for(int col = 0; col < row; col++)
                if (getProximity(row, col) == null) return false;

        return true;
    }
}
