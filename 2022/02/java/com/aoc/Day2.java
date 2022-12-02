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
    Rock(ROCK, 1, SCISSORS),
    Paper(PAPER, 2, ROCK),
    Scissors(SCISSORS, 3, PAPER);

    private final String name;
    private final int value;
    private final String beats;

    private Hand(final String name, final int value, final String beats) {
      this.name = name;
      this.value = value;
      this.beats = beats;
    }

    public boolean doesBeat(Hand other) {
      return other.name.equals(beats);
    }

    public static Hand decode(String code) {
      if (code.equals("A") || code.equals("X")) return Rock;
      if (code.equals("B") || code.equals("Y")) return Paper;
      if (code.equals("C") || code.equals("Z")) return Scissors;
      throw new IllegalArgumentException("invalid hand code.");
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
    int elfScore1 = 0; 
    int elfScore2 = 0;
    try (BufferedReader br = new BufferedReader(new FileReader(DATA_FILEPATH))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] handCodes = line.split(" ");
        Hand elfHand1 = Hand.decode(handCodes[0]); Hand elfHand2 = Hand.decode(handCodes[1]);
        GameResult result = playHands(elfHand1, elfHand2);
        elfScore1 += result.playerPoints1;
        elfScore2 += result.playerPoints2;
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    System.out.printf("Scores { Player1: %d, Player2: %d }\n", elfScore1, elfScore2); // Part 1
  }

  private static final class GameResult {
    
    private final int playerPoints1;
    private final int playerPoints2;

    private GameResult(final int playerPoints1, final int playerPoints2) {
      this.playerPoints1 = playerPoints1;
      this.playerPoints2 = playerPoints2;
    }
  }
}