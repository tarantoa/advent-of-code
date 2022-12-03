package aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

  private static final String TEST_DATA_FILEPATH = "../../data/day_01_test.txt";
  private static final String DATA_FILEPATH = "../../data/day_01.txt";

  public static List<Long> readCalorieCountsFromfile() {
    List<Long> elfCalories = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILEPATH))) {
      String line;
      Long calorieCount = 0L;
      while ((line = br.readLine()) != null)  {
        if (line.isEmpty()) {
          elfCalories.add(calorieCount);
          calorieCount = 0L;
        } else {
          calorieCount += Long.parseLong(line);
        }
      }
      br.close();
      elfCalories.add(calorieCount);
    } catch (Exception e) {
      e.printStackTrace();
    }
    return elfCalories;
  }

  public static void main(String[] args) {
    List<Long> elfCalories = readCalorieCountsFromfile();
    Collections.sort(elfCalories);
    Collections.reverse(elfCalories);
    System.out.printf("Most calories: %d\n", elfCalories.get(0)); // Part 1
    if (elfCalories.size() < 3) {
      throw new IllegalStateException("there must be at least three calorie entries.");
    }
    System.out.printf("Calories from top 3 elves: %d\n", 
      elfCalories.get(0) + elfCalories.get(1) + elfCalories.get(2));
  }
}