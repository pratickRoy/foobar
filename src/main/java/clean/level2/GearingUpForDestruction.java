package clean.level2;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;

/**
 *
 * Gearing Up for Destruction
 * ==========================
 *
 * As Commander Lambda's personal assistant, you've been assigned the task of configuring the LAMBCHOP doomsday
 * device's axial orientation gears. It should be pretty simple -- just add gears to create the appropriate rotation
 * ratio. But the problem is, due to the layout of the LAMBCHOP and the complicated system of beams and pipes
 * supporting it, the pegs that will support the gears are fixed in place.
 *
 * The LAMBCHOP's engineers have given you lists identifying the placement of groups of pegs along various support
 * beams. You need to place a gear on each peg (otherwise the gears will collide with unoccupied pegs).
 * The engineers have plenty of gears in all different sizes stocked up, so you can choose gears of any size,
 * from a radius of 1 on up. Your goal is to build a system where the last gear rotates at twice the rate
 * (in revolutions per minute, or rpm) of the first gear, no matter the direction. Each gear (except the last)
 * touches and turns the gear on the next peg to the right.
 *
 * Given a list of distinct positive integers named pegs representing the location of each peg along the support
 * beam, write a function solution(pegs) which, if there is a solution, returns a list of two positive integers
 * a and b representing the numerator and denominator of the first gear's radius in its simplest form in order
 * to achieve the goal above, such that radius = a/b. The ratio a/b should be greater than or equal to 1.
 * Not all support configurations will necessarily be capable of creating the proper rotation ratio, so if the
 * task is impossible, the function solution(pegs) should return the list [-1, -1].
 *
 * For example, if the pegs are placed at [4, 30, 50], then the first gear could have a radius of 12, the second
 * gear could have a radius of 14, and the last one a radius of 6. Thus, the last gear would rotate twice as fast
 * as the first one. In this case, pegs would be [4, 30, 50] and solution(pegs) should return [12, 1].
 *
 * The list pegs will be given sorted in ascending order and will contain at least 2 and no more than 20 distinct
 * positive integers, all between 1 and 10000 inclusive.
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
 * Solution.solution({4, 17, 50})
 * Output:
 *     -1,-1
 *
 * Input:
 * Solution.solution({4, 30, 50})
 * Output:
 *     12,1
 *
 * -- Python cases --
 * Input:
 * solution.solution([4, 30, 50])
 * Output:
 *     12,1
 *
 * Input:
 * solution.solution([4, 17, 50])
 * Output:
 *     -1,-1
 */
public class GearingUpForDestruction {

    private final static int[] IMPOSSIBLE_OUTPUT = new int[]{-1,-1};
    private final static int MINIMUM_ALLOWED_RADIUS_FOR_ALL_GEARS_EXCEPT_FIRST = 1;
    private final static int MINIMUM_ALLOWED_RADIUS_FOR_FIRST_GEAR = 2 * MINIMUM_ALLOWED_RADIUS_FOR_ALL_GEARS_EXCEPT_FIRST;

    public static int[] solution(int[] pegs) {

        // Get the possible first gear radius given the peg arrangement
        double firstGearRadius = getFirstGearRadiusForPegArrangement(pegs);

        // The possible solution might not be allowed given the constraints, we need to filter out such cases
        if (!isGearSolutionPossibleForPegArrangement(firstGearRadius, pegs)) {
            return IMPOSSIBLE_OUTPUT;
        }

        // Converting Gear Radius to Fractional Form
        final SimplestFraction firstGearRadiusAsSimplestFraction = new SimplestFraction(firstGearRadius);

        return new int[]{ firstGearRadiusAsSimplestFraction.numerator, firstGearRadiusAsSimplestFraction.denominator };
    }

    private static boolean isGearSolutionPossibleForPegArrangement(double firstGearRadius, int[] pegs) {

        // This is not strictly required, as if this is false then last gear will be < 1
        // As last gear is half of first. This is minor optimization that allows us to exit faster
        if (firstGearRadius < MINIMUM_ALLOWED_RADIUS_FOR_FIRST_GEAR) {
            return false;
        }

        double currentGearRadius = firstGearRadius;
        for (int pegIndex = 1; pegIndex < pegs.length; pegIndex++) {

            double distance = pegs[pegIndex] - pegs[pegIndex - 1];
            currentGearRadius = distance - currentGearRadius;
            if (currentGearRadius < MINIMUM_ALLOWED_RADIUS_FOR_ALL_GEARS_EXCEPT_FIRST) {
                return false;
            }
        }
        return true;
    }

