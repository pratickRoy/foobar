package level3;

import java.math.BigInteger;

/**
 * Fuel Injection Perfection
 * =========================
 *
 * Commander Lambda has asked for your help to refine the automatic quantum antimatter fuel injection system for the
 * LAMBCHOP doomsday device. It's a great chance for you to get a closer look at the LAMBCHOP --
 * and maybe sneak in a bit of sabotage while you're at it -- so you took the job gladly.
 *
 * Quantum antimatter fuel comes in small pellets, which is convenient since the many moving parts of the
 * LAMBCHOP each need to be fed fuel one pellet at a time. However, minions dump pellets in bulk into the fuel intake.
 * You need to figure out the most efficient way to sort and shift the pellets down to a single pellet at a time.
 *
 * The fuel control mechanisms have three operations:
 *
 * 1) Add one fuel pellet
 * 2) Remove one fuel pellet
 * 3) Divide the entire group of fuel pellets by 2 (due to the destructive energy released when a quantum antimatter
 * pellet is cut in half, the safety controls will only allow this to happen if there is an even number of pellets)
 *
 * Write a function called solution(n) which takes a positive integer as a string and returns the minimum number of
 * operations needed to transform the number of pellets to 1. The fuel intake control panel can only display a number
 * up to 309 digits long, so there won't ever be more pellets than you can express in that many digits.
 *
 * For example:
 * solution(4) returns 2: 4 -> 2 -> 1
 * solution(15) returns 5: 15 -> 16 -> 8 -> 4 -> 2 -> 1
 * Quantum antimatter fuel comes in small pellets, which is convenient since the many moving parts of the LAMBCHOP
 * each need to be fed fuel one pellet at a time. However, minions dump pellets in bulk into the fuel intake.
 * You need to figure out the most efficient way to sort and shift the pellets down to a single pellet at a time.
 *
 * The fuel control mechanisms have three operations:
 *
 * 1) Add one fuel pellet
 * 2) Remove one fuel pellet
 * 3) Divide the entire group of fuel pellets by 2 (due to the destructive energy released when a quantum antimatter
 * pellet is cut in half, the safety controls will only allow this to happen if there is an even number of pellets)
 *
 * Write a function called solution(n) which takes a positive integer as a string and returns the minimum number of
 * operations needed to transform the number of pellets to 1. The fuel intake control panel can only display a number
 * up to 309 digits long, so there won't ever be more pellets than you can express in that many digits.
 *
 * For example:
 * solution(4) returns 2: 4 -> 2 -> 1
 * solution(15) returns 5: 15 -> 16 -> 8 -> 4 -> 2 -> 1
 *
 * Languages
 * =========
 *
 * To provide a Python solution, edit solution.py
 * To provide a Java solution, edit Solution.java
 *
 * Test cases
 * ==========
 * Your code should pass the following test cases.
 * Note that it may also be run against hidden test cases not shown here.
 *
 * -- Python cases --
 * Input:
 * solution.solution('15')
 * Output:
 *     5
 *
 * Input:
 * solution.solution('4')
 * Output:
 *     2
 *
 * -- Java cases --
 * Input:
 * Solution.solution('4')
 * Output:
 *     2
 *
 * Input:
 * Solution.solution('15')
 * Output:
 *     5
 */
public class FuelInjectionPerfection {

    private static final BigInteger THREE = new BigInteger("3");

    public static int solution(String x) {

        BigInteger pellets = new BigInteger(x);

        // Is No of pellets <= 0
        if(pellets.signum() <= 0) {
            return 0;
        }

        int count = 0;
        while (!pellets.equals(BigInteger.ONE)) {

            // If Pellets are even divide by 2
            if (!pellets.testBit(0)) {
                pellets = pellets.shiftRight(1);
            } else {

                // Unless subtracting will get a multiple of 2 add
                // Any No And 3 will be equal to 1 if no + 1 is a multiple of 2
                if (pellets.equals(THREE) || pellets.and(THREE).equals(BigInteger.ONE)) {
                    pellets = pellets.subtract(BigInteger.ONE);
                } else {
                    pellets = pellets.add(BigInteger.ONE);
                }
            }
            count++;
        }
        return count;
    }

    public static void main(String[] args) {

        System.out.println(FuelInjectionPerfection.solution("-1"));
        System.out.println(FuelInjectionPerfection.solution("1"));
        System.out.println(FuelInjectionPerfection.solution("2"));
        System.out.println(FuelInjectionPerfection.solution("4"));
        System.out.println(FuelInjectionPerfection.solution("1027"));
        System.out.println(FuelInjectionPerfection.solution("251369441954344211905646627858729002496435462554469162076262795199424564270324624017823224787028757317641640784120817121344177839898546337604542013271215217641066092746681817055960915839537324050759212611945827655849761950087872600994226069685023346301679347144238059279554387460591860528979745476673134261048"));
    }
}
