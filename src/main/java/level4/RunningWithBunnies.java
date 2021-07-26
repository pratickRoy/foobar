package level4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Running with Bunnies
 * ====================
 *
 * You and the bunny workers need to get out of this collapsing death trap of a space station -- and fast!
 * Unfortunately, some of the bunnies have been weakened by their long work shifts and can't run very fast.
 * Their friends are trying to help them, but this escape would go a lot faster if you also pitched in.
 * The defensive bulkhead doors have begun to close, and if you don't make it through in time, you'll be trapped!
 * You need to grab as many bunnies as you can and get through the bulkheads before they close.
 *
 * The time it takes to move from your starting point to all of the bunnies and to the bulkhead will be given to you
 * in a square matrix of integers. Each row will tell you the time it takes to get to the start, first bunny,
 * second bunny, ..., last bunny, and the bulkhead in that order. The order of the rows follows the same pattern
 * (start, each bunny, bulkhead). The bunnies can jump into your arms, so picking them up is instantaneous,
 * and arriving at the bulkhead at the same time as it seals still allows for a successful, if dramatic, escape.
 * (Don't worry, any bunnies you don't pick up will be able to escape with you since they no longer have to carry
 * the ones you did pick up.) You can revisit different spots if you wish, and moving to the bulkhead doesn't mean
 * you have to immediately leave -- you can move to and from the bulkhead to pick up additional bunnies if time
 * permits.
 *
 * In addition to spending time traveling between bunnies, some paths interact with the space station's security
 * checkpoints and add time back to the clock. Adding time to the clock will delay the closing of the bulkhead doors,
 * and if the time goes back up to 0 or a positive number after the doors have already closed, it triggers the
 * bulkhead to reopen. Therefore, it might be possible to walk in a circle and keep gaining time: that is, each time
 * a path is traversed, the same amount of time is used or added.
 *
 * Write a function of the form solution(times, time_limit) to calculate the most bunnies you can pick up and which
 * bunnies they are, while still escaping through the bulkhead before the doors close for good. If there are
 * multiple sets of bunnies of the same size, return the set of bunnies with the lowest worker IDs (as indexes)
 * in sorted order. The bunnies are represented as a sorted list by worker ID, with the first bunny being 0.
 * There are at most 5 bunnies, and time_limit is a non-negative integer that is at most 999.
 *
 * For instance, in the case of
 * [
 *   [0, 2, 2, 2, -1],  # 0 = Start
 *   [9, 0, 2, 2, -1],  # 1 = Bunny 0
 *   [9, 3, 0, 2, -1],  # 2 = Bunny 1
 *   [9, 3, 2, 0, -1],  # 3 = Bunny 2
 *   [9, 3, 2, 2,  0],  # 4 = Bulkhead
 * ]
 * and a time limit of 1, the five inner array rows designate the starting point, bunny 0, bunny 1, bunny 2,
 * and the bulkhead door exit respectively. You could take the path:
 *
 * Start End Delta Time Status
 *     -   0     -    1 Bulkhead initially open
 *     0   4    -1    2
 *     4   2     2    0
 *     2   4    -1    1
 *     4   3     2   -1 Bulkhead closes
 *     3   4    -1    0 Bulkhead reopens; you and the bunnies exit
 *
 * With this solution, you would pick up bunnies 1 and 2. This is the best combination for this space station
 * hallway, so the solution is [1, 2].
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
 * Solution.solution({{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}}, 3)
 * Output:
 *     [0, 1]
 *
 * Input:
 * Solution.solution({{0, 2, 2, 2, -1}, {9, 0, 2, 2, -1}, {9, 3, 0, 2, -1}, {9, 3, 2, 0, -1}, {9, 3, 2, 2, 0}}, 1)
 * Output:
 *     [1, 2]
 *
 * -- Python cases --
 * Input:
 * solution.solution([[0, 2, 2, 2, -1], [9, 0, 2, 2, -1], [9, 3, 0, 2, -1], [9, 3, 2, 0, -1], [9, 3, 2, 2, 0]], 1)
 * Output:
 *     [1, 2]
 *
 * Input:
 * solution.solution([[0, 1, 1, 1, 1], [1, 0, 1, 1, 1], [1, 1, 0, 1, 1], [1, 1, 1, 0, 1], [1, 1, 1, 1, 0]], 3)
 * Output:
 *     [0, 1]
 */
public class RunningWithBunnies {

    public static class FloydWarshall {

        private final Integer[][] allPairShortestPathMatrix;
        private final Integer[][] pathFinderMatrix;

        public FloydWarshall(final Integer[][] matrix) {

            if (matrix.length != matrix[0].length) {
                throw new IllegalArgumentException("Bad Adj Matrix Passed");
            }

            allPairShortestPathMatrix = new Integer[matrix.length][matrix[0].length];
            pathFinderMatrix = new Integer[matrix.length][matrix[0].length];
            for(int i = 0; i < matrix.length; i++) {
                for(int j = 0; j < matrix[i].length; j++) {
                    allPairShortestPathMatrix[i][j] = matrix[i][j];
                    if(matrix[i][j] != null) {
                        pathFinderMatrix[i][j] = j;
                    }
                }
            }
            findAllPairsShortestPath(matrix);
        }

        public boolean doesMatrixContainNegativeCycles() {

            for (int i = 0; i < allPairShortestPathMatrix.length; i++) {
                if (allPairShortestPathMatrix[i][i] < 0) {
                    return true;
                }
            }
            return false;
        }

