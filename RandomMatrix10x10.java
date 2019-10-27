import javax.swing.*; // JFrame, JPanel, ...
import java.awt.*; // GridLayout

public class RandomMatrix10x10 extends JFrame { // This is the window class
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    public static class RandomNumber extends JPanel { // This is the random number grid space class
        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public RandomNumber() {
            JTextArea area = new JTextArea(); // This will contain the number
            area.setText(Double.toString(Math.random())); // This puts the number in place
            area.setEditable(false); // This prevents the user from changing the matrix
            this.add(area); // This puts the number into the gridspace
        }
    }

    public RandomMatrix10x10() {
        this.setLayout(new GridLayout(10, 10)); // This makes the frame into a 10 x 10 grid
        for (int i = 0; i < 25; i++) {
            this.add(new RandomNumber()); // This puts all 100 numbers in place
        }
    }
}