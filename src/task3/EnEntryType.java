package task3;

public enum EnEntryType {
    etCmd("command"),
    etVar("var"),
    etConst("const"),
    etCmdPtr("command pointer");

    private final String text;

    EnEntryType(final String text) {
        this.text = text;
    }

    @java.lang.Override
    public String toString() {
        return text;
    }
}
