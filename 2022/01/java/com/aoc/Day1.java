package aoc;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Day1 {

  private static final String TEST_DATA_FILEPATH = "../../data/day_01_test.txt";
  private static final String DATA_FILEPATH = "../../data/day_01.txt";

  public static List<Elf> readElvesFromFile() {
    List<Elf> elves = new ArrayList<>();
    try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILEPATH))) {
      List<Long> food = new ArrayList<>();
      String line;
      while ((line = br.readLine()) != null)  {
        if (line.isEmpty()) {
          elves.add(Elf.newBuilder().withFood(food).build());
          food = new ArrayList<>();
        } else {
          food.add(Long.parseLong(line));
        }
      }
      br.close();
      elves.add(Elf.newBuilder().withFood(food).build());
    } catch (Exception e) {
      e.printStackTrace();
    }
    return elves;
  }

  public static void main(String[] args) {
    List<Elf> elves = readElvesFromFile();
    Collections.sort(elves);
    System.out.printf("Most calories: %d\n", elves.get(0).getCalories()); // Part 1
    if (elves.size() < 3) {
      throw new IllegalStateException("there must be at least three elves.");
    }
    System.out.printf("Calories from top 3 elves: %d\n", 
      elves.get(0).getCalories() + elves.get(1).getCalories() + elves.get(2).getCalories()); // Part 2
  }
}