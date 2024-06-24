package generic;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;


public class Misc {
    
    public static void writeToFile(String fileName, String content) {
		try (PrintWriter out = new PrintWriter(new FileWriter(fileName, true))) {
			out.println(content);
		} catch (IOException e) {
			printErrorAndExit("Error writing to file: " + e.getMessage());
		}
	}
    public static void printErrorAndExit(String errorMessage) {
        System.err.println("Error: " + errorMessage);
        System.exit(1);
    }
}