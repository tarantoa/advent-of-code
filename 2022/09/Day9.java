import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day9 {

  public enum Direction{
    Up("U"),
    Right("R"),
    Down("D"),
    Left("L");

    private final String value;

    private Direction(final String value) {
      this.value = value;
    }

    private static Direction of(final String direction) {
      switch (direction) {
      case "U":
        return Up;
      case "R":
        return Right;
      case "D":
        return Down;
      case "L":
        return Left;
      }
      throw new IllegalArgumentException("invalid direction string '" + direction + "'");
    }
  }

  private static final String FILE_NAME = "day_09.txt";
  private static final String DATA_DIRECTORY = "../data/";
  private static final String TEST_DATA_DIRECTORY = "../testdata/";

  private static List<String> grid;

  private Day9() {}

  public static void main(String[] args) {
    String filepath = TEST_DATA_DIRECTORY + FILE_NAME;
    Integer ropeSize = 2; 
    if (args.length > 0) {
      if (args[0].equals("--notest")) {
        filepath = DATA_DIRECTORY + FILE_NAME;
      }
    }
    if (args.length > 1) {
      System.out.println(args[1]);
      ropeSize = Integer.parseInt(args[1]);
    }

    Location startingLocation = new Location(0, 0);
    List<Location> rope = IntStream.range(0, ropeSize)
      .mapToObj(i -> new Location(startingLocation))
      .collect(Collectors.toList());

    List<Instruction> instructions = new ArrayList<>();
    try (BufferedReader buffReader = new BufferedReader(new FileReader(filepath))) {
      buffReader.lines()
        .map(line -> {
          String[] inputTokens = line.split(" ");
          return new Instruction(Direction.of(inputTokens[0]), Integer.parseInt(inputTokens[1]));
        })
        .forEach(instructions::add);
    } catch (Exception e) {
      e.printStackTrace();
      return;
    }

    Set<Location> uniqueTailLocations = new HashSet<>();
    uniqueTailLocations.add(new Location(rope.get(ropeSize - 1)));
    IntStream.range(0, instructions.size())
      .mapToObj(i -> instructions.get(i))
      .forEach(instruction -> {
        IntStream.range(0, instruction.steps)
          .forEach(i -> {
            rope.get(0).move(instruction.direction);
            IntStream.range(1, rope.size())
              .filter(knot -> !rope.get(knot).isTouching(rope.get(knot - 1)))
              .forEach(knot -> rope.get(knot).moveTo(rope.get(knot - 1)));
            uniqueTailLocations.add(new Location(rope.get(rope.size() - 1)));
          });
      });
    System.out.println("Unique tail locations: " + uniqueTailLocations.size());
  }

  public static class Instruction {

    private Direction direction;
    private int steps;

    public Instruction(Direction direction, int steps) {
      this.direction = direction;
      this.steps = steps;
    }
  }

  public static class Location {

    private int row;
    private int col;

    public Location(int row, int col) {
      this.row = row;
      this.col = col;
    }

    public Location(Location source) {
      this(source.row, source.col);
    }

    public void moveTo(Location other) {
      if (other.row > row) {
        ++row;
      } else if (other.row < row) {
        --row;
      }
      if (other.col > col) {
        ++col;
      } else if (other.col < col) {
        --col;
      }
    }

    public void move(final Direction direction) {
      switch(direction) {
      case Up:
        ++row;
        return;
      case Right:
        ++col;
        return;
      case Down:
        --row;
        return;
      case Left:
        --col;
        return;
      }
      throw new IllegalArgumentException("unable to process direction '" + direction.value + "'.");
    }

    public boolean isTouching(Location other) {
      return Math.abs(row - other.row) <= 1 && Math.abs(col - other.col) <= 1;
    }

    @Override
    public String toString() {
      return String.format("(%d, %d)", row, col);
    }

    @Override
    public int hashCode() {
      return Objects.hash(row, col);
    }

    @Override
    public boolean equals(Object other) {
      if (other == null || other.getClass() != this.getClass()) {
        return false;
      }
      return row == ((Location) other).row && col == ((Location) other).col;
    }
  }
}