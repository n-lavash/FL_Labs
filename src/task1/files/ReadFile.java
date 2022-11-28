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

    public static List<Double> readFileForInterpreter(String path) {
        List<Double> result = new ArrayList<>();
        InputStream ins = null;
        Reader r = null;
        BufferedReader br = null;

        try {
            String currLine;
            ins = new FileInputStream(path);
            r = new InputStreamReader(ins, "UTF-8");
            br = new BufferedReader(r);
            while ((currLine = br.readLine()) != null) {
                String[] values = currLine.split(" ");
                for (String val :
                        values)
                    result.add(Double.valueOf(val));
            }
            return result;
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally {
            if (br != null) { try { br.close(); } catch(Throwable t) {  } }
            if (r != null) { try { r.close(); } catch(Throwable t) { } }
            if (ins != null) { try { ins.close(); } catch(Throwable t) {  } }
        }

        return new ArrayList<>();
    }
}
