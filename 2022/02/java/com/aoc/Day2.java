import java.io.BufferedReader;
import java.io.FileReader;

public class Day2 {
  
  private static final String DATA_FILEPATH = "../../data/day_02.txt";
  private static final String TEST_DATA_FILEPATH = "../../data/day_02_test.txt";

  private static final String ROCK = "rock";
  private static final String PAPER = "paper";
  private static final String SCISSORS = "scissors";

  private static final int WIN_POINTS = 6;
  private static final int TIE_POINTS = 3;

  private enum Hand {
    Rock(ROCK, 1, SCISSORS, PAPER),
    Paper(PAPER, 2, ROCK, SCISSORS),
    Scissors(SCISSORS, 3, PAPER, ROCK);

    private final String name;
    private final int value;
    private final String beats;
    private final String losesTo;

    private Hand(final String name, final int value, final String beats, final String losesTo) {
      this.name = name;
      this.value = value;
      this.beats = beats;
      this.losesTo = losesTo;
    }

    public boolean doesBeat(final Hand other) {
      return other.name.equals(beats);
    }

    public static Hand decode(final String code) {
      if (code == null || code.isEmpty()) throw new IllegalArgumentException("code cannot be blank.");
      if (code.equals("A") || code.equals("X")) return Rock;
      if (code.equals("B") || code.equals("Y")) return Paper;
      if (code.equals("C") || code.equals("Z")) return Scissors;
      throw new IllegalArgumentException("invalid hand code. { " + code + " }");
    }

    public static Hand fromString(final String name) {
      if (name == null || name.isEmpty()) throw new IllegalArgumentException("name cannot be blank.");
      if (name.equals(ROCK)) return Rock;
      if (name.equals(PAPER)) return Paper;
      if (name.equals(SCISSORS)) return Scissors;
      throw new IllegalArgumentException("invalid hand name. { " + name + " }");
    }

    public static Hand decode2(final Hand other, final String code) {
      if (other == null) throw new IllegalArgumentException("other cannot be null");
      if (code == null || code.isEmpty()) throw new IllegalArgumentException("code cannot be blank.");
      if (code.equals("X")) return fromString(other.beats);
      if (code.equals("Y")) return other;
      if (code.equals("Z")) return fromString(other.losesTo);
      throw new IllegalArgumentException("invalid hand code. { " + code + " }");
    }
  }
  
  private Day2() {}

  private static GameResult playHands(final Hand elfHand1, final Hand elfHand2) {
    int elfScore1 = elfHand1.value;
    int elfScore2 = elfHand2.value;
    if (elfHand1.name.equals(elfHand2.name)) return new GameResult(elfScore1 + TIE_POINTS, elfScore2 + TIE_POINTS);
    if (elfHand1.doesBeat(elfHand2)) return new GameResult(elfScore1 + WIN_POINTS, elfScore2);
    return new GameResult(elfScore1, elfScore2 + WIN_POINTS);
  }
  
  public static void main(String[] args) {
    GameResult resultPart1 = new GameResult(0, 0);
    GameResult resultPart2 = new GameResult(0, 0);
    try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILEPATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] handCodes = line.split(" ");
        Hand elfHand1 = Hand.decode(handCodes[0]);
        Hand elfHand2 = Hand.decode(handCodes[1]);
        Hand elfHand2Part2 = Hand.decode2(elfHand1, handCodes[1]);
        resultPart1.combine(playHands(elfHand1, elfHand2));
        resultPart2.combine(playHands(elfHand1, elfHand2Part2));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.printf(
      "Scores { Player1: %d, Player2: %d }\n", resultPart1.playerPoints1, resultPart1.playerPoints2); // Part 1
    System.out.printf(
      "Scores { Player1: %d, Player2: %d }\n", resultPart2.playerPoints2, resultPart2.playerPoints2); // Part 2
  }

  private static final class GameResult {
    
    private int playerPoints1;
    private int playerPoints2;

    private GameResult(final int playerPoints1, final int playerPoints2) {
      this.playerPoints1 = playerPoints1;
      this.playerPoints2 = playerPoints2;
    }

    private void combine(final GameResult other) {
      this.playerPoints1 += other.playerPoints1;
      this.playerPoints2 += other.playerPoints2;
    }
  }
}