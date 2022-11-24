package task1.lexemes;

import task1.enums.EnLexemeType;

import java.util.Arrays;
import java.util.List;

public class Lexeme {
    private String lexeme;
    private EnLexemeType lexemeType;
    private int index;
    private int position;

    public Lexeme() {}

    public Lexeme(String lexeme, int position) {
        List<String> allVariants = Arrays.asList(
                "do", "loop", "until", "and", "or",
                "+", "-", "*", "\\",
                "<", ">", "<>", "==", "="
        );

        this.lexeme = lexeme;

        List<String> allArithmeticOver = Arrays.asList("*", "\\");
        List<String> allArithmeticLower = Arrays.asList("+", "-");
        List<String> allLogic = Arrays.asList(">", "<", "<>", "==");
        switch (lexeme) {
            case "do":
                this.lexemeType = EnLexemeType.lDo;
                break;
            case "loop":
                this.lexemeType = EnLexemeType.lLoop;
                break;
            case "until":
                this.lexemeType = EnLexemeType.lUntil;
                break;
            case "and":
                this.lexemeType = EnLexemeType.lAnd;
                break;
            case "or":
                this.lexemeType = EnLexemeType.lOr;
                break;
            case "=":
                this.lexemeType = EnLexemeType.lAs;
                break;
            default:
                if (lexeme.matches("[-+]?\\d+"))
                    this.lexemeType = EnLexemeType.lConst;
                else if (allArithmeticOver.contains(lexeme))
                    this.lexemeType = EnLexemeType.lAo1;
                else if (allArithmeticLower.contains(lexeme))
                    this.lexemeType = EnLexemeType.lAo2;
                else if (allLogic.contains(lexeme))
                    this.lexemeType = EnLexemeType.lRel;
                else
                    this.lexemeType = EnLexemeType.lVar;
                break;
        }

        this.index = allVariants.indexOf(lexeme);
        this.position = position;
    }

    public String getLexeme() {
        return lexeme;
    }

    public void setLexeme(String lexeme) {
        this.lexeme = lexeme;
    }

    public EnLexemeType getLexemeType() {
        return lexemeType;
    }

    public void setLexemeType(EnLexemeType lexemeType) {
        this.lexemeType = lexemeType;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
            return "lexeme = '" + lexeme + '\'' +
                    ", lexemeType = " + lexemeType +
                    ", position = " + position;
    }
}
