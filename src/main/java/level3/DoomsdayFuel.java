package level3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Doomsday Fuel
 * =============
 *
 * Making fuel for the LAMBCHOP's reactor core is a tricky process because of the exotic matter involved.
 * It starts as raw ore, then during processing, begins randomly changing between forms, eventually reaching a stable
 * form. There may be multiple stable forms that a sample could ultimately reach, not all of which are useful as fuel.
 *
 * Commander Lambda has tasked you to help the scientists increase fuel creation efficiency by predicting the end state
 * of a given ore sample. You have carefully studied the different structures that the ore can take and which
 * transitions it undergoes. It appears that, while random, the probability of each structure transforming is fixed.
 * That is, each time the ore is in 1 state, it has the same probabilities of entering the next state
 * (which might be the same state).
 * You have recorded the observed transitions in a matrix. The others in the lab have hypothesized more exotic forms
 * that the ore can become, but you haven't seen all of them.
 *
 * Write a function solution(m) that takes an array of array of nonnegative ints representing how many times that state
 * has gone to the next state and return an array of ints for each terminal state giving the exact probabilities
 * of each terminal state, represented as the numerator for each state, then the denominator for all of them
 * at the end and in simplest form. The matrix is at most 10 by 10. It is guaranteed that no matter which state
 * the ore is in, there is a path from that state to a terminal state. That is, the processing will always eventually
 * end in a stable state. The ore starts in state 0. The denominator will fit within a signed 32-bit integer during
 * the calculation, as long as the fraction is simplified regularly.
 *
 * For example, consider the matrix m:
 * [
 *   [0,1,0,0,0,1],  # s0, the initial state, goes to s1 and s5 with equal probability
 *   [4,0,0,3,2,0],  # s1 can become s0, s3, or s4, but with different probabilities
 *   [0,0,0,0,0,0],  # s2 is terminal, and unreachable (never observed in practice)
 *   [0,0,0,0,0,0],  # s3 is terminal
 *   [0,0,0,0,0,0],  # s4 is terminal
 *   [0,0,0,0,0,0],  # s5 is terminal
 * ]
 * So, we can consider different paths to terminal states, such as:
 * s0 -> s1 -> s3
 * s0 -> s1 -> s0 -> s1 -> s0 -> s1 -> s4
 * s0 -> s1 -> s0 -> s5
 * Tracing the probabilities of each, we find that
 * s2 has probability 0
 * s3 has probability 3/14
 * s4 has probability 1/7
 * s5 has probability 9/14
 * So, putting that together, and making a common denominator, gives an answer in the form of
 * [s2.numerator, s3.numerator, s4.numerator, s5.numerator, denominator] which is
 * [0, 3, 2, 9, 14].
 *
 * Languages
 * =========
 *
 * To provide a Java solution, edit Solution.java
 * To provide a Python solution, edit solution.py
 *
 * Test cases
 * ==========
 * Your code should pass the following test cases.
 * Note that it may also be run against hidden test cases not shown here.
 *
 * -- Java cases --
 * Input:
 * Solution.solution(
 * {
 *  {0, 2, 1, 0, 0},
 *  {0, 0, 0, 3, 4},
 *  {0, 0, 0, 0, 0},
 *  {0, 0, 0, 0, 0},
 *  {0, 0, 0, 0, 0}
 * })
 * Output:
 *     [7, 6, 8, 21]
 *
 * Input:
 * Solution.solution(
 * {
 *  {0, 1, 0, 0, 0, 1},
 *  {4, 0, 0, 3, 2, 0},
 *  {0, 0, 0, 0, 0, 0},
 *  {0, 0, 0, 0, 0, 0},
 *  {0, 0, 0, 0, 0, 0},
 *  {0, 0, 0, 0, 0, 0}
 * })
 * Output:
 *     [0, 3, 2, 9, 14]
 *
 * -- Python cases --
 * Input:
 * solution.solution([[0, 2, 1, 0, 0], [0, 0, 0, 3, 4], [0, 0, 0, 0, 0], [0, 0, 0, 0,0], [0, 0, 0, 0, 0]])
 * Output:
 *     [7, 6, 8, 21]
 *
 * Input:
 * solution.solution(
 * [
 *  [0, 1, 0, 0, 0, 1],
 *  [4, 0, 0, 3, 2, 0],
 *  [0, 0, 0, 0, 0, 0],
 *  [0, 0, 0, 0, 0, 0],
 *  [0, 0, 0, 0, 0, 0],
 *  [0, 0, 0, 0, 0, 0]
 * ])
 * Output:
 *     [0, 3, 2, 9, 14]
 */
