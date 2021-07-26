package homework;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DFS {

    public DFS(final Integer[][] matrix) {

        List<Integer[]> list = generateAllPossiblePaths(matrix);

        for (int index = 0; index < list.size(); index++) {

            int time = 0;
            int currentNode = 0;
            for (Integer node : list.get(index)) {
                int nextNode = node + 1;
                time += matrix[currentNode][nextNode];
                currentNode = nextNode;
            }
            time +=  matrix[currentNode][matrix.length - 1];
            if (time <= 6) {
                System.out.println(Arrays.toString(list.get(index)));
                break;
            }
        }
    }

    public List<Integer[]> generateAllPossiblePaths(final Integer[][] matrix) {

        final ArrayList<Integer[]> allPaths = new ArrayList<>();
        for (int noOfNodes = matrix.length - 2; noOfNodes >= 1; noOfNodes--) {

            //Integer[] path = new Integer[noOfNodes];
            for (int i = 0; i <= 4; i++) {

                if (noOfNodes > 1) {
                    for (int j = i + 1; j <= 4; j++) {

                        if (noOfNodes > 2) {
                            for (int k = j + 1; k <= 4; k++) {

                                if (noOfNodes > 3) {
                                    for (int l = k + 1; l <= 4; l++) {
                                        allPaths.add(new Integer[]{i,j,k,l});
                                    }
                                } else {
                                    allPaths.add(new Integer[]{i,j,k});
                                }
                            }
                        } else {
                            allPaths.add(new Integer[]{i,j});
                        }
                    }
                } else {
                    allPaths.add(new Integer[]{i});
                }
            }
        }

        return allPaths;
    }

    public static void main(String[] args) {

        final DFS dfs = new DFS(
            new Integer[][]{
                {0, 2, 2, 2, -1},
                {9, 0, 2, 2, -1},
                {9, 3, 0, 2, -1},
                {9, 3, 2, 0, -1},
                {9, 3, 2, 2, 0}
            }
        );
    }
}