    /*

        As per the arrangement:
        - On each peg we will put a gear.
        - The gears must touch each other.
        - The distance from peg(center of gear) to gear edge is the radius of a gear.

        Thus, distance between two adjacent pegs = sum of radius of the gears on those pegs

        Let us assume
         - DX is the distance between peg on Index X and Index X+1 = sum of radius of the gears on peg X and peg X+1
         - rX as the radius of the gear on peg with index X

        now let us assume there are N pegs, then

            r0 + r1 = D0
        =>  r1 = DO - r0

            r1 + r2 = D1
        =>  r2 = D1 - r1
        =>  r2 = D1 - (D0 - r0)
               = D1 - D0 + r0

            r2 + r3 = D2
        =>  r3 = D2 - r2
        =>  r3 = D2 - (D1 - D0 + r0)
               = D2 - D1 + D0 - r0

        Now, if lets say that r3 is the last peg, then

        r3 = r0/2

        then equation becomes :

        => r0/2 + r0 = D2 - D1 + D0
        => r0 = 2/3 * (D2 - D1 + D0)
              = 2/3 * (D0 - D1 + D2)
              = 2/3 * (Sum of all Even Index Distances - Sum of All Odd Index Distances)

            r3 + r4 = D3
        =>  r4 = D3 - r3
        =>  r4 = D3 - (D2 - D1 + D0 - r0)
               = D3 - D2 + D1 - D0 + r0

        Now, if lets say that r4 is the last peg, then

        r4 = r0/2

        then equation becomes :

        => r0/2 - r0 = D3 - D2 + D1 - D0
        => r0 = 2 * (- D3 + D2 - D1 + D0)
              = 2 * (  D0 - D1 + D2 - D3)
              = 2 * (Sum of all Even Index Distances - Sum of All Odd Index Distances)

        Thus

        r0 = (pegs.length % 2 == 0 ? 2/3 : 2) * (Sum of all Even Index Distances - Sum of All Odd Index Distances)

     */
    private static double getFirstGearRadiusForPegArrangement(int[] pegs) {

        double sumOfEvenIndexedAndNegativeOfOddIndexedDistances = 0;
        for (int pegIndex = 0; pegIndex < pegs.length - 1 ; pegIndex++) {

            double distance = pegs[pegIndex + 1] - pegs[pegIndex];
            sumOfEvenIndexedAndNegativeOfOddIndexedDistances += isEven(pegIndex)
                ? distance
                : -1 * distance;
        }

        return (isEven(pegs.length) ? 2d/3d : 2) * (sumOfEvenIndexedAndNegativeOfOddIndexedDistances);
    }

    private static boolean isEven(int number) {
        return number % 2 == 0;
    }


    private static class SimplestFraction {

        private final int numerator;
        private final int denominator;