public class DoomsdayFuel {

    private static class Rational {

        private final int numerator;
        private final int denominator;

        public Rational(double floatingNumber) {

            double tolerance = 1.0E-6;

            double numerator = 1;
            double tempNumerator = 0;

            double denominator = 0;
            double tempDenominator = 1;

            double approximateFloatingNumber = floatingNumber;

            do {

                double integralNumber = Math.floor(approximateFloatingNumber);

                double temp = numerator;
                numerator = (integralNumber * numerator) + tempNumerator;
                tempNumerator = temp;

                temp = denominator;
                denominator = (integralNumber * denominator) + tempDenominator;
                tempDenominator = temp;

                approximateFloatingNumber = 1 / (approximateFloatingNumber - integralNumber);

            } while (Math.abs(floatingNumber - (numerator / denominator)) > (floatingNumber * tolerance));

            this.numerator = (int) numerator;
            this.denominator = (int) denominator;
        }
    }

    public static int[] solution(int[][] m) {

        int firstRowSum = 0;
        for (int j = 0; j < m[0].length; j++) {
            firstRowSum += m[0][j];
        }

        if (firstRowSum == 0) {

            int[] result = new int[m[0].length + 1];
            result[0] = 1;
            result[m[0].length] = 1;
            return result;
        }

        final HashMap<Integer, Integer> nonAbsorbingStateWithSum = new HashMap<>();
        for (int i = 0; i < m.length; i++) {

            int rowSum = 0;
            for (int j = 0; j < m[0].length; j++) {
                rowSum += m[i][j];
            }
            if (rowSum != 0) {
                nonAbsorbingStateWithSum.put(i, rowSum);
            }
        }

        // Building R & Q Matrices of Markov chains
        double[][] R = new double[nonAbsorbingStateWithSum.size()][m.length - nonAbsorbingStateWithSum.size()];
        double[][] Q = new double[nonAbsorbingStateWithSum.size()][nonAbsorbingStateWithSum.size()];
        int ri = 0;
        int qi = 0;
        for (int i = 0; i < m.length; i++) {

           if (!nonAbsorbingStateWithSum.containsKey(i)) {
              continue;
           }

           int rj = 0;
           int qj = 0;
           for (int j = 0; j < m[0].length; j++) {

               if (!nonAbsorbingStateWithSum.containsKey(j)) {
                   R[ri][rj] = m[i][j] / (double) nonAbsorbingStateWithSum.get(i);
                   rj++;
               } else {
                   Q[qi][qj] = m[i][j] / (double) nonAbsorbingStateWithSum.get(i);
                   qj++;
               }
           }
           if (rj != 0) {
               ri++;
           }
           if (qj != 0) {
               qi++;
           }
        }

        // Building I of Markov chains
        double[][] identity = new double[nonAbsorbingStateWithSum.size()][nonAbsorbingStateWithSum.size()];
        for (int i = 0; i < nonAbsorbingStateWithSum.size(); i++) {

            identity[i][i] = 1;
        }

        double[][] F = invert(subtract(identity, Q));
        double[][] FR = multiply(F, R);

        final List<Rational> rationals = new ArrayList<>();
        int lcm = 1;

        // First Row of FR Gives the probabilities
        for (double value : FR[0]) {
            final Rational rational = new Rational(value);
            lcm = lcm(lcm, rational.denominator);
            rationals.add(rational);
        }

        // Common Sizing and generating output
        final int[] results = new int[FR[0].length + 1];
        for (int i = 0; i < rationals.size(); i++) {
            final Rational rational = rationals.get(i);
            int multiple = lcm / rational.denominator;
            results[i] = rational.numerator * multiple;
        }
        results[FR[0].length] = lcm;

        return results;
    }

