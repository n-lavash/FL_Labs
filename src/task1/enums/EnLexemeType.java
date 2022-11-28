package task1.enums;

public enum EnLexemeType {
    lDo ("do"),
    lLoop ("loop"),
    lWhile("while"),
    lAnd ("and"),
    lOr ("or"),
    lAo1 ("arithmetic operation over"),
    lAo2 ("arithmetic operation lower"),
    lRel ("logic operation"),
    lAs ("assign"),
    lConst ("constant"),
    lVar ("identifier");

    private final String typeName;

    EnLexemeType(String typeName) {
        this.typeName = typeName;
    }


    @Override
    public String toString() {
        return typeName;
    }
}
