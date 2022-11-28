package task3;

public enum EnCmd {
    JMP("JMP"),
    JZ("JZ"),
    SET("SET"),
    ADD("ADD"),
    SUB("SUB"),
    MUL("MUL"),
    DIV("DIV"),
    AND("AND"),
    OR("OR"),
    CMPE("CMPE"),
    CMPNE("CMPNE"),
    CMPL("CMPL"),
    CMPG("CMPG"),
    NULL("not a command");

    private final String text;

    EnCmd(final String text) {
        this.text = text;
    }

    @Override
    public String toString() {
        return text;
    }

}
