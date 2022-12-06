import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.HashSet;

public class Day6 {

  private static final String DATA_FILE  = "day_06.txt";

  private static final String DATA_DIRECTORY = "../data/";
  private static final String TEST_DATA_DIRECTORY = "../testdata/";

  private Day6() {}

  private static final long getFirstIndexAfterMarker(final String line, final int segmentLength) {
    for (int i = 0; i < line.length() - segmentLength; i++) {
      String segment = line.substring(i, i + segmentLength);
      if ((new HashSet<String>(Arrays.asList(segment.split("")))).size() == segmentLength) {
        return i + segmentLength;
      }
    }
    return -1;
  }

  private static final void printMarkerIndicesForInput(final String filepath, final int segmentLength) {
    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
      reader.lines()
        .forEach(inputLine -> System.out.printf("First index after %d-length marker [%s]: %d\n",
          segmentLength, inputLine.substring(0, 5), getFirstIndexAfterMarker(inputLine, segmentLength)));
      reader.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void main(String[] args) {
    String filepath = 
      (args.length == 1 && args[0].equals("--notest") ? DATA_DIRECTORY : TEST_DATA_DIRECTORY) + DATA_FILE;
    printMarkerIndicesForInput(filepath, 4);
    printMarkerIndicesForInput(filepath, 14);
  }

}