    private static double[][] multiply(final double[][] a, double[][] b) {

        double[][] multiply = new double[a.length][b[0].length];

        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < b.length; k++) {
                    multiply[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return multiply;
    }

    private static double[][] subtract(final double[][] a, double[][] b) {

        double[][] minus = new double[a.length][a[0].length];
        for (int i = 0; i < a.length; i++) {

            for (int j = 0; j < a[0].length; j++) {

                minus[i][j] = a[i][j] - b[i][j];
            }
        }

        return minus;
    }

    public static double[][] invert(double[][] a) {
        int n = a.length;
        double[][] x = new double[n][n];
        double[][] b = new double[n][n];
        int[] index = new int[n];
        for (int i=0; i<n; ++i) {
            b[i][i] = 1;
        }

        gaussian(a, index);

        // Update the matrix b[i][j] with the ratios stored
        for (int i = 0; i < n-1; i++) {

            for (int j = i+1; j < n; j++) {

                for (int k = 0; k < n; k++) {

                    b[index[j]][k] -= a[index[j]][i] * b[index[i]][k];
                }
            }
        }

        // Perform backward substitutions
        for (int i = 0; i < n; i++) {

            x[n-1][i] = b[index[n-1]][i] / a[index[n-1]][n-1];

            for (int j = n-2; j >= 0; j--) {

                x[j][i] = b[index[j]][i];

                for (int k = j + 1; k < n; k++) {

                    x[j][i] -= a[index[j]][k] * x[k][i];
                }

                x[j][i] /= a[index[j]][j];
            }
        }
        return x;
    }

    private static void gaussian(double[][] a, int[] index) {

        int n = index.length;
        double[] c = new double[n];

        // Initialize the index
        for (int i=0; i<n; ++i) {
            index[i] = i;
        }


        // Find the rescaling factors, one from each row
        for (int i=0; i<n; ++i) {

            double c1 = 0;
            for (int j=0; j<n; ++j) {

                double c0 = Math.abs(a[i][j]);
                if (c0 > c1) c1 = c0;
            }
            c[i] = c1;
        }

        // Search the pivoting element from each column
        int k = 0;
        for (int j=0; j<n-1; ++j) {

            double pi1 = 0;
            for (int i=j; i<n; ++i) {

                double pi0 = Math.abs(a[index[i]][j]);
                pi0 /= c[index[i]];

                if (pi0 > pi1) {
                    pi1 = pi0;
                    k = i;
                }
            }

            // Interchange rows according to the pivoting order
            int itmp = index[j];
            index[j] = index[k];
            index[k] = itmp;
            for (int i= j+1; i < n; i++) {

                double pj = a[index[i]][j] / a[index[j]][j];

                // Record pivoting ratios below the diagonal
                a[index[i]][j] = pj;

                // Modify other elements accordingly
                for (int l = j+1; l < n; l++) {
                    a[index[i]][l] -= pj * a[index[j]][l];
                }
            }
        }
    }

    public static int lcm(int a, int b) {

        int lcm = Math.max(a, b);

        // Always true
        while (lcm % a != 0 || lcm % b != 0) {

            ++lcm;
        }

        return lcm;
    }

    public static void main(String[] args) {

        System.out.println(
            Arrays.toString(level3.DoomsdayFuel.solution(
                new int[][]{
                    {0, 2, 1, 0, 0},
                    {0, 0, 0, 3, 4},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0}
                }
            ))
        );
    }
}