        private void findAllPairsShortestPath(final Integer[][] matrix) {

            for(int k = 0; k < matrix.length; k++) {
                for(int i = 0; i < matrix.length; i++) {
                    for(int j = 0; j < matrix[i].length; j++) {

                        final Integer directPathCost = allPairShortestPathMatrix[i][j];
                        final Integer indirectPathToIntermediateCost = allPairShortestPathMatrix[i][k];
                        final Integer indirectPathFromIntermediateCost = allPairShortestPathMatrix[k][j];

                        if (indirectPathToIntermediateCost != null && indirectPathFromIntermediateCost != null) {
                            if (directPathCost == null ||
                                (directPathCost > indirectPathToIntermediateCost + indirectPathFromIntermediateCost)) {
                                allPairShortestPathMatrix[i][j] = allPairShortestPathMatrix[i][k] + allPairShortestPathMatrix[k][j];
                                pathFinderMatrix[i][j] = pathFinderMatrix[i][k];
                            }
                        }
                    }
                }
            }
        }
    }

    public static int[] solution(int[][] times, int times_limit) {

        final Integer[][] matrix = new Integer[times.length][times[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = times[i][j];
            }
        }
        final FloydWarshall floydWarshall = new FloydWarshall(matrix);

        final Set<Integer> bunniesLeft = IntStream
            .range(1, matrix.length - 1)
            .boxed()
            .collect(Collectors.toSet());

        if (floydWarshall.doesMatrixContainNegativeCycles()) {

            return bunniesLeft
                .stream()
                .mapToInt(bunny -> bunny - 1)
                .toArray();
        }

        List<Integer[]> paths = generateAllPossiblePaths(matrix);

        for (Integer[] path : paths) {

            int time = 0;
            int currentNode = 0;
            for (Integer node : path) {
                int nextNode = node + 1;
                time += floydWarshall.allPairShortestPathMatrix[currentNode][nextNode];
                currentNode = nextNode;
            }
            time += floydWarshall.allPairShortestPathMatrix[currentNode][matrix.length - 1];

            if (time <= times_limit) {
                return Arrays
                    .stream(path)
                    .sorted(Comparator.comparingInt(o -> o))
                    .mapToInt(i -> i)
                    .toArray();
            }
        }

        return new int[]{};
    }

    // Not Clean Code, but does the job
    private static List<Integer[]> generateAllPossiblePaths(final Integer[][] matrix) {

        final ArrayList<Integer[]> allPaths = new ArrayList<>();
        int maxNodes = matrix.length - 2;
        for (int noOfNodes = maxNodes; noOfNodes >= 1; noOfNodes--) {

            //Integer[] path = new Integer[noOfNodes];
            for (int i = 0; i <= maxNodes - 1; i++) {

                if (noOfNodes > 1) {
                    for (int j = 0; j <= maxNodes - 1; j++) {

                        if (noOfNodes > 2) {
                            for (int k = 0; k <= maxNodes - 1; k++) {

                                if (noOfNodes > 3) {
                                    for (int l = 0; l <= maxNodes - 1; l++) {
                                        if (noOfNodes > 4) {

                                            for (int m = 0; m <= maxNodes - 1; m++) {
                                                addDistinct(allPaths, i,j,k,l,m);
                                            }

                                        } else {
                                            addDistinct(allPaths, i,j,k,l);
                                        }

                                    }
                                } else {
                                    addDistinct(allPaths, i,j,k);
                                }
                            }
                        } else {
                            addDistinct(allPaths, i,j);
                        }
                    }
                } else {
                    addDistinct(allPaths, i);
                }
            }
        }

        return allPaths;
    }

    private static void addDistinct(ArrayList<Integer[]> allPaths,
                                    Integer... path) {

        if (new HashSet<>(Arrays.asList(path)).size() == path.length) {
            allPaths.add(path);
        }
    }

    public static void main(String[] args) {

        System.out.println(
            Arrays.toString(
                solution(
                    new int[][]{
                        //S  0   1   2   3   4   E
                        { 0, 1, -1,  2, -3, -2,  0}, // S
                        { 2, 0,  1,  4, -1,  0,  2}, // 0
                        { 5, 6,  0,  7,  2,  3,  5}, // 1
                        {-1, 0, -2,  0, -4, -3, -1}, // 2
                        { 8, 9,  7, 10,  0,  6,  8}, // 3
                        { 4, 5,  3,  6,  1,  0,  4}, // 4
                        { 0, 1, -1,  2, -3,  2,  0}  // E
                    },
                    6
                )
            )
        );

        System.out.println(
            Arrays.toString(
                solution(
                    new int[][]{
                        {0, 2, 2, 2, -1},
                        {9, 0, 2, 2, -1},
                        {9, 3, 0, 2, -1},
                        {9, 3, 2, 0, -1},
                        {9, 3, 2, 2, 0}
                    },
                    1
                )
            )
        );

        System.out.println(
            Arrays.toString(
                solution(
                    new int[][]{
                        {0, 2, 2, 2, -1},
                        {9, 0, 2, 2, -1},
                        {9, 3, 0, 2, -1},
                        {9, 3, 2, 0, -1},
                        {9, 3, 2, 2, 0}
                    },
                    1
                )
            )
        );
    }
}
