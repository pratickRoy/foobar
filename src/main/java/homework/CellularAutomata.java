package homework;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CellularAutomata {

	private static final List<boolean[][]> trueNeighbours = Stream.of(
		new boolean[][]{{false, false}, {false, true}},
		new boolean[][]{{false, false}, {true, false}},
		new boolean[][]{{false, true}, {false, false}},
		new boolean[][]{{true, false}, {false, false}}
	).collect(Collectors.toList());

	private static final List<boolean[][]> falseNeighbours = Stream.of(
		new boolean[][]{{false, false}, {false, false}},
		new boolean[][]{{false, false}, {true, true}},
		new boolean[][]{{false, true}, {false, true}},
		new boolean[][]{{false, true}, {true, false}},
		new boolean[][]{{false, true}, {true, true}},
		new boolean[][]{{true, false}, {false, true}},
		new boolean[][]{{true, false}, {true, false}},
		new boolean[][]{{true, false}, {true, true}},
		new boolean[][]{{true, true}, {false, false}},
		new boolean[][]{{true, true}, {false, true}},
		new boolean[][]{{true, true}, {true, false}},
		new boolean[][]{{true, true}, {true, true}}
	).collect(Collectors.toList());

	public void printMask(boolean[][] image) {

		String leftMask = "0b";
		String rightMask = "0b";

		String topMask = "0b";
		String bottomMask = "0b";

		if (!image[0][0]) {
			leftMask = leftMask + "0";
			topMask = topMask + "0";
		} else {
			leftMask = leftMask + "1";
			topMask = topMask + "1";
		}

		if (!image[1][0]) {
			leftMask = leftMask + "0";
			bottomMask = bottomMask + "0";
		} else {
			leftMask = leftMask + "1";
			bottomMask = bottomMask + "1";
		}

		if (!image[0][1]) {
			rightMask = rightMask + "0";
			topMask = topMask + "0";
		} else {
			rightMask = rightMask + "1";
			topMask = topMask + "1";
		}

		if (!image[1][1]) {
			rightMask = rightMask + "0";
			bottomMask = bottomMask + "0";
		} else {
			rightMask = rightMask + "1";
			bottomMask = bottomMask + "1";
		}

		System.out.println("new PreImage("+leftMask+", "+rightMask+", "+topMask+", "+bottomMask+"),");

	}

	public List<boolean[][]> getAllowedPreimagesForRow(final boolean[] row) {

		List<boolean[][]> allowedPreimages = getPreimages(row[0]);
		for (int i = 1; i < row.length; i++) {

			List<boolean[][]> newAllowedPreImages = new ArrayList<>();
			List<boolean[][]> rightAllowedPreimages =  getPreimages(row[i]);

			for (boolean[][] leftImage : allowedPreimages) {

				for (boolean[][] rightImage : rightAllowedPreimages) {

					if (leftImage[0][leftImage[0].length - 1] == rightImage[0][0]
						&& leftImage[1][leftImage[0].length - 1] == rightImage[1][0]) {

						boolean[][] allowedPreimage = new boolean[2][leftImage[0].length + 1];
						allowedPreimage[0] = Arrays.copyOf(leftImage[0], leftImage[0].length + 1);
						allowedPreimage[1] = Arrays.copyOf(leftImage[1], leftImage[0].length + 1);
						allowedPreimage[0][leftImage[0].length] = rightImage[0][1];
						allowedPreimage[1][leftImage[0].length] = rightImage[1][1];
						newAllowedPreImages.add(allowedPreimage);
					}
				}
			}

			allowedPreimages = newAllowedPreImages;
		}

		print(allowedPreimages);

		return allowedPreimages;
	}

	public List<boolean[][]> getAllowedPreimagesCombiningColumns(final List<boolean[][]> firstRowPreimages,
																 final List<boolean[][]> secondRowPreimages) {

		List<boolean[][]> allowedPreimages = new ArrayList<>();
		for (boolean[][] topImage : firstRowPreimages) {

			for (boolean[][] bottomImage : secondRowPreimages) {

				if (arrayEquals(topImage[topImage.length - 1], bottomImage[0])) {

					boolean[][] allowedPreimage = Arrays.copyOf(topImage, topImage.length + 1);
					allowedPreimage[allowedPreimage.length - 1] = Arrays.copyOf(bottomImage[1], bottomImage[0].length);
					allowedPreimages.add(allowedPreimage);
				}
			}
		}

		print(allowedPreimages);

		return allowedPreimages;
	}

	private List<boolean[][]> getPreimages(boolean cell) {

		if (cell) {
			return trueNeighbours;
		}
		return falseNeighbours;
	}

	public void printTruePreimages() {

		final List<boolean[][]> falttenedPreimages = getAllPossiblefalttenedPreimages();

		final List<boolean[][]> falttenedTruePreimages = new ArrayList<>();
		for (boolean[][] falttenedPreimage : falttenedPreimages) {

			int count = 0;
			for (int j = 0; j < falttenedPreimage.length; j++) {

				for (int k = 0; k < falttenedPreimage[0].length; k++) {

					if (falttenedPreimage[j][k]) {
						count++;
					}
				}
			}

			if (count == 1) {
				falttenedTruePreimages.add(falttenedPreimage);
			}
		}

		print(falttenedTruePreimages);
	}

	public void printFalsePreimages() {

		final List<boolean[][]> falttenedPreimages = getAllPossiblefalttenedPreimages();

		final List<boolean[][]> falttenedFalsePreimages = new ArrayList<>();
		for (boolean[][] falttenedPreimage : falttenedPreimages) {

			int count = 0;
			for (int j = 0; j < falttenedPreimage.length; j++) {

				for (int k = 0; k < falttenedPreimage[0].length; k++) {

					if (falttenedPreimage[j][k]) {
						count++;
					}
				}
			}

			if (count == 0 || count > 1) {
				falttenedFalsePreimages.add(falttenedPreimage);
			}
		}

		print(falttenedFalsePreimages);
	}

	private List<boolean[][]> getAllPossiblefalttenedPreimages() {

		final List<boolean[][]> falttenedPreimages = new ArrayList<>();

		for (int i = 0; i <= 1; i++) {

			for (int j = 0; j <= 1; j++) {

				for (int k = 0; k <= 1; k++) {

					for (int l = 0; l <= 1; l++) {

						falttenedPreimages.add(new boolean[][]{
							{getBool(i), getBool(j)},
							{getBool(k), getBool(l)}
						});
					}
				}
			}
		}

		return falttenedPreimages;
	}

	private boolean getBool(int i) {

		return i != 0;
	}

	private boolean arrayEquals(boolean[] array1,
								boolean[] array2) {

		for (int i = 0; i < array1.length; i++) {

			if (array1[i] != array2[i]) {
				return false;
			}
		}
		return true;
	}

	private void print(final List<boolean[][]> falttenedPreimages) {

		for (boolean[][] falttenedPreimage : falttenedPreimages) {

			System.out.println(Arrays.deepToString(falttenedPreimage));
		}
	}

	private void rowValidate(final boolean[][] output) {

		boolean[] x = new boolean[3];
		for (int i = 0; i < 1; i++) {

			for (int j = 0; j < output[0].length - 1; j++) {

				int count = 0;
				if (output[i][j]) {
					count++;
				}
				if (output[i][j+1]) {
					count++;
				}
				if (output[i+1][j]) {
					count++;
				}
				if (output[i+1][j+1]) {
					count++;
				}

				if (count == 1) {
					x[j] = true;
				}
			}
		}

		System.out.println();
		for (int i = 0; i < x.length; i++) {

			System.out.print((x[i] ? 1 : 0) + " ");
		}
	}

	private void validate(final boolean[][] output) {

		boolean[][] x = new boolean[3][3];
		for (int i = 0; i < output.length - 1; i++) {

			for (int j = 0; j < output[0].length - 1; j++) {

				int count = 0;
				if (output[i][j]) {
					count++;
				}
				if (output[i][j+1]) {
					count++;
				}
				if (output[i+1][j]) {
					count++;
				}
				if (output[i+1][j+1]) {
					count++;
				}

				if (count == 1) {
					x[i][j] = true;
				}
			}
		}

		System.out.println();
		for (int i = 0; i < output.length - 1; i++) {

			for (int j = 0; j < output[0].length - 1; j++) {

				System.out.print((x[i][j] == true ? 1 : 0) + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {

		//new CA().printTruePreimages();
		//System.out.println();
		//new CA().printFalsePreimages();
		//System.out.println();
		//new CA().getAllowedPreimagesForRow(new boolean[]{true,false,true});
		//System.out.println();
		//new CA().getAllowedPreimagesForRow(new boolean[]{false,true,false});
		//CA ca = new CA();
		//ca.getAllowedPreimagesForRow(new boolean[]{false,true,false}).forEach(image -> ca.rowValidate(image));

		CellularAutomata cellularAutomata = new CellularAutomata();

		boolean[][] input = new boolean[][] {
			{true, false, true},
			{false, true, false},
			{true, false, true}
		};

		/*List<boolean[][]> preimages = ca.getAllowedPreimagesCombiningColumns(
			ca.getAllowedPreimagesCombiningColumns(
				ca.getAllowedPreimagesForRow(new boolean[]{true,false,true}),
				ca.getAllowedPreimagesForRow(new boolean[]{false,true,false})
			),
			ca.getAllowedPreimagesForRow(new boolean[]{true, false, true})
		);

		System.out.println();
		preimages.forEach(image -> ca.validate(image));
		//System.out.println(preimages);*/

		falseNeighbours
			.forEach(booleans -> cellularAutomata.printMask(booleans));
	}
}
