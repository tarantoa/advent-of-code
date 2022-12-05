import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day5 {

  private static final String FILENAME = "day_05.txt";

  private static final String DATA_FILEPATH = "../data/";
  private static final String TEST_DATA_FILEPATH = "../testdata/";

  private static boolean isUpperCaseLetter(final char c) {
    return 65 <= c && c <= 90;
  }

  private static final void printSupplyStack(List<Stack<Character>> supplies) {
    System.out.printf("Top boxes: %s\n", 
      supplies.stream()
        .filter(supplyStack -> !supplyStack.isEmpty())
        .map(stack -> Character.toString(stack.peek()))
        .reduce("", (topBoxes, box) -> topBoxes + box));
  }

  public static void main(String[] args) {
    String dataDir = args.length == 1 && args[0].equals("--notest") ?
      DATA_FILEPATH : TEST_DATA_FILEPATH;
    dataDir += FILENAME;

    List<List<Character>> inputStackList = IntStream.range(0, 9)
      .mapToObj(i -> new ArrayList<Character>())
      .collect(Collectors.toList());
    List<Stack<Character>> supplyStacks = IntStream.range(0, 9)
      .mapToObj(i -> new Stack<Character>())
      .collect(Collectors.toList());
    List<Stack<Character>> supplyStacksPart2 = IntStream.range(0, 9)
      .mapToObj(i -> new Stack<Character>())
      .collect(Collectors.toList());

    try (BufferedReader reader = new BufferedReader(new FileReader(dataDir))) {
      String line;
      while (!(line = reader.readLine()).isEmpty()) {
        for (int i = 1; i < line.length(); i += 4) {
          if (isUpperCaseLetter(line.charAt(i))) {
            inputStackList.get(i / 4).add(line.charAt(i));
          }
        }
      }

      inputStackList.forEach(Collections::reverse);
      IntStream.range(0, inputStackList.size())
        .forEach(i -> inputStackList.get(i)
          .forEach(c -> {
            supplyStacks.get(i).push(c);
            supplyStacksPart2.get(i).push(c);
          }));

      reader.lines().forEach(instruction -> {
        String[] instructionSegments = instruction.replaceAll("[a-z]", "").trim().replaceAll("  ", " ").split(" ");
        Integer numBoxes = Integer.parseInt(instructionSegments[0]);
        Integer source = Integer.parseInt(instructionSegments[1]) - 1;
        Integer destination = Integer.parseInt(instructionSegments[2]) - 1;
        List<Character> boxesForPart2 = new ArrayList<>();
        IntStream.range(0, Math.min(numBoxes, supplyStacks.get(source).size()))
          .forEach(i -> {
            supplyStacks.get(destination).push(supplyStacks.get(source).pop());
            boxesForPart2.add(supplyStacksPart2.get(source).pop());
          });
        Collections.reverse(boxesForPart2);
        boxesForPart2.forEach(supplyStacksPart2.get(destination)::push);
      });

      printSupplyStack(supplyStacks);
      printSupplyStack(supplyStacksPart2);

      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}