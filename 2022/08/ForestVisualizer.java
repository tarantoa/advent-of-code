import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ForestVisualizer {

  private static final String DATA_FILENAME = "day_08.txt";
  private static final String DATA_DIRECTORY = "../data/";
  private static final String TEST_DATA_DIRECTORY = "../testdata/"; 

  private static final String TEXT_RESET = "\033[0m";
  private static final String GREEN = "\033[0;32m";
  private static final String YELLOW = "\033[0;33m";
  private static final String RED = "\033[0;31m";
  private static final String BLACK = "\033[0;30m";

  private static List<List<Integer>> forestData = new ArrayList<>();

  private ForestVisualizer() {}

  private static boolean isOnPerimeter(final int row, final int col) {
    return row == 0 || row == forestData.size() - 1 || col == 0 || col == forestData.get(0).size() - 1;
  }
 
  private static boolean isTreeVisible(final int row, final int col) {
    if (isOnPerimeter(row, col)) {
      return true;
    }

    int treeHeight = forestData.get(row).get(col);
    return Stream.of(
        IntStream.range(1, col + 1).mapToObj(i -> forestData.get(row).get(col - i)),
        IntStream.range(1, forestData.get(row).size() - col).mapToObj(i -> forestData.get(row).get(col + i)),
        IntStream.range(1, row + 1).mapToObj(i -> forestData.get(row - i).get(col)),
        IntStream.range(1, forestData.size() - row).mapToObj(i -> forestData.get(row + i).get(col)))
      .filter(treeLine -> treeLine.allMatch(neighborHeight -> neighborHeight < treeHeight))
      .findFirst()
      .isPresent();
  }

  private static String getCharacter(final int row, final int col) {
    int treeHeight = forestData.get(row).get(col);
    if (treeHeight > 6) return RED + "@";
    if (treeHeight > 3) return YELLOW + "O";
    return GREEN + "o";
  }

  public static void main(String[] args) {
    String filepath = 
      (args.length == 1 && args[0].equals("--notest") ? DATA_DIRECTORY : TEST_DATA_DIRECTORY) + DATA_FILENAME;

    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      forestData = reader.lines()
        .map(treeLine -> Arrays.stream(treeLine.split(""))
          .map(Integer::parseInt)
          .collect(Collectors.toList()))
        .collect(Collectors.toList());
      reader.close();
    } catch (Exception e) {
        e.printStackTrace();
        return;
    }

    String forest = String.join("\n", IntStream.range(0, forestData.size())
      .mapToObj(row -> String.join(TEXT_RESET, IntStream.range(0, forestData.get(row).size())
        .mapToObj(col -> isTreeVisible(row, col) ? getCharacter(row, col) : BLACK + ".")
        .collect(Collectors.toList())))
      .collect(Collectors.toList()));
    System.out.println(forest);
  }
}