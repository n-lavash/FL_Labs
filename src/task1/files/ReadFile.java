package task1.files;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadFile {
    public static List<String> readFile(File file) {
        List<String> result = new ArrayList<>();

        try (FileReader fileReader = new FileReader(file); BufferedReader reader = new BufferedReader(fileReader)) {
            String line;

            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return result;
    }
}