        /*
            The idea is very simple and I took it from
            https://math.stackexchange.com/questions/1404403/how-to-convert-a-decimal-to-a-fraction-easily

            Basically if no is 10.123123
            we know that 10/1 < 10.123123 < 11/1
            now, lets sum the min and max numerators & denominators i.e. (10+11)/(1+1) = 21/2
            this is our new approximate number
            21/2 = 10.5, which is > 10.123123
            so lets replace the higher number by 21/2
            so now 10/1 < 10.123123 < 21/2

            Thus we were able to reduce our range from 10-11 to 10-10.5

            We keep on doing this until our abs(approximate - actual) < tolerance.
            Here we are taking tolerance of 0.000001

            Once we have this we just take the numerator and denominator of the approximate, divide them by their gcd.

            Once you understand it, it is mind-blowing in its simplicity!

            Lets run an example

            3/1 < 3.142857 < 7/2
            3/1 < 3.142857 < 10/3
            3/1 < 3.142857 < 13/4
            3/1 < 3.142857 < 16/5
            3/1 < 3.142857 < 19/6
            3/1 < 3.142857 < 22/7

            22/7 = 3.142857143

            abs(3.142857143 - 3.142857) = 0.000000143, which is < 0.000001, so our approximation is 22/7

            If you know what 22/7 means, which I assume you do, then Mind Blown. :)
         */
        public SimplestFraction(double floatingNumber) {

            // Difference should be less than 0.000001.
            // We can play around with this further. For the challenge this is good enough,
            // and i tried it with some difficult cases such as 10.123123 & 0.3760683761 and got expected results
            // one thing we can try to do is keep make tolerance = no of decimal places of the number
            // i.e. if no is 12.31312, then tolerance = 1.0E-5, if 12.123, tolerance = 1.0E-3
            // It will be fun to see what output that gives us.
            double tolerance = 1.0E-6;

            int minNumerator = (int) Math.floor(floatingNumber);
            int minDenominator = 1;

            int maxNumerator = (int) Math.ceil(floatingNumber);
            int maxDenominator = 1;

            double approximateFloatingNumber;
            int approximateFloatingNumberNumerator;
            int approximateFloatingNumberDenominator;

            do {

                approximateFloatingNumberNumerator = minNumerator + maxNumerator;
                approximateFloatingNumberDenominator = minDenominator + maxDenominator;

                approximateFloatingNumber = (double) approximateFloatingNumberNumerator / approximateFloatingNumberDenominator;

                if (approximateFloatingNumber > floatingNumber) {
                    maxNumerator = approximateFloatingNumberNumerator;
                    maxDenominator = approximateFloatingNumberDenominator;
                } else {
                    minNumerator = approximateFloatingNumberNumerator;
                    minDenominator = approximateFloatingNumberDenominator;
                }

            } while (Math.abs(approximateFloatingNumber - floatingNumber) > tolerance); // Keep going until our approximation - actual > tolerance

            int gcd = findGreatestCommonDenominator(approximateFloatingNumberNumerator, approximateFloatingNumberDenominator);

            this.numerator = approximateFloatingNumberNumerator / gcd;
            this.denominator = approximateFloatingNumberDenominator / gcd;
        }

        private int findGreatestCommonDenominator(int number1, int number2) {

            if (number2 == 0) {
                return number1;
            }
            return findGreatestCommonDenominator(number2, number1 % number2);
        }
    }

    static class Tester {

        public static void test() {

            printOutput(GearingUpForDestruction.solution(new int[]{4, 30, 50}));              // [12, 1] Base Test Case
            printOutput(GearingUpForDestruction.solution(new int[]{4, 17, 50}));              // [-1, -1] Negative Test
            printOutput(GearingUpForDestruction.solution(new int[]{1, 100}));                 // [66, 1] 2 Pegs
            printOutput(GearingUpForDestruction.solution(new int[]{375, 3850, 7328, 8630}));  // [866, 1] Even
            printOutput(GearingUpForDestruction.solution(new int[]{13, 130, 234, 327, 394})); // [78, 1] Odd
            printOutput(GearingUpForDestruction.solution(new int[]{9377, 9447, 9569, 9646})); // [50, 3] Hard to make into Fraction (Radii is 16.666666..)

            //findValidTestSets(4, true, true);
        }

        private static void printOutput(int[] output) {

            System.out.println(
                Arrays.stream(output)
                    .mapToObj(String::valueOf)
                    .collect(Collectors.joining(",", "[", "]"))
            );
        }

        // To come up with test cases
        private static void findValidTestSets(int numberOfPegs,
                                              boolean mustBeValid,
                                              boolean mustBeAHardFraction) {

            while (true) {

                int[] pegs = new int[numberOfPegs];
                pegs[0] = getRandomInteger(1, 10000);
                for (int pegIndex = 1; pegIndex < numberOfPegs; pegIndex++) {
                    pegs[pegIndex] = getRandomInteger(pegs[pegIndex - 1], 10000);
                }

                int[] sol = level2.GearingUpForDestruction.solution(pegs);
                if (mustBeValid && sol[0] != -1) {
                    if (mustBeAHardFraction && sol[1] != 1) {
                        System.out.println(Arrays.toString(pegs));
                        printOutput(sol);
                        break;
                    }
                }
            }
        }

        private static int getRandomInteger(int low, int high) {
            return new Random().nextInt(high-low) + low;
        }
    }

    public static void main(String[] args) {
        Tester.test();
    }
}
