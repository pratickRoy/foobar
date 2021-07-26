package homework;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FloydWarshall {

    private Integer[][] allPairShortestPathMatrix;
    private Integer[][] pathFinderMatrix;

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

    private boolean doesMatrixContainNegativeCycles() {

        for (int i = 0; i < allPairShortestPathMatrix.length; i++) {
            if (allPairShortestPathMatrix[i][i] < 0) {
                return true;
            }
        }
        return false;
    }

    public void printAllPairsShortestPath() {

        printMatrix(allPairShortestPathMatrix);
    }

    public void printPathFinderMatrix() {

        printMatrix(pathFinderMatrix);
    }

    public List<Integer> findShortestPathBetweenNodes(final int startNode, final int endNode) {

        final List<Integer> path = new ArrayList<>();
        Integer currentNode = startNode;
        path.add(startNode);

        while (currentNode != endNode) {

            currentNode = pathFinderMatrix[currentNode][endNode];
            if (currentNode == null || allPairShortestPathMatrix[currentNode][currentNode] < 0) {
                return Collections.emptyList();
            }
            path.add(currentNode);
        }

        return new ArrayList<>(path);
    }

    private void printMatrix(final Integer[][] matrix) {

        for(int i = 0; i < matrix.length; i++) {
            for(int j = 0; j < matrix[i].length; j++) {
                System.out.print(matrix[i][j] + "\t");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {

        final FloydWarshall floydWarshall1 = new FloydWarshall(
            new Integer[][]{
                //S  0   1   2   3   4   E
                { 0, 1, -1,  2, -3, -2,  0}, // S
                { 2, 0,  1,  4, -1,  0,  2}, // 0
                { 5, 6,  0,  7,  2,  3,  5}, // 1
                {-1, 0, -2,  0, -4, -3, -1}, // 2
                { 8, 9,  7, 10,  0,  6,  8}, // 3
                { 4, 5,  3,  6,  1,  0,  4}, // 4
                { 0, 1, -1,  2, -3,  2,  0}  // E
            }
        );

        floydWarshall1.printAllPairsShortestPath();
        System.out.println();
        floydWarshall1.printPathFinderMatrix();
        System.out.println(floydWarshall1.findShortestPathBetweenNodes(0, 3));
        System.out.println(floydWarshall1.findShortestPathBetweenNodes(3, 6));
        System.out.println(floydWarshall1.doesMatrixContainNegativeCycles());

        /*final FloydWarshall floydWarshall2 = new FloydWarshall(
            new Integer[][]{
                {0,   5,  null, 10},
                {null,  0,  3,  null},
                {null, null, 0,   1},
                {null, null, null, 0}
            }
        );

        floydWarshall2.printAllPairsShortestPath();
        System.out.println();
        floydWarshall2.printPathFinderMatrix();
        System.out.println(floydWarshall2.findShortestPathBetweenNodes(0, 3));
        System.out.println(floydWarshall2.doesMatrixContainNegativeCycles());

        final FloydWarshall floydWarshall3 = new FloydWarshall(
            new Integer[][]{
                {0, 1, 2, 2, 5},
                {9, 0, 10, 2, 0},
                {9, 3, -5, 2, 0},
                {9, 3, 2, 0, 0},
                {9, 3, 2, 2, 0},
            }
        );

        floydWarshall3.printAllPairsShortestPath();
        System.out.println();
        floydWarshall3.printPathFinderMatrix();
        System.out.println(floydWarshall3.findShortestPathBetweenNodes(0, 2));
        System.out.println(floydWarshall3.doesMatrixContainNegativeCycles());*/
    }
}
