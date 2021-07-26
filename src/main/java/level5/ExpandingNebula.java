package level5;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Expanding Nebula
 * ================
 *
 * You've escaped Commander Lambda's exploding space station along with numerous escape pods full of bunnies.
 * But -- oh no! -- one of the escape pods has flown into a nearby nebula, causing you to lose track of it.
 * You start monitoring the nebula, but unfortunately, just a moment too late to find where the pod went.
 * However, you do find that the gas of the steadily expanding nebula follows a simple pattern, meaning that you
 * should be able to determine the previous state of the gas and narrow down where you might find the pod.
 *
 * From the scans of the nebula, you have found that it is very flat and distributed in distinct patches,
 * so you can model it as a 2D grid. You find that the current existence of gas in a cell of the grid is
 * determined exactly by its 4 nearby cells, specifically, (1) that cell, (2) the cell below it, (3) the cell
 * to the right of it, and (4) the cell below and to the right of it. If, in the current state, exactly 1 of
 * those 4 cells in the 2x2 block has gas, then it will also have gas in the next state. Otherwise, the cell
 * will be empty in the next state.
 *
 * For example, let's say the previous state of the grid (p) was:
 * .O..
 * ..O.
 * ...O
 * O...
 *
 * To see how this grid will change to become the current grid (c) over the next time step, consider the
 * 2x2 blocks of cells around each cell.  Of the 2x2 block of [p[0][0], p[0][1], p[1][0], p[1][1]], only
 * p[0][1] has gas in it, which means this 2x2 block would become cell c[0][0] with gas in the next time step:
 * .O -> O
 * ..
 *
 * Likewise, in the next 2x2 block to the right consisting of [p[0][1], p[0][2], p[1][1], p[1][2]], two of
 * the containing cells have gas, so in the next state of the grid, c[0][1] will NOT have gas:
 * O. -> .
 * .O
 *
 * Following this pattern to its conclusion, from the previous state p, the current state of the grid c will be:
 * O.O
 * .O.
 * O.O
 *
 * Note that the resulting output will have 1 fewer row and column, since the bottom and rightmost cells do
 * not have a cell below and to the right of them, respectively.
 *
 * Write a function solution(g) where g is an array of array of bools saying whether there is gas in
 * each cell (the current scan of the nebula), and return an int with the number of possible previous states
 * that could have resulted in that grid after 1 time step.  For instance, if the function were given the
 * current state c above, it would deduce that the possible previous states were p (given above) as well
 * as its horizontal and vertical reflections, and would return 4. The width of the grid will be between
 * 3 and 50 inclusive, and the height of the grid will be between 3 and 9 inclusive.  The solution will always
 * be less than one billion (10^9).
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
 * Solution.solution({
 *    {true, true, false, true, false, true, false, true, true, false},
 *    {true, true, false, false, false, false, true, true, true, false},
 *    {true, true, false, false, false, false, false, false, false, true},
 *    {false, true, false, false, false, false, true, true, false, false}}
 * )
 * Output:
 *     11567
 *
 * Input:
 * Solution.solution({{true, false, true}, {false, true, false}, {true, false, true}})
 * Output:
 *     4
 *
 * Input:
 * Solution.solution({
 *     {true, false, true, false, false, true, true, true},
 *     {true, false, true, false, false, false, true, false},
 *     {true, true, true, false, false, false, true, false},
 *     {true, false, true, false, false, false, true, false},
 *     {true, false, true, false, false, true, true, true}}
 * )
 *
 * Output:
 *     254
 *
 * -- Python cases --
 * Input:
 * solution.solution([
 *  [True, True, False, True, False, True, False, True, True, False],
 *  [True, True, False, False, False, False, True, True, True, False],
 *  [True, True, False, False, False, False, False, False, False, True],
 *  [False, True, False, False, False, False, True, True, False, False]]
 * )
 * Output:
 *     11567
 *
 * Input:
 * solution.solution([[True, False, True], [False, True, False], [True, False, True]])
 * Output:
 *     4
 *
 * Input:
 * solution.solution([
 *  [True, False, True, False, False, True, True, True],
 *  [True, False, True, False, False, False, True, False],
 *  [True, True, True, False, False, False, True, False],
 *  [True, False, True, False, False, False, True, False],
 *  [True, False, True, False, False, True, True, True]]
 * )
 * Output:
 *     254
 */
public class ExpandingNebula {

    private static class PreImage {

        private int count;
        private final int leftMask;
        private final int rightMask;
        private final int topMask;
        private final int bottomMask;

        public PreImage(int leftMask, int rightMask, int topMask, int bottomMask) {
            this.count = 1;
            this.leftMask = leftMask;
            this.rightMask = rightMask;
            this.topMask = topMask;
            this.bottomMask = bottomMask;
        }

        public PreImage(int count, int bottomMask) {
            this.count = count;
            this.leftMask = -1;
            this.rightMask = -1;
            this.topMask = -1;
            this.bottomMask = bottomMask;
        }

