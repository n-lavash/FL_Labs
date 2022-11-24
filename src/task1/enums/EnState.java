package task1.enums;

public enum EnState {

    S("S"), /* */
    Ai("Ai"), /* идент */
    Ac("Ac"), /* константа */
    As("As"), /* < */
    Bs("Bs"), /* = */
    Cs("Cs"), /* <> */
    Ds("Ds"), /* == */
    Gs("Gs"), /* + - * \ */
    Ks("Ks"), /* > */
    E("E"),
    F("F");

    private final String stateName;

    EnState(String stateName) {
        this.stateName = stateName;
    }

    @Override
    public String toString() {
        return stateName;
    }
}
