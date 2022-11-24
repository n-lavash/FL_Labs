package task2;

import task1.lexemes.Lexeme;

import java.util.List;

public class Expression {
    private List<Lexeme> lexemesInExpression;
    private List<Expression> childList;
    int level;

    public Expression(List<Lexeme> lexemesInExpression, int level , List<Expression> childList) {
        this.lexemesInExpression = lexemesInExpression;
        this.childList = childList;
        this.level = level;
    }

    public List<Expression> getChildList() {
        return childList;
    }

    public void setChildList(List<Expression> childList) {
        this.childList = childList;
    }

    public List<Lexeme> getLexemesInExpression() {
        return lexemesInExpression;
    }

    public void setLexemesInExpression(List<Lexeme> lexemasInExpression) {
        this.lexemesInExpression = lexemasInExpression;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @java.lang.Override
    public java.lang.String toString() {
        String connectLexemes = "";
        for(Lexeme lex: this.lexemesInExpression) {
            connectLexemes += lex.getLexeme() + " ";
        }
        if (connectLexemes.equals(""))
            connectLexemes = "specialexpr";
        if (childList != null) {
            return "Expression{" +
                    connectLexemes +
                    ", level=" + level +
                    ", child=" + childList +
                    '}';
        }
        else {
            return "Expression{" +
                    connectLexemes +
                    ", level=" + level +
                    ", child=" + "null" +
                    '}';
        }
    }
}
