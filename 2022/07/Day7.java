import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Stream;

public class Day7 {

  private static final String DATA_FILE = "day_07.txt";

  private static final String DATA_DIRECTORY = "../data/";
  private static final String TEST_DATA_DIRECTORY = "../testdata/";

  private static Directory root = new Directory("/", null);
  private static Directory cwd = root;


  private static Directory changeDirectory(final String target) {
    if (target.equals("..")) {
      return cwd.parent;
    }
    return cwd.directories.stream()
      .filter(dir -> dir.name.equals(target))
      .findFirst()
      .get();
  }

  private static void handleCommand(final String command) {
    if (command == null || command.isEmpty()) return;

    String[] commandTokens = command.split(" ");
    if (commandTokens[1].equals("cd")) {
      cwd = changeDirectory(commandTokens[2]);
    }
  }

  private static void makeFileOrDirectory(final String command) {
    String[] commandTokens = command.split(" ");
    try {
      Long fileSize = Long.parseLong(commandTokens[0]);
      cwd.files.add(new File(commandTokens[1], fileSize));
    } catch (NumberFormatException e) {
      cwd.directories.add(new Directory(commandTokens[1], cwd));
    }
  }

  private static void processInput(final String line) {
    if (line == null || line.isEmpty()) {
      return;
    }
    if (line.startsWith("$")) {
      handleCommand(line);
    } else {
      makeFileOrDirectory(line);
    }
  }

  public static void main(String[] args) {
    String dataDir = 
      (args.length == 1 && args[0].equals("--notest") ? DATA_DIRECTORY : TEST_DATA_DIRECTORY) + DATA_FILE;
    
    try (BufferedReader reader = new BufferedReader(new FileReader(dataDir))) {
      reader.lines().skip(1)
        .forEach(line -> processInput(line));
    } catch(Exception e) {
      e.printStackTrace();
    }

    Long fileSizeSum = 0L;
    Long rootDirectorySize = root.getDirectorySize();
    Long smallestOverThreshold = rootDirectorySize;
    Long freeSpace = 70000000L - rootDirectorySize;
    Stack<Directory> toTraverse = new Stack<>();
    toTraverse.push(root);
    while (!toTraverse.isEmpty()) {
      cwd = toTraverse.pop();
      for (Directory d : cwd.directories) {
        toTraverse.push(d);
      }
      Long directorySize = cwd.getDirectorySize();
      if (directorySize <= 100000L) {
        fileSizeSum += directorySize;
      }
      if (freeSpace + directorySize >= 30000000L) {
        smallestOverThreshold = 
          smallestOverThreshold > directorySize ? directorySize : smallestOverThreshold;
      }
    }
    System.out.printf("File Size sum: %d\n", fileSizeSum); // Part 1
    System.out.printf("Delete dir: [%d]\n", smallestOverThreshold); // Part 2
  }

  private static final class File {

    private final String name;
    private final long size;

    private File(final String name, final long size) {
      this.name = name;
      this.size = size;
    }

    private long getFileSize() {
      return size;
    }
  }

  private static final class Directory {

    private final String name;
    private final Directory parent;
    private final Set<Directory> directories;
    private final List<File> files;

    private Directory(final String name, final Directory parent) {
      this.name = name;
      this.parent = parent;
      this.directories = new HashSet<>();
      this.files = new ArrayList<>();
    }

    private Long getDirectorySize() {
      return 
        Stream.concat(files.stream().map(file -> file.getFileSize()), 
            directories.stream().map(directory -> directory.getDirectorySize()))
          .reduce(0L, Long::sum);
    }
  }
}