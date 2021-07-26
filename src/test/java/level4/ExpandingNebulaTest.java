package level4;

import org.junit.Assert;
import org.junit.Test;

public class ExpandingNebulaTest {

    @Test
    // S - 0 - 1 - E
    // 3 - 2 - 1 - 0
    // If there are multiple sets of bunnies of the same size, return the set of bunnies with the lowest prisoner IDs (as indexes) in sorted order
    public void caseATest() {
        int[][] times = new int[][]{
            //S 0  1  2  E
            {0, 1, 1, 1, 1}, // S
            {1, 0, 1, 1, 1}, // 0
            {1, 1, 0, 1, 1}, // 1
            {1, 1, 1, 0, 1}, // 2
            {1, 1, 1, 1, 0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 1}, RunningWithBunnies.solution(times, 3));
    }

    @Test
    // S - E - 1 - E - 2 - E
    // 1 - 2 - 0 - 1 - (-1) - 0
    public void caseBTest() {
        int[][] times = new int[][]{
            //S 0  1  2   E
            {0, 2, 2, 2, -1}, // S
            {9, 0, 2, 2, -1}, // 0
            {9, 3, 0, 2, -1}, // 1
            {9, 3, 2, 0, -1}, // 2
            {9, 3, 2, 2,  0}  // E
        };
        Assert.assertArrayEquals(new int[]{1, 2}, RunningWithBunnies.solution(times, 1));
    }

    @Test
    /*
        (S-E-S)* - 0 - 1 - 2 - 3 - 4 - E
        495 - 495 - 396 - 297 - 198 - 99 - 0
        S-S:0
        S-E-S:-1
        CYCLE FOUND, RETURN ALL
        S-0:99
        S-S*-0:0
     */
    public void caseCTest() {
        int[][] times = new int[][]{
            //S   0   1   2   3   4   E
            {0,  99, 99, 99, 99, 99, -1}, // S
            {99,  0, 99, 99, 99, 99, 99}, // 0
            {99, 99,  0, 99, 99, 99, 99}, // 1
            {99, 99, 99,  0, 99, 99, 99}, // 2
            {99, 99, 99, 99,  0, 99, 99}, // 3
            {99, 99, 99, 99,  0,  0, 99}, // 4
            {0,  99, 99, 99, 99, 99,  0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4}, RunningWithBunnies.solution(times, 1));
    }

    @Test
    /*
    S-2-3-0 - S-2-3-4 - S-2-3-E
    14-13-12-10-9-8-7-4-3-2-1-0
    */
    public void caseDTest() {
        int[][] times = new int[][]{
            //S 0  1  2  3  4  E
            {0, 9, 9, 1, 9, 9, 9}, // S
            {1, 0, 9, 9, 9, 9, 9}, // 0
            {1, 9, 0, 9, 9, 9, 9}, // 1
            {9, 9, 9, 0, 1, 9, 9}, // 2
            {9, 2, 4, 9, 0, 3, 1}, // 3
            {1, 9, 9, 9, 9, 0, 9}, // 4
            {1, 9, 9, 9, 9, 9, 0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 2, 3, 4}, RunningWithBunnies.solution(times, 14));
    }

    @Test
    /*
        S-1-4-3-2-0-E
        0-2-1-(-2)-(-3)-(-1)-0
     */
    public void caseETest() {
        int[][] times = new int[][]{
            //S   0   1  2   3   4   E
            { 0,  1, -2, 3,  2, -1,  0}, // S
            {-1,  0, -3, 2,  1, -2, -1}, // 0
            { 2,  3,  0, 5,  4,  1,  2}, // 1
            {-3, -2, -5, 0, -1, -4, -3}, // 2
            {-2, -1, -4, 1,  0, -3, -2}, // 3
            { 1,  2, -1, 4,  3,  0,  1}, // 4
            { 0,  1, -2, 3,  2, -1,  0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4}, RunningWithBunnies.solution(times, 0));
    }

    @Test
    // No bunnies can be picked up during time limit
    public void caseFTest() {
        int[][] times = new int[][]{
            //S 0  1  E
            {0, 2, 2, 2}, // S
            {2, 0, 2, 2}, // 0
            {2, 2, 0, 2}, // 1
            {2, 2, 2, 0}  // E
        };
        Assert.assertArrayEquals(new int[]{}, RunningWithBunnies.solution(times, 3));
    }

    @Test
    /*
        S-0-3-2-1-4-E
        999-996-990-966-961-917
        S-0-1-4-2-3-E
        999-996-989-984-975-930-925
     */
    public void caseGTest() {
        int[][] times = new int[][]{
            //S   0   1   2   3   4   E
            { 0,  3, 82, 91, 15, 24, 77}, // S
            { 8,  0,  7, 32,  6, 33, 14}, // 0
            {66, 98,  0, 62, 59,  5, 39}, // 1
            {64, 97,  5,  0, 45, 84, 21}, // 2
            { 3, 33, 81, 24,  0, 53,  5}, // 3
            {73, 93, 29,  9, 78,  0, 44}, // 4
            {70, 76, 15,  0, 43, 58,  0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 1, 2, 3, 4}, RunningWithBunnies.solution(times, 999));
    }

    @Test
    /*
        S-1-2-0-E
        3-8-6-4-0
     */
    public void caseHTest() {
        int[][] times = new int[][]{
            //S  0   1   2   3   4  E
            {0, -3, -5, -4, -1, -2, 0}, // S
            {5,  0, -1,  0,  3,  2, 4}, // 0
            {7,  3,  0,  2,  5,  4, 6}, // 1
            {6,  2,  0,  0,  4,  3, 5}, // 2
            {3, -1, -3, -2,  0,  0, 2}, // 3
            {4,  0, -2, -1,  2,  0, 3}, // 4
            {2, -2, -4, -3,  0, -1, 0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 1, 2}, RunningWithBunnies.solution(times, 3));
    }

    @Test
    /*
        S-4-0-2-E
        6-8-3-(-1)-0
        S-4-2-0-E
        6-8-2-2-0
     */
    public void caseITest() {
        int[][] times = new int[][]{
            //S  0   1   2   3   4   E
            { 0, 1, -1,  2, -3, -2,  0}, // S
            { 2, 0,  1,  4, -1,  0,  2}, // 0
            { 5, 6,  0,  7,  2,  3,  5}, // 1
            {-1, 0, -2,  0, -4, -3, -1}, // 2
            { 8, 9,  7, 10,  0,  6,  8}, // 3
            { 4, 5,  3,  6,  1,  0,  4}, // 4
            { 0, 1, -1,  2, -3,  2,  0}  // E
        };
        Assert.assertArrayEquals(new int[]{0, 2, 4}, RunningWithBunnies.solution(times, 6));
    }

    @Test
    /*
        S-3-2-3-1-E
        7-8-8-8-4-1
     */
    public void caseJTest() {
        int[][] times = new int[][]{
            //S   0   1   2   3   4   E
            { 0, 15, 19, 10, -1, 12,  4}, // S
            { 7,  0, 19,  4, 19, 17,  7}, // 0
            {15,  8,  0, 14,  8,  4,  3}, // 1
            {10, 14,  6,  0,  0,  5,  9}, // 2
            {18,  8,  4,  0,  0, 12, 16}, // 3
            { 0, 13,  1, -1, 12,  0,  4}, // 4
            { 8,  5,  2, 11, 12, 16,  0}  // E
        };
        Assert.assertArrayEquals(new int[]{1, 2, 3}, RunningWithBunnies.solution(times, 7));
    }
}