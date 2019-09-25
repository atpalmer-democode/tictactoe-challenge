public enum Mark {
    X('X'),
    O('O'),
    EMPTY('.');

    private final char display;

    Mark(char display) {
        this.display = display;
    }

    public char display() {
        return this.display;
    }
}
