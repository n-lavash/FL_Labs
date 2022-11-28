package task4;

import task1.enums.EnState;
import task1.files.ReadFile;
import task1.files.WriteFiles;
import task1.lexemes.Lexeme;
import task2.Expression;
import task3.TreeHandler;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static List<Expression> alreadyWrittenExpressions = new ArrayList<>();

    public static void main(String[] args) {
        List<Lexeme> results = new ArrayList<>();
        File file = new File("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/Task1/src/structure.txt");
        List<String> structureFromFile = ReadFile.readFile(file);
        String structureJoined = String.join(" ", structureFromFile);
        EnState currentState = EnState.S;
        boolean add = false;
        EnState nextState;
        int index = 0;
        char currentSymbol = 'a';
        String currentLexeme = "";

        while ((currentState != EnState.E) && (currentState != EnState.F) && (index <= structureJoined.length() - 1)) {
            nextState = currentState;
            add = true;
            currentSymbol = structureJoined.charAt(index);

            switch (currentState) {
                case S:
                    if (currentSymbol == ' ')
                        break;
                    else if (Character.isLetter(currentSymbol))
                        currentState = EnState.Ai;
                    else if (Character.isDigit(currentSymbol))
                        currentState = EnState.Ac;
                    else if (currentSymbol == '>')
                        currentState = EnState.Ks;
                    else if (currentSymbol == '<')
                        currentState = EnState.As;
                    else if ((currentSymbol == '+') || (currentSymbol == '-') || (currentSymbol == '*') || (currentSymbol == '\\'))
                        currentState = EnState.Gs;
                    else if (currentSymbol == '=')
                        currentState = EnState.Bs;
                    else if (currentSymbol == '\n')
                        currentState = EnState.F;
                    else
                        currentState = EnState.E;
                    add = false;
                    break;
                case Ai:
                    if (currentSymbol == ' ')
                        currentState = EnState.S;
                    else if ((Character.isLetter(currentSymbol)) || (Character.isDigit(currentSymbol)))
                        add = false;
                    else if (currentSymbol == '>')
                        currentState = EnState.Ks;
                    else if (currentSymbol == '<')
                        currentState = EnState.As;
                    else if ((currentSymbol == '+') || (currentSymbol == '-') || (currentSymbol == '*') || (currentSymbol == '\\'))
                        currentState = EnState.Gs;
                    else if (currentSymbol == '=')
                        currentState = EnState.Bs;
                    else if (currentSymbol == '\n')
                        currentState = EnState.F;
                    else {
                        currentState = EnState.E;
                        add = false;
                    }
                    break;
                case Ac:
                    if (currentSymbol == ' ')
                        currentState = EnState.S;
                    else if (Character.isDigit(currentSymbol))
                        add = false;
                    else if (currentSymbol == '>')
                        currentState = EnState.Ks;
                    else if (currentSymbol == '<')
                        currentState = EnState.As;
                    else if ((currentSymbol == '+') || (currentSymbol == '-') || (currentSymbol == '*') || (currentSymbol == '\\'))
                        currentState = EnState.Gs;
                    else if (currentSymbol == '=')
                        currentState = EnState.Bs;
                    else if (currentSymbol == '\n')
                        currentState = EnState.F;
                    else {
                        currentState = EnState.E;
                        add = false;
                    }
                    break;
                case Ks:
                case Cs:
                case Ds:
                case Gs:
                    if (currentSymbol == ' ')
                        currentState = EnState.S;
                    else if (Character.isLetter(currentSymbol))
                        currentState = EnState.Ai;
                    else if (Character.isDigit(currentSymbol))
                        currentState = EnState.Ac;
                    else if (currentSymbol == '\n')
                        currentState = EnState.F;
                    else {
                        currentState = EnState.E;
                        add = false;
                    }
                    break;
                case As:
                    if (currentSymbol == ' ')
                        currentState = EnState.S;
                    else if (Character.isLetter(currentSymbol))
                        currentState = EnState.Ai;
                    else if (Character.isDigit(currentSymbol))
                        currentState = EnState.Ac;
                    else if (currentSymbol == '>') {
                        currentState = EnState.Cs;
                        add = false;
                    } else if (currentSymbol == '\n')
                        currentState = EnState.F;
                    else {
                        currentState = EnState.E;
                        add = false;
                    }
                    break;
                case Bs:
                    if (currentSymbol == ' ')
                        currentState = EnState.S;
                    else if (Character.isLetter(currentSymbol))
                        currentState = EnState.Ai;
                    else if (Character.isDigit(currentSymbol))
                        currentState = EnState.Ac;
                    else if (currentSymbol == '=') {
                        currentState = EnState.Ds;
                        add = false;
                    } else if (currentSymbol == '\n')
                        currentState = EnState.F;
                    else {
                        currentState = EnState.E;
                        add = false;
                    }
                    break;
            }

            if (currentSymbol != ' ')
                currentLexeme += currentSymbol;

            try {
                if (!currentLexeme.equals("do") && !currentLexeme.equals("loop") && !currentLexeme.equals("until")) {
                    char nextSymbol = structureJoined.charAt(index+1);
                    if (nextSymbol == ' ')
                        add = true;
                }
            } catch (IndexOutOfBoundsException e) {
                add = true;
            }


            if (add) {
                if (!currentLexeme.equals("") && !currentLexeme.equals(" "))
                    results.add(new Lexeme(currentLexeme, index));
                currentLexeme = "";
            }

            index++;
        }

        if (currentState == EnState.E)
            WriteFiles.writeFile("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/Task1/src/results.txt", "There is a mistake in the structure");
        else {
            WriteFiles.writeFile("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/Task1/src/results.txt", results);
            TreeHandler treeHandler = new TreeHandler(results);
            boolean result = treeHandler.workTheTree();
            if (result) {
                List<Expression> expressionsList = treeHandler.getExpressionList().stream().
                        sorted((o1, o2) -> compare(o1.getLevel(), o2.getLevel())).
                        collect(Collectors.toList());
                List<String> toWrite = new ArrayList<>();
                int i = 0;
                System.out.println(expressionsList);
                while (i < expressionsList.size()) {
                    Expression expr = expressionsList.get(i);
                    if (expr != null) {
                        print(expr);
                        toWrite.add(expr.toString());
                        List<Expression> exprChildren = expr.getChildList();
                        int j = 0;
                        if (exprChildren != null) {
                            Expression exprChild = exprChildren.get(j);
                            while (exprChild != null && j < exprChildren.size()) {
                                print(exprChild);
                                toWrite.add(exprChild.toString());
                                j++;
                                if (j < exprChildren.size()) {
                                    exprChild = exprChildren.get(j);
                                }
                            }
                        }
                    }
                    i++;
                }
                WriteFiles.writeFile1("C:/Users/levas/Desktop/Мои усы лапы и хвост/Учеба/Автоматы/Task1/src/tree.txt", toWrite);
            }
        }
    }

    public static boolean print(Expression expr) {
        if (!alreadyWrittenExpressions.contains(expr)) {
            alreadyWrittenExpressions.add(expr);
        }
        return false;
    }

    public static int compare(int a1, int a2) {
        if (a1 > a2)
            return 1;
        else
            return -1;
    }
}
