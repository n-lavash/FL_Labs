package task3;

public class PolizEntry {
    EnEntryType entryType;
    String lexema;
    EnCmd cmdType;

    public PolizEntry() {

    }

    public PolizEntry(EnEntryType entryType, String lexema) {
        this.entryType = entryType;
        this.lexema = lexema;
        if(entryType.equals(EnEntryType.etCmd)) {
            switch (lexema) {
                case "JMP" -> this.cmdType = EnCmd.JMP;
                case "JZ" -> this.cmdType = EnCmd.JZ;
                case "SET" -> this.cmdType = EnCmd.SET;
                case "ADD" -> this.cmdType = EnCmd.ADD;
                case "SUB" -> this.cmdType = EnCmd.SUB;
                case "DIV" -> this.cmdType = EnCmd.DIV;
                case "MUL" -> this.cmdType = EnCmd.MUL;
                case "AND" -> this.cmdType = EnCmd.AND;
                case "OR" -> this.cmdType = EnCmd.OR;
                case "CMPE" -> this.cmdType = EnCmd.CMPE;
                case "CMPNE" -> this.cmdType = EnCmd.CMPNE;
                case "CMPL" -> this.cmdType = EnCmd.CMPL;
                case "CMPG" -> this.cmdType = EnCmd.CMPG;
            }
        }
        else {
            this.cmdType = EnCmd.NULL;
        }
    }

    public EnCmd getCmdType() {
        return cmdType;
    }

    public void setCmdType(EnCmd cmdType) {
        this.cmdType = cmdType;
    }

    public EnEntryType getEntryType() {
        return entryType;
    }

    public void setEntryType(EnEntryType entryType) {
        this.entryType = entryType;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    @Override
    public java.lang.String toString() {
        if (!this.entryType.equals("command"))
            return this.lexema;
        else
            return this.cmdType.toString();
    }
}
