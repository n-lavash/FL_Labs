package task2;

import task1.enums.EnLexemeType;
import task1.files.WriteFiles;
import task1.lexemes.Lexeme;

import java.util.ArrayList;
import java.util.List;

public class TreeHandler {
    private List<Lexeme> lexemeList;
    private List<Expression> expressionList;
    private boolean errorTriggered;

    public TreeHandler(List<Lexeme> lexemeList) {
        this.lexemeList = lexemeList;
        this.expressionList = new ArrayList<>();
        this.errorTriggered = false;
    }

    public List<Expression> getExpressionList() {
        return expressionList;
    }

    public void setExpressionList(List<Expression> expressionList) {
        this.expressionList = expressionList;
    }

    public boolean isErrorTriggered() {
        return errorTriggered;
    }

    public void setErrorTriggered(boolean errorTriggered) {
        this.errorTriggered = errorTriggered;
    }

    public List<Lexeme> getLexemeList() {
        return lexemeList;
    }

    public void setLexemeList(List<Lexeme> lexemeList) {
        this.lexemeList = lexemeList;
    }

    public boolean workTheTree() {
        return doStatement();
    }

    public boolean doStatement() {
        int index = 0;
        int level = 0;
        Lexeme currLex;
        Lexeme prevLex = new Lexeme();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();

        try {
            currLex = this.lexemeList.get(index);
        } catch ( IndexOutOfBoundsException e ) {
            Error("Ожидается do", 0);
            return false;
        }
        if(!currLex.getLexemeType().equals(EnLexemeType.lDo)) {
            Error("Ожидается do", 0);
            return false;
        }
        exprList.add(currLex);
        index++;

        try {
            prevLex = currLex;
            currLex = this.lexemeList.get(index);
        } catch (IndexOutOfBoundsException e) {
            if (!this.errorTriggered) {
                Error("Ожидается оператор", prevLex.getPosition() + prevLex.getLexeme().length() + 1);
            }
            return false;
        }

        List<Integer> statements = statements(index, level + 1);
        if (statements.get(0) == 0 && !this.errorTriggered) {
            Error("Ожидается оператор", currLex.getPosition());
            return false;
        }

        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index = statements.get(1);

        try {
            currLex = this.lexemeList.get(index);
        } catch (IndexOutOfBoundsException e) {
            if (!this.errorTriggered) {
                Error("Ожидается loop", prevLex.getPosition() + prevLex.getLexeme().length() + 1);
            }
            return false;
        }
        if(!currLex.getLexemeType().equals(EnLexemeType.lLoop) && !this.errorTriggered) {
            Error("Ожидается loop", currLex.getPosition());
            return false;
        }
        exprList.add(currLex);
        index++;

        try {
            currLex = this.lexemeList.get(index);
        } catch (IndexOutOfBoundsException e) {
            if (!this.errorTriggered) {
                Error("Ожидается until", prevLex.getPosition() + prevLex.getLexeme().length() + 1);
            }
            return false;
        }
        if(!currLex.getLexemeType().equals(EnLexemeType.lUntil) && !this.errorTriggered) {
            Error("Ожидается until", currLex.getPosition());
            return false;
        }

        exprList.add(currLex);
        index++;

        try {
            prevLex = currLex;
            currLex = this.lexemeList.get(index);
        } catch (IndexOutOfBoundsException e) {
            if (!this.errorTriggered) {
                Error("Ожидается логическое выражение", prevLex.getPosition() + prevLex.getLexeme().length() + 1);
            }
            return false;
        }
        List<Integer> logicalExpr = logicalExpr(index, level + 1);
        if (logicalExpr.get(0) == 0 && !this.errorTriggered) {
            Error("Ожидается логическое выражение", currLex.getPosition());
            return false;
        }
        index = logicalExpr.get(1);
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        return true;
    }

