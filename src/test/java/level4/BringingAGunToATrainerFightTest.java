package level4;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class BringingAGunToATrainerFightTest {

    @Test
    public void caseATest() {
        Assert.assertEquals(
            7,
            BringingAGunToATrainerFight.solution(
                new int[]{3,2},
                new int[]{1,1},
                new int[]{2,1},
                4
            )
        );
    }

    @Test
    public void caseBTest() {
        Assert.assertEquals(
            27,
            BringingAGunToATrainerFight.solution(
                new int[]{2,5},
                new int[]{1,2},
                new int[]{1,4},
                11
            )
        );
    }

    @Test
    public void caseCTest() {
        Assert.assertEquals(
            8,
            BringingAGunToATrainerFight.solution(
                new int[]{23,10},
                new int[]{6,4},
                new int[]{3,2},
                23
            )
        );
    }

    @Test
    public void caseDTest() {
        Assert.assertEquals(
            196,
            BringingAGunToATrainerFight.solution(
                new int[]{1250,1250},
                new int[]{1000,1000},
                new int[]{500,400},
                10000
            )
        );
    }

    @Test
    public void caseETest() {
        Assert.assertEquals(
            9,
            BringingAGunToATrainerFight.solution(
                new int[]{300,275},
                new int[]{150,150},
                new int[]{165,100},
                500
            )
        );
    }

    @Test
    public void caseFTest() {
        Assert.assertEquals(
            30904,
            BringingAGunToATrainerFight.solution(
                new int[]{42,59},
                new int[]{34,44},
                new int[]{6,34},
                5000
            )
        );
    }

    @Test
    public void caseGTest() {
        Assert.assertEquals(
            739323,
            BringingAGunToATrainerFight.solution(
                new int[]{10,10},
                new int[]{4,4},
                new int[]{3,3},
                5000
            )
        );
    }
}