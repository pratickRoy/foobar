package level4;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Bringing a Gun to a Trainer Fight
 * =================================
 *
 * Uh-oh -- you've been cornered by one of Commander Lambdas elite bunny trainers! Fortunately, you grabbed
 * a beam weapon from an abandoned storeroom while you were running through the station, so you have a chance
 * to fight your way out. But the beam weapon is potentially dangerous to you as well as to the bunny trainers:
 * its beams reflect off walls, meaning you'll have to be very careful where you shoot to avoid bouncing a shot
 * toward yourself!
 *
 * Luckily, the beams can only travel a certain maximum distance before becoming too weak to cause damage.
 * You also know that if a beam hits a corner, it will bounce back in exactly the same direction.
 * And of course, if the beam hits either you or the bunny trainer, it will stop immediately (albeit painfully).
 *
 * Write a function solution(dimensions, your_position, trainer_position, distance) that gives an array of 2 integers
 * of the width and height of the room, an array of 2 integers of your x and y coordinates in the room,
 * an array of 2 integers of the trainer's x and y coordinates in the room, and returns an integer of the number
 * of distinct directions that you can fire to hit the elite trainer, given the maximum distance that the beam can
 * travel.
 *
 * The room has integer dimensions [1 < x_dim <= 1250, 1 < y_dim <= 1250]. You and the elite trainer are both
 * positioned on the integer lattice at different distinct positions (x, y) inside the room such that
 * [0 < x < x_dim, 0 < y < y_dim]. Finally, the maximum distance that the beam can travel before becoming harmless
 * will be given as an integer 1 < distance <= 10000.
 *
 * For example, if you and the elite trainer were positioned in a room with dimensions [3, 2], your_position [1, 1],
 * trainer_position [2, 1], and a maximum shot distance of 4, you could shoot in seven different directions to hit
 * the elite trainer (given as vector bearings from your location): [1, 0], [1, 2], [1, -2], [3, 2], [3, -2], [-3, 2],
 * and [-3, -2]. As specific examples, the shot at bearing [1, 0] is the straight line horizontal shot of distance 1,
 * the shot at bearing [-3, -2] bounces off the left wall and then the bottom wall before hitting the elite trainer with
 * a total shot distance of sqrt(13), and the shot at bearing [1, 2] bounces off just the top wall before hitting the
 * elite trainer with a total shot distance of sqrt(5).
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
 * Solution.solution([3,2], [1,1], [2,1], 4)
 * Output:
 *     7
 *
 * Input:
 * Solution.solution([300,275], [150,150], [185,100], 500)
 * Output:
 *     9
 *
 * -- Python cases --
 * Input:
 * solution.solution([3,2], [1,1], [2,1], 4)
 * Output:
 *     7
 *
 * Input:
 * solution.solution([300,275], [150,150], [185,100], 500)
 * Output:
 *     9
 */
public class BringingAGunToATrainerFight {

    private static int[] horizontalMirrorYList;
    private static int[] verticalMirrorXList;
    private static double maxSquaredDistance;
    private static int[] myPosition;

    public static int solution(int[] dimensions, int[] your_position, int[] trainer_position, int distance) {

        horizontalMirrorYList = new int[]{0, dimensions[1]};
        verticalMirrorXList = new int[]{0, dimensions[0]};
        maxSquaredDistance = Math.pow(distance, 2);
        myPosition = your_position;

        if (maxSquaredDistance < getSquaredDistance(myPosition, trainer_position)) {
            return 0;
        }

        if (maxSquaredDistance == getSquaredDistance(myPosition, trainer_position)) {
            return 1;
        }

        final List<int[]> allTrainers = new ArrayList<>();
        final Set<String> allTrainersHash = new HashSet<>();
        allTrainers.add(trainer_position);
        allTrainersHash.add(hashArray(trainer_position));

        int trainerIndex = 0;
        while (trainerIndex < allTrainers.size()) {

            fillAllReachableMirroredTrainers(
                allTrainers,
                allTrainersHash,
                allTrainers.get(trainerIndex)
            );
            trainerIndex++;
        }

        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (int[] trainer : allTrainers) {

            minX = Math.min(minX, trainer[0]);
            maxX = Math.max(maxX, trainer[0]);
            minY = Math.min(minY, trainer[1]);
            maxY = Math.max(maxY, trainer[1]);
        }

        final List<int[]> allSelfs = new ArrayList<>();
        final Set<String> allSelfsHash = new HashSet<>();
        allSelfs.add(your_position);
        allSelfsHash.add(hashArray(your_position));
        int selfIndex = 0;
        while (selfIndex < allSelfs.size()) {

            fillAllMirroredSelf(
                allSelfs,
                allSelfsHash,
                allSelfs.get(selfIndex),
                maxX,
                maxY,
                minX,
                minY
            );
            selfIndex++;
        }

        Map<Double, Double> hitMe = getShotAngleToSquaredDistanceMap(allSelfs);
        Map<Double, Double> hitTarget = getShotAngleToSquaredDistanceMap(allTrainers);

        int count = 0;

        for (final Double key : hitTarget.keySet()) {
            if (hitMe.containsKey(key)) {
                if (hitTarget.get(key) < hitMe.get(key)) {
                    count++;
                }
            }
            else {
                count++;
            }
        }

        return count;
    }