    public List<Integer> logicalExpr(int index, int level) {

        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;
        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        List<Integer> logExpr1 = logExpr1(index1, level + 1);
        if (logExpr1.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = logExpr1.get(1);
        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexemeType().equals(EnLexemeType.lOr)) {
            exprList.add(currLex);
            logExpr1 = logExpr1(index1 + 1, level + 1);
            if (logExpr1.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = logExpr1.get(1);
            try {
                currLex = this.lexemeList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        result.add(1);
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(index1);
        return result;
    }

    public List<Integer> logExpr1(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;
        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> relExpr = relExpr(index1, level + 1);
        if (relExpr.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = relExpr.get(1);

        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexemeType().equals(EnLexemeType.lAnd)) {
            exprList.add(currLex);
            if(currLex.getLexemeType().equals(EnLexemeType.lOr)) {
                exprList.add(currLex);
                index1++;
                try {
                    currLex = this.lexemeList.get(index1);
                } catch ( IndexOutOfBoundsException e ) {
                    result.add(0);
                    result.add(index1);
                    return result;
                }
            }
            relExpr = relExpr(index1 + 1, level + 1);
            if (relExpr.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = relExpr.get(1);
            try {
                currLex = this.lexemeList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }


    public List<Integer> relExpr(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;

        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> operand = operand(index1, level + 1);
        if (operand.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = operand.get(1);

        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        while(currLex.getLexemeType().equals(EnLexemeType.lRel)) {
            exprList.add(currLex);
            operand = operand(index1 + 1, level + 1);
            if (operand.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = operand.get(1);
            try {
                currLex = this.lexemeList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }

        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> operand(int index, int level) {
        int index1 = index;
        Lexeme currLex;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();

        try {
            currLex = this.lexemeList.get(index1);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор или константа", lexemeList.get(index1 - 1).getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }

        if(!currLex.getLexemeType().equals(EnLexemeType.lVar) && !currLex.getLexemeType().equals(EnLexemeType.lConst)) {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор или константа", currLex.getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }

        exprList.add(currLex);
        this.expressionList.add(new Expression(lexemeList, level, null));
        result.add(1);
        result.add(index1 + 1);
        return result;
    }

    public List<Integer> statements(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;

        try {
            currLex = this.lexemeList.get(index1);
        } catch ( IndexOutOfBoundsException e ) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> statement = statement(index1, level + 1);
        if(statement.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }

        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = statement.get(1);
        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> statement(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;

        try {
            currLex = this.lexemeList.get(index1);
        } catch ( IndexOutOfBoundsException e ) {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор", lexemeList.get(index1 - 1).getPosition());
            }
            result.add(0);
            result.add(index1);
            return result;
        }

        if(currLex.getLexemeType().equals(EnLexemeType.lVar)) {
            exprList.add(currLex);
            index1++;

            try {
                currLex = this.lexemeList.get(index1);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается знак присвоения", lexemeList.get(index1 - 1).getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }

            if(!currLex.getLexemeType().equals(EnLexemeType.lAs)) {
                if(!this.errorTriggered) {
                    Error("Ожидается знак присвоения", currLex.getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }

            exprList.add(currLex);
            index1++;
            try {
                currLex = this.lexemeList.get(index1);
            } catch ( IndexOutOfBoundsException e ) {
                if(!this.errorTriggered) {
                    Error("Ожидается арифметическое выражение", lexemeList.get(index1 - 1).getPosition());
                }
                result.add(0);
                result.add(index1);
                return result;
            }
            List<Integer> arithExpr = arithExpr(index1, level + 1);
            if (arithExpr.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            this.expressionList.add(new Expression(exprList, level, allExpressions));
            index1 = arithExpr.get(1);
            result.add(1);
        }
        else {
            if(!this.errorTriggered) {
                Error("Ожидается идентификатор", lexemeList.get(index1 - 1).getPosition());
            }
            result.add(0);
        }
        result.add(index1);
        return result;

    }

    public List<Integer> arithExpr(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;

        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> arithExpr1 = arithExpr1(index1, level + 1);
        if (arithExpr1.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }
        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = arithExpr1.get(1);
        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexemeType().equals(EnLexemeType.lAo1)) {
            exprList.add(currLex);
            arithExpr1 = arithExpr1(index1 + 1, level + 1);
            if (arithExpr1.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = arithExpr1.get(1);
            try {
                currLex = this.lexemeList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }
        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public List<Integer> arithExpr1(int index, int level) {
        int index1 = index;
        List<Integer> result = new ArrayList<>();
        List<Lexeme> exprList = new ArrayList<>();
        List<Expression> allExpressions = new ArrayList<>();
        Lexeme currLex;

        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }

        List<Integer> operand = operand(index1, level + 1);
        if (operand.get(0) == 0) {
            result.add(0);
            result.add(index1);
            return result;
        }

        allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
        index1 = operand.get(1);

        try {
            currLex = this.lexemeList.get(index1);
        } catch (IndexOutOfBoundsException e) {
            result.add(0);
            result.add(index1);
            return result;
        }
        while(currLex.getLexemeType().equals(EnLexemeType.lAo2)) {
            exprList.add(currLex);
            operand = operand(index1 + 1, level + 1);
            if (operand.get(0) == 0) {
                result.add(0);
                result.add(index1);
                return result;
            }
            allExpressions.add(this.expressionList.get(this.expressionList.size() - 1));
            index1 = operand.get(1);
            try {
                currLex = this.lexemeList.get(index1);
            } catch (IndexOutOfBoundsException e) {
                result.add(0);
                result.add(index1);
                return result;
            }
        }

        this.expressionList.add(new Expression(exprList, level, allExpressions));
        result.add(1);
        result.add(index1);
        return result;
    }

    public void Error(String message, int position) {
        WriteFiles.writeFile("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/Task1/src/tree.txt", message + " в позиции " + position);
        this.errorTriggered = true;
    }

}

