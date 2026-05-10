import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileManger {
    public static List<String> readFile(String filename) {
        List<String> data = new ArrayList<>();
        try {
            File file = new File(filename);
            Scanner a = new Scanner(file);
            while (a.hasNextLine()) {
                data.add(a.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        }
        return data;
    }

    public static void writeFile(String filename, List<String> data) {
        try {
            FileWriter writer = new FileWriter(filename);
            for (String line : data) {
                writer.write(line+"\n");
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Error writing file: " + filename);
        }
    }

    public static void appendToFile(String filename, String line) {
        try {
            FileWriter writer = new FileWriter(filename, true);
            writer.write(line + "\n");
            writer.close();
        } catch (IOException e) {
            System.out.println("Error appending file: " + filename);
        }
    }
}
