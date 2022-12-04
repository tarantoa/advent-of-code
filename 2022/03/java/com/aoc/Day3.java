import java.io.BufferedReader;
import java.io.FileReader;
import java.util.stream.Collectors;

public class Day3 {

  private static final String DISABLE_TEST_FLAG = "--notest";

  private static final String DATA_FILEPATH = "../../data/day_03.txt";
  private static final String TEST_DATA_FILEPATH = "../../data/day_03_test.txt";

  public static void main(String[] args) {
    String dataDir = TEST_DATA_FILEPATH;
    if (args.length == 1 && args[0].equals(DISABLE_TEST_FLAG)) {
      dataDir = DATA_FILEPATH;
    }

    try (BufferedReader br = new BufferedReader(new FileReader(dataDir))) {
      long prioritySum = br.lines()
        .map(line -> {
          int middleIndex = line.length() / 2;
          String leftHalf = line.substring(0, middleIndex);
          String rightHalf = line.substring(middleIndex);
          return rightHalf.chars()
            .mapToObj(i -> Character.toString((char)i))
            .filter(leftHalf::contains)
            .collect(Collectors.toSet())
            .stream()
            .reduce("", (intersection, currentChar) -> intersection + currentChar);
          })
        .map(intersection -> intersection.chars()
          .reduce(0, (sum, currentCharacter) -> sum + priorityValue(currentCharacter)))
        .reduce(0, (cumSum, currentLineSum) -> cumSum + currentLineSum);
        System.out.printf("Priority Sum: %d\n", prioritySum);
      br.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static final boolean isUpperCase(final int character)  {
    return 65 <= character && character <= 90;
  }

  private static final boolean isLowerCase(final int character) {
    return 97 <= character && character <= 122;
  }

  private static final int priorityValue(int val) {
    if (isLowerCase(val)) return val - '`';
    if (isUpperCase(val)) return val - '&';
    return 0;
  }
}