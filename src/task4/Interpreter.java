package task4;

import task3.EnEntryType;
import task3.Poliz;
import task3.PolizEntry;

import java.util.*;

public class Interpreter {
    private StringBuilder results;
    private Stack<String> allElements;
    private Poliz poliz;
    private List<Boolean> stackBoolean;
    private List<Double> stackDouble;
    private List<String> stackVars;
    private List<Double> varStartValues;
    private List<Double> stackCurrentVars;
    private HashMap<String, Double> varCurrValues;
    private List<String> allUsedVars;
    private int cnt;

    public Interpreter(Poliz poliz, List<Double> varValues) {
        this.allElements = new Stack<>();
        this.results = new StringBuilder();
        this.poliz = poliz;
        this.varStartValues = varValues;
        this.allUsedVars = new ArrayList<>();
        this.varCurrValues = new HashMap<>();
        this.stackBoolean = new ArrayList<>();
        this.stackCurrentVars = new ArrayList<>();
        this.stackDouble = new ArrayList<>();
        this.stackVars = new ArrayList<>();
        this.cnt = 0;
    }

    public void interpreterWork() {
        int pos = 0;
        int tmp = 0;
        List<PolizEntry> polizEntryList = this.poliz.getAllPolizEntries();
        while (pos < polizEntryList.size()) {
            PolizEntry currEntry = polizEntryList.get(pos);
            String entryType = currEntry.getEntryType().toString();
            printStack(pos, currEntry.getLexema(), currEntry.getEntryType().toString(), this.allElements, varCurrValues);
            if (entryType.equals("var")) {
                String lexema = currEntry.getLexema();
                if (!allUsedVars.contains(lexema)) {
                    this.allUsedVars.add(lexema);
                    this.varCurrValues.put(lexema, varStartValues.get(cnt));
                    cnt++;
                }
                pushValDouble(this.varCurrValues.get(lexema));
                this.stackVars.add(lexema);
                this.allElements.push(lexema);
                pos++;
            } else if (entryType.equals("command")) {
                String ecmd = currEntry.getCmdType().toString();
                switch (ecmd) {
                    case "JMP" -> pos = polizEntryList.size();
                    case "JZ" -> {
                        tmp = popValDouble().intValue();
                        if (!popValBool())
                            pos = tmp;
                        else
                            pos = polizEntryList.size();
                        this.stackVars = new ArrayList<>();
                        this.stackBoolean = new ArrayList<>();
                        this.stackDouble = new ArrayList<>();
                        this.allElements = new Stack<>();
                    }
                    case "SET" -> {
                        SetVarAndPop(this.stackVars.get(0));
                        pos++;
                        this.allElements.pop();
                        this.allElements.pop();
                        this.stackVars = new ArrayList<>();
                    }
                    case "ADD" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(Double.toString(var1 + var2));
                        pushValDouble(var1 + var2);
                        pos++;
                    }
                    case "SUB" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(Double.toString(-var1 + var2));
                        pushValDouble(-var1 + var2);
                        pos++;
                    }
                    case "MUL" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(Double.toString(var1 * var2));
                        pushValDouble(var1 * var2);
                        pos++;
                    }
                    case "DIV" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(Double.toString(1 / var1 * var2));
                        pushValDouble(1 / var1 * var2);
                        pos++;
                    }
                    case "AND" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Boolean var1 = popValBool();
                        Boolean var2 = popValBool();
                        this.allElements.push(var1 && var2 ? "1" : "0");
                        pushValBool(var1 && var2);
                        pos++;
                        this.stackVars = new ArrayList<>();
                    }
                    case "OR" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Boolean var1 = popValBool();
                        Boolean var2 = popValBool();
                        this.allElements.push(var1 || var2 ? "1" : "0");
                        pushValBool(var1 || var2);
                        pos++;
                    }
                    case "CMPE" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(Objects.equals(var1, var2) ? "1" : "0");
                        pushValBool(Objects.equals(var1, var2));
                        pos++;
                    }
                    case "CMPNE" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(!Objects.equals(var1, var2) ? "1" : "0");
                        pushValBool(!Objects.equals(var1, var2));
                        pos++;
                    }
                    case "CMPL" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(var1 > var2 ? "1" : "0");
                        pushValBool(var1 > var2);
                        pos++;
                    }
                    case "CMPG" -> {
                        this.allElements.pop();
                        this.allElements.pop();
                        Double var1 = popValDouble();
                        Double var2 = popValDouble();
                        this.allElements.push(var1 < var2 ? "1" : "0");
                        pushValBool(var1 < var2);
                        pos++;
                    }
                    default -> pos++;
                }

            } else {
                pushValDouble(Double.valueOf(currEntry.getLexema()));
                this.allElements.push(currEntry.getLexema());
                pos++;
            }

            printStack(pos-1, currEntry, stackVars, stackBoolean, varCurrValues);

        }
    }

    public void SetVarAndPop(String var) {
        this.varCurrValues.put(var, popValDouble());
    }

    public Double popValDouble() {
//        Double popDouble = Double.parseDouble(this.allElements.pop());
        Double popValue = this.stackDouble.get(0);
        this.stackDouble.remove(0);
        return popValue;
    }

    public Boolean popValBool() {
//        Boolean popBoolean = Double.parseDouble(this.allElements.pop()) == 1.0;
        Boolean popValue = this.stackBoolean.get(0);
        this.stackBoolean.remove(0);
        return popValue;
    }

    public void pushValBool(Boolean val) {
        this.stackBoolean.add(0, val);
//        this.allElements.push(val ? "1" : "0");
    }

    public void pushValDouble(Double val) {
//        this.allElements.push(Double.toString(val));
        this.stackDouble.add(0, val);
    }

    public void pushCurDouble(Double val) {
        this.stackCurrentVars.add(0, val);
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

    public void printStack(int position, PolizEntry curEntry, List<String> stackVars, List<Boolean> stackBoolean, HashMap<String, Double> varValues) {
        results.append("\n").append("Position = ").append(position)
                .append("\nlexeme: ").append(curEntry.getLexema())
                .append("\nstack: ");

        if (!stackVars.isEmpty()) {
            for (String var :
                    stackVars) {
                results.append(var).append(" ");
            }
        }

        if (!stackBoolean.isEmpty()) {
            for (Boolean bool :
                    stackBoolean) {
                results.append(bool ? 1 : 0).append(" ");
            }
        }

        results.append("\nvalues: ");
        if (!varValues.isEmpty())
            for (String key :
                    varValues.keySet()) {
                results.append(key).append(" = ").append(varValues.get(key)).append(" ");
            }
        results.append("\n");
    }

    public void printStack(int position, String lexeme, String typeLexeme, Stack<String> stack, HashMap<String, Double> varCurrValues) {
        System.out.print("\nPosition: " + position
                + "\n\tLexeme: " + lexeme
                + "\n\tLexeme type: " + typeLexeme
                + "\n\tStack: ");
        for (String var :
                stack) {
            System.out.print(var + " ");
        }

        System.out.print("\n\tValues: ");
        if (!varCurrValues.isEmpty()) {
            for (String key :
                    varCurrValues.keySet()) {
                System.out.print(key + " = " + varCurrValues.get(key) + " ");
            }
        }
        System.out.println();
    }
}
