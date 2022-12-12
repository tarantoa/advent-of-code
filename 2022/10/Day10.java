import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class Day10 {

  private static final String FILENAME = "day_10.txt";
  private static final String DATA_DIRECTORY = "../data/";
  private static final String TEST_DATA_DIRECTORY = "../testdata/";

  public static void main(String[] args) {
    String filepath = TEST_DATA_DIRECTORY + FILENAME;
    if (args.length == 1 && args[0].equals("--notest")) {
      filepath = DATA_DIRECTORY + FILENAME;
    }

    List<String> inputs = new ArrayList<>();
    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      reader.lines().forEach(inputs::add);
    } catch (Exception e) {
      e.printStackTrace();
    }

    int regValue = 1;
    int clockCycle = 1;
    List<Integer> interestingSignals = new ArrayList<>();
    for (String input : inputs) {
      String[] inputTokens = input.split(" ");
      Integer value = 0;
      int cycleLength = 1;
      switch (inputTokens[0]) {
      case "addx":
        cycleLength = 2;
        value = Integer.parseInt(inputTokens[1]);
        break;
      case "noop":
        break;
      }
      for (int i = 0; i < cycleLength; i++) {
        if ((clockCycle - 20) % 40 == 0) {
          interestingSignals.add(clockCycle * regValue);
        }
        clockCycle++;
      }
      regValue += value;
    }


    System.out.printf("Sum of interesting signals: %d\n", interestingSignals.stream()
      .reduce(0, Integer::sum));   
  }
}