        public PreImage increment(int count) {
            this.count += count;
            return this;
        }
    }

    private static final List<PreImage> presentMask = Arrays.asList(
        new PreImage(0b00, 0b01, 0b00, 0b01),
        new PreImage(0b01, 0b00, 0b00, 0b10),
        new PreImage(0b00, 0b10, 0b01, 0b00),
        new PreImage(0b10, 0b00, 0b10, 0b00)
    );

    private static final List<PreImage> absentMask = Arrays.asList(
        new PreImage(0b00, 0b00, 0b00, 0b00),
        new PreImage(0b01, 0b01, 0b00, 0b11),
        new PreImage(0b00, 0b11, 0b01, 0b01),
        new PreImage(0b01, 0b10, 0b01, 0b10),
        new PreImage(0b01, 0b11, 0b01, 0b11),
        new PreImage(0b10, 0b01, 0b10, 0b01),
        new PreImage(0b11, 0b00, 0b10, 0b10),
        new PreImage(0b11, 0b01, 0b10, 0b11),
        new PreImage(0b10, 0b10, 0b11, 0b00),
        new PreImage(0b10, 0b11, 0b11, 0b01),
        new PreImage(0b11, 0b10, 0b11, 0b10),
        new PreImage(0b11, 0b11, 0b11, 0b11)
    );

    private static int count = 0;

    public static int solution(boolean[][] g) {

        final boolean[][] transpose = transpose(g);
        List<PreImage> preimages = getAllowedPreimagesForRow(transpose[0]);
        for (int i = 1; i < transpose.length; i++) {
            preimages = getAllowedPreimagesCombiningColumns(
                preimages,
                getAllowedPreimagesForRow(transpose[i])
            );
        }
        return count;
    }

    private static boolean[][] transpose(boolean[][] matrix) {

        boolean[][] transpose = new boolean[matrix[0].length][matrix.length];

        for (int i = 0; i < matrix[0].length; i++) {

            for (int j = 0; j < matrix.length; j++) {

                transpose[i][j] = matrix[j][i];
            }
        }

        return transpose;
    }

    private static List<PreImage> getAllowedPreimagesForRow(final boolean[] row) {

        List<PreImage> allowedPreimages = getPreimages(row[0]);
        for (int i = 1; i < row.length; i++) {

            List<PreImage> newAllowedPreImages = new ArrayList<>();
            List<PreImage> rightAllowedPreimages = getPreimages(row[i]);

            for (PreImage leftImage : allowedPreimages) {

                for (PreImage rightImage : rightAllowedPreimages) {

                    if (leftImage.rightMask == rightImage.leftMask) {

                        newAllowedPreImages.add(
                            new PreImage(
                                -1,
                                rightImage.rightMask,
                                (leftImage.topMask * 100) + rightImage.topMask,
                                (leftImage.bottomMask * 100) + rightImage.bottomMask
                            )
                        );
                    }
                }
            }

            allowedPreimages = newAllowedPreImages;
        }

        return allowedPreimages;
    }

    private static List<PreImage> getAllowedPreimagesCombiningColumns(final List<PreImage> topPreimages,
                                                                      final List<PreImage> bottomPreimages) {

        count = 0;

        List<PreImage> allowedPreimages = new ArrayList<>();
        final Map<Integer, PreImage> topPreimagesMaskMap = topPreimages
            .stream()
            .collect(Collectors.toMap(
                preImage -> preImage.bottomMask,
                preImage -> preImage,
                (preImage1, preImage2) -> preImage1.increment(preImage2.count)
            ));

        for (PreImage bottomImage : bottomPreimages) {

            PreImage topImage = topPreimagesMaskMap.getOrDefault(bottomImage.topMask, null);
            if (topImage != null) {

                count += topImage.count;
                allowedPreimages.add(
                    new PreImage(
                        topImage.count,
                        bottomImage.bottomMask
                    )
                );
            }
        }

        return allowedPreimages;
    }

    private static List<PreImage> getPreimages(boolean cell) {

        if (cell) {
            return presentMask;
        }
        return absentMask;
    }

    public static void main(String[] args) {

        /*System.out.println(
            solution(
                new boolean[][] {
                    {true, true, false, true, false, true, false, true, true, false},
                    {true, true, false, false, false, false, true, true, true, false},
                    {true, true, false, false, false, false, false, false, false, true},
                    {false, true, false, false, false, false, true, true, false, false}
                }
            )
        );*/

        System.out.println(solution(
            new boolean[][]{
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true},
                {false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, true, true, true},
                {false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false, false, false, false, false, false, true, true, true, true, true, true, true, true, false, false, false},
                {false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false, false, true, true, true, true, false, false, false},
                {false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, true, true, false, false, false},
                {true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, false, true, true}

            }
        ));
    }
}
