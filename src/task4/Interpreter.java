package task4;

import task3.Poliz;
import task3.PolizEntry;

import java.util.*;

public class Interpreter {
    private StringBuilder results;
    private Stack<String> allElements;
    private Stack<Double> vars;
    private Poliz poliz;
    private List<Double> inputVarValues;
    private HashMap<String, Double> varCurrValues;
    private List<String> allUsedVars;
    private int cnt;

    public Interpreter(Poliz poliz, List<Double> inputVarValues) {
        this.allElements = new Stack<>();
        this.vars = new Stack<>();
        this.results = new StringBuilder();
        this.poliz = poliz;
        this.inputVarValues = inputVarValues;
        this.allUsedVars = new ArrayList<>();
        this.varCurrValues = new HashMap<>();
        this.cnt = 0;
    }

    public void interpreterWork() {
        int pos = 0;
        int tmp = 0;
        List<PolizEntry> polizEntryList = this.poliz.getAllPolizEntries();
        while (pos < polizEntryList.size()) {
            PolizEntry currEntry = polizEntryList.get(pos);
            String entryType = currEntry.getEntryType().toString();
            printStack(pos, currEntry.getLexema(), this.allElements, varCurrValues);
            if (entryType.equals("var")) {
                String lexema = currEntry.getLexema();
                if (!allUsedVars.contains(lexema)) {
                    this.allUsedVars.add(lexema);
                    this.varCurrValues.put(lexema, inputVarValues.get(cnt));
                    cnt++;
                }
                this.vars.push(this.varCurrValues.get(lexema));
                this.allElements.push(lexema);
                pos++;
            } else if (entryType.equals("command")) {
                String ecmd = currEntry.getCmdType().toString();
                switch (ecmd) {
                    case "JMP" -> pos = polizEntryList.size();
                    case "JZ" -> {
                        tmp = Integer.parseInt(this.allElements.pop());
                        boolean check = Integer.parseInt(this.allElements.pop()) == 1;
                        if (!check)
                            pos = tmp;
                        else
                            pos = polizEntryList.size();
                        this.allElements = new Stack<>();
                    }
                    case "SET" -> {
                        this.allElements.pop();
                        this.varCurrValues.put(this.allElements.pop(), this.vars.pop());
                        pos++;
                    }
                    case "ADD" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(Double.toString(var1 + var2));
                        this.vars.push(var1 + var2);
                        pos++;
                    }
                    case "SUB" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(Double.toString(-var1 + var2));
                        this.vars.push(-var1 + var2);
                        pos++;
                    }
                    case "MUL" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(Double.toString(var1 * var2));
                        this.vars.push(var1 * var2);
                        pos++;
                    }
                    case "DIV" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(Double.toString(1 / var1 * var2));
                        this.vars.push(1 / var1 * var2);
                        pos++;
                    }
                    case "AND" -> {
                        Boolean var1 = Objects.equals(this.allElements.pop(), "1");
                        Boolean var2 = Objects.equals(this.allElements.pop(), "1");
                        ;
                        this.allElements.push(var1 && var2 ? "1" : "0");
                        pos++;
                    }
                    case "OR" -> {
                        Boolean var1 = Objects.equals(this.allElements.pop(), "1");
                        Boolean var2 = Objects.equals(this.allElements.pop(), "1");
                        this.allElements.push(var1 || var2 ? "1" : "0");
                        pos++;
                    }
                    case "CMPE" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(Objects.equals(var1, var2) ? "1" : "0");
                        pos++;
                    }
                    case "CMPNE" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(!Objects.equals(var1, var2) ? "1" : "0");
                        pos++;
                    }
                    case "CMPL" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(var1 > var2 ? "1" : "0");
                        pos++;
                    }
                    case "CMPG" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = this.vars.pop();
                        Double var2 = this.vars.pop();
                        this.allElements.push(var1 < var2 ? "1" : "0");
                        pos++;
                    }
                    default -> pos++;
                }

            } else {
                this.vars.push(Double.parseDouble(currEntry.getLexema()));
                this.allElements.push(currEntry.getLexema());
                pos++;
            }

        }
    }

    public Poliz getPoliz() {
        return poliz;
    }

    public void setPoliz(Poliz poliz) {
        this.poliz = poliz;
    }

    public HashMap<String, Double> getVarCurrValues() {
        return varCurrValues;
    }

    public void setVarCurrValues(HashMap<String, Double> varCurrValues) {
        this.varCurrValues = varCurrValues;
    }

    public StringBuilder getResults() {
        return results;
    }

    public void setResults(StringBuilder results) {
        this.results = results;
    }

    public void printStack(int position, String lexeme, Stack<String> stack, HashMap<String, Double> varCurrValues) {
        results.append("Position: ").append(position)
                .append("\n\tLexeme: ").append(lexeme)
                .append("\n\tStack: ");

        for (String var :
                stack) {
            results.append(var).append(" ");
        }

        results.append("\n\tValues: ");
        if (!varCurrValues.isEmpty()) {
            for (String key :
                    varCurrValues.keySet()) {
                results.append(key).append(" = ").append(varCurrValues.get(key)).append(" ");
            }
        }
        results.append("\n\n");
    }
}
