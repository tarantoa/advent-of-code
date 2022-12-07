package aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class Day1 {

  private static final String TEST_DATA_FILEPATH = "../../testdata/day_01.txt";
  private static final String DATA_FILEPATH = "../../data/day_01.txt";

  private static Collector<String, List<List<String>>, List<List<String>>> toSplitList(final String delimiter) {
    return Collector.of(
      ArrayList<List<String>>::new,
      (splitList, item) -> {
        List<String> list = splitList.isEmpty() ? null : splitList.get(splitList.size() - 1);
        if (list == null || item.equals(delimiter)) {
          splitList.add(list = new ArrayList<>());
          return;
        }
        list.add(item);
      },
      (r1, r2) -> { throw new UnsupportedOperationException("Parallel processing not supported."); }
    );
  }

  public static void main(String[] args) {
    final String filepath = 
      args.length == 1 && args[0].equals("--notest") ? DATA_FILEPATH : TEST_DATA_FILEPATH;
    List<Long> calorieTotals = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      calorieTotals = reader.lines()
        .collect(toSplitList(""))
        .stream()
        .map(foodItems -> foodItems.stream()
          .map(Long::parseLong)
          .reduce(0L, Long::sum))
        .collect(Collectors.toList());
    } catch (Exception e) {
      e.printStackTrace();
    }

    if (calorieTotals.isEmpty()) {
      System.out.println("Result calorie list is empty.");
      return;
    }

    Collections.sort(calorieTotals);
    Collections.reverse(calorieTotals);

    System.out.printf("Highest calorie count: %d\n", calorieTotals.get(0)); // Part 1
    System.out.printf("Sum of the highest three calorie counts: %d\n",
      calorieTotals.get(0) + calorieTotals.get(1) + calorieTotals.get(2));
  }
}