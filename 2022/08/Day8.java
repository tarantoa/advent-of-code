import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day8 {
  
  private static final String DATAFILE = "day_08.txt";

  private static final String DATA_DIRECTORY = "../data/";
  private static final String TEST_DATA_DIRECTORY = "../testdata/";

  private static List<List<Integer>> forest = new ArrayList<>();

  private static boolean isTreeVisibleFromPerimeter(IntStream treeLine, final int treeHeight) {
    return !treeLine
      .filter(neighborHeight -> neighborHeight >= treeHeight)
      .findFirst()
      .isPresent();
  }
  
  private static boolean isVisible(final int row, final int col) {
    if (row == 0 || row == forest.size() - 1 || 
        col == 0 || col == forest.get(row).size() - 1) {
      return true;
    }
    final int treeHeight = forest.get(row).get(col);
    boolean leftPerimeterVisibility = 
      isTreeVisibleFromPerimeter(IntStream.range(0, col).map(i -> forest.get(row).get(i)), treeHeight);
    boolean rightPerimeterVisibility = 
      isTreeVisibleFromPerimeter(
        IntStream.range(1, forest.get(row).size() - col).map(i -> forest.get(row).get(col + i)), treeHeight);
    boolean topPerimeterVisibility = 
      isTreeVisibleFromPerimeter(IntStream.range(0, row).map(i -> forest.get(i).get(col)), treeHeight);
    boolean bottomPerimeterVisibility =
      isTreeVisibleFromPerimeter(
        IntStream.range(1, forest.size() - row).map(i -> forest.get(row + i).get(col)), treeHeight);
    return leftPerimeterVisibility || rightPerimeterVisibility || topPerimeterVisibility || bottomPerimeterVisibility;
  }

  private static int getScenicScore(final int row, final int col) {
    final int treeHeight = forest.get(row).get(col);
    int distanceLeft = IntStream.range(1, col + 1)
      .filter(i -> forest.get(row).get(col - i) >= treeHeight)
      .findFirst()
      .orElse(col);
    int distanceRight = IntStream.range(1, forest.get(0).size() - col)
      .filter(i -> forest.get(row).get(col + i) >= treeHeight)
      .findFirst()
      .orElse(forest.get(0).size() - col - 1);
    int distanceTop = IntStream.range(1, row)
      .filter(i -> forest.get(row - i).get(col) >= treeHeight)
      .findFirst()
      .orElse(row);
    int distanceBottom = IntStream.range(1, forest.size() - row)
      .filter(i -> forest.get(row + i).get(col) >= treeHeight)
      .findFirst()
      .orElse(forest.size() - row - 1);
    return distanceLeft * distanceRight * distanceTop * distanceBottom;
  }

  public static void main(String[] args) {
    String dataDir = 
      (args.length == 1 && args[0].equals("--notest") ? DATA_DIRECTORY : TEST_DATA_DIRECTORY) + DATAFILE;
    try (BufferedReader reader = new BufferedReader(new FileReader(dataDir))) {
      forest = reader.lines()
        .map(line -> Arrays.stream(line.split(""))
          .map(Integer::parseInt)
          .collect(Collectors.toList()))
        .collect(Collectors.toList());
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    final int rows = forest.size();
    final int cols = forest.get(0).size();

    System.out.printf("Number of trees visible: %d\n",
      IntStream.range(0, rows)
        .mapToObj(treeRow -> IntStream.range(0, cols)
          .filter(tree -> isVisible(treeRow, tree))
          .mapToObj(tree -> isVisible(treeRow, tree))
          .collect(Collectors.toList()))
        .flatMap(list -> list.stream())
        .filter(isVisible -> isVisible)
        .count());
    System.out.printf("Highest scenic score: %d\n",
      IntStream.range(1, rows - 1)
        .flatMap(treeRow -> IntStream.range(1, cols - 1)
          .map(tree -> getScenicScore(treeRow, tree)))
        .max()
        .getAsInt());
  }
}