    private static Map<Double, Double> getShotAngleToSquaredDistanceMap(List<int[]> targets) {

        final Map<Double, Double> map = new HashMap<>();

        for (int[] target : targets) {

            double angle = getAngle(myPosition, target);
            double squaredDistance = getSquaredDistance(myPosition, target);

            if (!map.containsKey(angle)) {
                map.put(angle, squaredDistance);
            } else {
                map.put(angle, Math.min(map.get(angle), squaredDistance));
            }
        }

        return map;
    }

    private static String hashArray(int[] array) {

        return Arrays.stream(array)
            .boxed()
            .map(i -> Integer.toString(i))
            .collect(Collectors.joining(","));
    }

    private static void fillAllReachableMirroredTrainers(final List<int[]> allTrainers,
                                                         final Set<String> allTrainersHash,
                                                         final int[] trainerPosition) {

        List<int[]> positions = new ArrayList<>();
        for (int horizontalMirrorY : horizontalMirrorYList) {
            positions.add(getHorizontalMirrorImagePosition(trainerPosition, horizontalMirrorY));
        }
        for (int verticalMirrorX : verticalMirrorXList) {
            positions.add(getVerticalMirrorImagePosition(trainerPosition, verticalMirrorX));
        }

        for(int[] position : positions) {

            String positionHash = hashArray(position);
            if (!allTrainersHash.contains(positionHash)
                && getSquaredDistance(myPosition, position) <= maxSquaredDistance) {

                allTrainersHash.add(positionHash);
                allTrainers.add(position);
            }
        }
    }

    private static void fillAllMirroredSelf(final List<int[]> allSelfs,
                                            final Set<String> allSelfsHash,
                                            final int[] myPosition,
                                            int maxX,
                                            int maxY,
                                            int minX,
                                            int minY) {

        List<int[]> positions = new ArrayList<>();
        for (int horizontalMirrorY : horizontalMirrorYList) {
            positions.add(getHorizontalMirrorImagePosition(myPosition, horizontalMirrorY));
        }
        for (int verticalMirrorX : verticalMirrorXList) {
            positions.add(getVerticalMirrorImagePosition(myPosition, verticalMirrorX));
        }

        for(int[] position : positions) {

            String positionHash = hashArray(position);
            if (!allSelfsHash.contains(positionHash)
                && position[0] <= maxX
                && position[0] >= minX
                && position[1] <= maxY
                && position[1] >= minY) {

                allSelfsHash.add(positionHash);
                allSelfs.add(position);
            }
        }
    }

    public static double getAngle(int[] pointA, int[] pointB) {

        return Math.atan2(pointA[0] - pointB[0], pointA[1] - pointB[1]);
    }

    private static double getSquaredDistance(int[] pointA, int[] pointB) {

        return (Math.pow(pointA[0] - pointB[0], 2)) + (Math.pow(pointA[1] - pointB[1], 2));
    }

    private static int[] getVerticalMirrorImagePosition(int[] position, int mirrorX) {

        return new int[] { mirrorX + (mirrorX - position[0]), position[1] };
    }

    private static int[] getHorizontalMirrorImagePosition(int[] position, int mirrorY) {

        return new int[] { position[0], mirrorY + (mirrorY - position[1]) };
    }
}
