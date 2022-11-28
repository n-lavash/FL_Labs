package task4;

import task3.Poliz;
import task3.PolizEntry;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Interpreter {
    private Poliz poliz;
    private List<Boolean> stackBoolean;
    private List<Double> stackDouble;
    private List<String> stackVars;
    private List<Double> varStartValues;
    private HashMap<String, Double> varCurrValues;
    private List<String> allUsedVars;
    private int cnt;
    private List<String> toWrite;

    public Interpreter(Poliz poliz, List<Double> varValues) {
        this.toWrite = new ArrayList<>();
        this.poliz = poliz;
        this.varStartValues = varValues;
        this.allUsedVars = new ArrayList<>();
        this.varCurrValues = new HashMap<>();
        this.stackBoolean = new ArrayList<>();
        this.stackDouble = new ArrayList<>();
        this.stackVars = new ArrayList<>();
        this.cnt = 0;
    }

    public List<String> getToWrite() {
        return toWrite;
    }

    public void interpreterWork() {
        int pos = 0;
        int tmp = 0;
        List<PolizEntry> polizEntryList = this.poliz.getAllPolizEntries();
        while (pos < polizEntryList.size()) {
            PolizEntry currEntry = polizEntryList.get(pos);
            String entryType = currEntry.getEntryType().toString();
            if(entryType.equals("var")) {
                String lexema = currEntry.getLexema();
                if(!allUsedVars.contains(lexema)) {
                    this.allUsedVars.add(lexema);
                    this.varCurrValues.put(lexema, varStartValues.get(cnt));
                    cnt++;
                }
                pushValDouble(this.varCurrValues.get(lexema));
                this.stackVars.add(lexema);
                pos++;
            }
            else if(entryType.equals("command")) {
                String ecmd = currEntry.getCmdType().toString();
                switch (ecmd) {
                    case "JMP" -> pos = polizEntryList.size();
                    case "JZ" -> {
                        tmp = popValDouble().intValue();
                        if (popValBool())
                            pos++;
                        else
                            pos = tmp;
                        this.stackVars = new ArrayList<>();
                        this.stackBoolean = new ArrayList<>();
                        this.stackDouble = new ArrayList<>();
                    }
                    case "SET" -> {
                        SetVarAndPop(this.stackVars.get(0));
                        pos++;
                        this.stackVars = new ArrayList<>();
                    }
                    case "ADD" -> {
                        pushValDouble(popValDouble() + popValDouble());
                        pos++;
                    }
                    case "SUB" -> {
                        pushValDouble(-popValDouble() + popValDouble());
                        pos++;
                    }
                    case "MUL" -> {
                        System.out.println(ecmd);
                        pushValDouble(popValDouble() * popValDouble());
                        pos++;
                    }
                    case "DIV" -> {
                        pushValDouble(1 / popValDouble() * popValDouble());
                        pos++;
                    }
                    case "AND" -> {
                        pushValBool(popValBool() && popValBool());
                        pos++;
                        this.stackVars = new ArrayList<>();
                    }
                    case "OR" -> {
                        pushValBool(popValBool() || popValBool());
                        pos++;
                    }
                    case "CMPE" -> {
                        pushValBool(Objects.equals(popValDouble(), popValDouble()));
                        pos++;
                    }
                    case "CMPNE" -> {
                        pushValBool(!Objects.equals(popValDouble(), popValDouble()));
                        pos++;
                    }
                    case "CMPL" -> {
                        pushValBool(popValDouble() > popValDouble());
                        pos++;
                    }
                    case "CMPG" -> {
                        pushValBool(popValDouble() < popValDouble());
                        pos++;
                    }
                    default -> pos++;
                }

            }
            else {
                pushValDouble(Double.valueOf(currEntry.getLexema()));
                pos++;
            }
        }
    }

    public void SetVarAndPop(String var) {
        this.varCurrValues.put(var, popValDouble());
    }

    public Double popValDouble() {
        Double popValue = this.stackDouble.get(0);
        this.stackDouble.remove(0);
        return popValue;
    }

    public Boolean popValBool() {
        Boolean popValue = this.stackBoolean.get(0);
        this.stackBoolean.remove(0);
        return popValue;
    }

    public void pushValBool(Boolean val) {
        this.stackBoolean.add(0, val);
    }

    public void pushValDouble(Double val) {
        this.stackDouble.add(0, val);
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
}
