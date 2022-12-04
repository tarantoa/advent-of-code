import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.stream.Stream;

public class Day4 {

  private static final String DATA_FILEPATH = "../../data/day_04.txt";
  private static final String TEST_DATA_FILEPATH = "../../testdata/day_04.txt";

  private static Stream<Pair> getAssignmentPairs(final BufferedReader reader) 
      throws FileNotFoundException, IOException {
    try {
      Stream<Pair> assignmentPairs = reader.lines()
        .map(rawAssignments -> rawAssignments.split(","))
        .map(intervalGroup -> {
          String[] firstInterval = intervalGroup[0].split("-");
          String[] secondInterval = intervalGroup[1].split("-");
          return new Pair(
            new Interval(Integer.parseInt(firstInterval[0]), Integer.parseInt(firstInterval[1])),
            new Interval(Integer.parseInt(secondInterval[0]), Integer.parseInt(secondInterval[1])));
        });
      return assignmentPairs;
    } catch (Exception e) {
      throw e;
    }
  }

  public static void main(String[] args) {
    String dataDir = args.length == 1 && args[0].equals("--notest") ? DATA_FILEPATH : TEST_DATA_FILEPATH;

    // Part 1
    try (BufferedReader reader = new BufferedReader(new FileReader(dataDir))) {
      System.out.printf("Number of intervals enclosed: %d\n", 
        getAssignmentPairs(reader)
          .filter(pair -> pair.first.encloses(pair.second) || pair.second.encloses(pair.first))
          .count());
    } catch (Exception e) {
      e.printStackTrace();
    }

    // Part 2
    try (BufferedReader reader = new BufferedReader(new FileReader(dataDir))) {
      System.out.printf("Overlapping intervals: %d\n",
        getAssignmentPairs(reader).filter(pair -> pair.first.contains(pair.second)).count());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private static final class Interval {
    private final int start;
    private final int stop;

    private Interval(final int start, final int stop) {
      this.start = start;
      this.stop = stop;
    }

    private boolean contains(final Interval other) {
      return other.stop >= stop ? other.start <= stop : other.stop >= start;
    }

    private boolean encloses(final Interval other) {
      return other.start >= start && other.stop <= stop;
    }

    @Override
    public String toString() {
      return String.format("%d-%d", start, stop);
    }
  }

  private static final class Pair {
    private final Interval first;
    private final Interval second;

    private Pair(final Interval first, final Interval second) {
      this.first = first;
      this.second = second;
    }

    @Override
    public String toString() {
      return String.format("{ [%s], [%s] }", first.toString(), second.toString());
    }
  }
}