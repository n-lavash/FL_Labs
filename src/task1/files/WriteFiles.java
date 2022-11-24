package task1.files;

import task1.enums.EnLexemeType;
import task1.lexemes.Lexeme;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WriteFiles {
    public static void writeFile(String path, List<Lexeme> toWrite) {

        try(FileWriter writer = new FileWriter(path, false))
        {
            List<Lexeme> identifiers = new ArrayList<>();
            List<Lexeme> constants = new ArrayList<>();
            writer.write("ALL LEXEMES LIST:" + "\n");
            for (Lexeme lex: toWrite) {
                writer.write(lex + "\n");
                if (lex.getLexemeType().equals(EnLexemeType.lVar)) {
                    if (lex.getLexeme().matches("[a-zA-Z]+")) {
                        identifiers.add(lex);
                    }
                }
                else if (lex.getLexemeType().equals(EnLexemeType.lConst))
                    constants.add(lex);
            }
            writer.write("\n");
            writer.write("ALL IDENTIFIERS LIST:" + "\n");
            for (Lexeme lex: identifiers) {
                writer.write(lex + "\n");
            }
            writer.write("\n");
            writer.write("ALL CONSTANTS LIST:" + "\n");
            for (Lexeme lex: constants) {
                writer.write(lex + "\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }

    }

    public static void writeFile(String path, String message) {
        try(FileWriter writer = new FileWriter(path, false))
        {
            writer.write(message);
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }

    public static void writeFile1(String path, List<String> toWrite) {
        try(FileWriter writer = new FileWriter(path, false))
        {
            for (String write :
                    toWrite) {
                writer.write(write + "\n");
            }
        }
        catch(IOException ex){

            System.out.println(ex.getMessage());
        }
    }
}
