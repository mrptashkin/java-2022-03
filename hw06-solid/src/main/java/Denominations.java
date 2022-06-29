public enum Denominations {
    FIFTY(50), ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000), FIVE_THOUSAND(5000);

    private final int title;

    public int getTitle() {
        return title;
    }

    Denominations(int title) {
        this.title = title;
    }

}