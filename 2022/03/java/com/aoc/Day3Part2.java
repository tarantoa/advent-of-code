import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;

public class Day3Part2 {

  private static final String DATA_FILEPATH = "../../data/day_03.txt";
  private static final String TEST_DATA_FILEPATH = "../../data/day_03_test.txt";

  private static final int GROUP_SIZE = 3;

  public static Collector<String, List<List<String>>, List<List<String>>> groupList() {
    final int groupSize = 3;
    return Collector.of(
            ArrayList<List<String>>::new,
            (list, inventory) -> {
                List<String> group = list.isEmpty() ? null : list.get(list.size() - 1);
                if (group == null || group.size() == GROUP_SIZE) {
                  list.add(group = new ArrayList<>());
                }
                group.add(inventory);
            },
            (r1, r2) -> { throw new UnsupportedOperationException("Parallel processing not supported"); }
    );
  };

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

  public static void main(String[] args) {
    String filepath = args.length == 1 && args[0].equals("--notest") ? DATA_FILEPATH : TEST_DATA_FILEPATH;
    try (BufferedReader buffReader = new BufferedReader(new FileReader(filepath))) {
      long badgePrioritySum = buffReader.lines()
        .collect(groupList())
        .stream()
        .map(groupInventory -> groupInventory.get(0).chars()
          .filter(currentCharacter -> {
            String badgeCandidate = Character.toString((char) currentCharacter);
            return groupInventory.get(1).contains(badgeCandidate) && groupInventory.get(2).contains(badgeCandidate);
          })
          .findFirst()
          .getAsInt())
        .map(badge -> priorityValue(badge))
        .reduce(0, (sum, badgeValue) -> sum + badgeValue);
      System.out.printf("Priority sum of badges: %d\n", badgePrioritySum);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}