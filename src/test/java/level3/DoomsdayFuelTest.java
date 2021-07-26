package level3;

import org.junit.Assert;
import org.junit.Test;

public class DoomsdayFuelTest {

	@Test
	public void caseATest() {
		Assert.assertArrayEquals(
			new int[] {7, 6, 8, 21},
			DoomsdayFuel.solution(
				new int[][]{
					{0, 2, 1, 0, 0},
					{0, 0, 0, 3, 4},
					{0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseBTest() {
		Assert.assertArrayEquals(
			new int[] {0, 3, 2, 9, 14},
			DoomsdayFuel.solution(
				new int[][]{
					{0, 1, 0, 0, 0, 1},
					{4, 0, 0, 3, 2, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseCTest() {
		Assert.assertArrayEquals(
			new int[] {1, 2, 3},
			DoomsdayFuel.solution(
				new int[][]{
					{1, 2, 3, 0, 0, 0},
					{4, 5, 6, 0, 0, 0},
					{7, 8, 9, 1, 0, 0},
					{0, 0, 0, 0, 1, 2},
					{0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseDTest() {
		Assert.assertArrayEquals(
			new int[] {1, 1},
			DoomsdayFuel.solution(
				new int[][]{
					{0}
				}
			)
		);
	}

	@Test
	public void caseETest() {
		Assert.assertArrayEquals(
			new int[] {1, 2, 3, 4, 5, 15},
			DoomsdayFuel.solution(
				new int[][]{
					{0, 0, 12, 0, 15, 0, 0, 0, 1, 8},
					{0, 0, 60, 0, 0, 7, 13, 0, 0, 0},
					{0, 15, 0, 8, 7, 0, 0, 1, 9, 0},
					{23, 0, 0, 0, 0, 1, 0, 0, 0, 0},
					{37, 35, 0, 0, 0, 0, 3, 21, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseFTest() {
		Assert.assertArrayEquals(
			new int[] {4, 5, 5, 4, 2, 20},
			DoomsdayFuel.solution(
				new int[][]{
					{0, 7, 0, 17, 0, 1, 0, 5, 0, 2},
					{0, 0, 29, 0, 28, 0, 3, 0, 16, 0},
					{0, 3, 0, 0, 0, 1, 0, 0, 0, 0},
					{48, 0, 3, 0, 0, 0, 17, 0, 0, 0},
					{0, 6, 0, 0, 0, 1, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseGTest() {
		Assert.assertArrayEquals(
			new int[] {1, 1, 1, 1, 1, 5},
			DoomsdayFuel.solution(
				new int[][]{
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseHTest() {
		Assert.assertArrayEquals(
			new int[] {2, 1, 1, 1, 1, 6},
			DoomsdayFuel.solution(
				new int[][]{
					{1, 1, 1, 0, 1, 0, 1, 0, 1, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 0, 1, 1, 1, 0, 1, 0, 1, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 0, 1, 0, 1, 1, 1, 0, 1, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 0, 1, 0, 1, 0, 1, 1, 1, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{1, 0, 1, 0, 1, 0, 1, 0, 1, 1},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseITest() {
		Assert.assertArrayEquals(
			new int[] {6, 44, 4, 11, 22, 13, 100},
			DoomsdayFuel.solution(
				new int[][]{
					{0, 86, 61, 189, 0, 18, 12, 33, 66, 39},
					{0, 0, 2, 0, 0, 1, 0, 0, 0, 0},
					{15, 187, 0, 0, 18, 23, 0, 0, 0, 0},
					{1, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
				}
			)
		);
	}

	@Test
	public void caseJTest() {
		Assert.assertArrayEquals(
			new int[] {1, 1, 1, 2, 5},
			DoomsdayFuel.solution(
				new int[][]{
					{0, 0, 0, 0, 3, 5, 0, 0, 0, 2},
					{0, 0, 4, 0, 0, 0, 1, 0, 0, 0},
					{0, 0, 0, 4, 4, 0, 0, 0, 1, 1},
					{13, 0, 0, 0, 0, 0, 2, 0, 0, 0},
					{0, 1, 8, 7, 0, 0, 0, 1, 3, 0},
					{1, 7, 0, 0, 0, 0, 0, 2, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
					{0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
				}
			)
		);
	}
}