import java.io.*;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;
import java.util.Scanner;

public class IOHelper {
    private IOHelper() {
    }

    public static void prompt(String message) {
        System.out.format("%s> ", message);
    }

    public static String readInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
        // don't close Scanner on stdin
    }

    private static Square parseCoordinates(String line) {
        InputStream byteStream = new ByteArrayInputStream(line.getBytes());
        try (Scanner scanner = new Scanner(byteStream)) {
            int row = scanner.nextInt();
            int column = scanner.nextInt();
            return new Square(row, column);
        }
    }

    public static Square readCoordinates() {
        while (true) {
            IOHelper.prompt("Choose a square [row column]");
            String line = IOHelper.readInput();
            try {
                return IOHelper.parseCoordinates(line);
            } catch (IllegalArgumentException e) {
                IOHelper.error("You must input a valid square. Try again.");
            }
        }
    }

    public static void gameOver(Player winner, Board board) {
        System.out.println("Game over!");
        if (winner != null) {
            // null winner when game drawn
            System.out.println(winner.mark() + " wins!");
        }
        System.out.println(board);
    }

    public static void gameOver(Board board) {
        gameOver(null, board);
    }

    public static void showBoard(Board board) {
        System.out.println(board);
    }

    public static void error(String message) {
        System.err.println(message);
    }

    public static String lossHistoryFilename() throws IOException {
        InputStream in = TicTacToe.class.getClassLoader().getResourceAsStream("config.properties");
        Properties properties = new Properties();
        properties.load(in);
        return properties.getProperty("LOSS_HISTORY_FILENAME");
    }

    public static File home(String filename) {
        String homeDirectory = System.getenv("HOME");
        Objects.requireNonNull(homeDirectory, "HOME must be set in environment");
        return Paths.get(homeDirectory).resolve(filename).toFile();
    }

    public static LossHistory loadLossHistory(String filename) {
        try {
            try (FileInputStream fin = new FileInputStream(home(filename))) {
                try (ObjectInputStream oin = new ObjectInputStream(fin)) {
                    return (LossHistory) oin.readObject();
                }
            }
        } catch (Exception ex) {
            // If *anything* goes wrong finding, reading, or deserializing,
            // just create a new loss history...
            return new LossHistory();
        }
    }

    public static void persistLossHistory(LossHistory history, String filename) throws IOException {
        try (FileOutputStream fout = new FileOutputStream(filename)) {
            try (ObjectOutputStream oout = new ObjectOutputStream(fout)) {
                oout.writeObject(history);
            }
        }
    }
}
