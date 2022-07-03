public class Cell {

    private final int count;
    private int balanceOfCell;


    public Cell(int denomination, int count) {
        this.count = count;
        checkBalanceOfCell(denomination);
    }

    public Cell(int denomination) {
        count = 100;
        checkBalanceOfCell(denomination);
    }


    public int getCount() {
        return count;
    }

    public int getBalanceOfCell() {
        return balanceOfCell;
    }

    private void checkBalanceOfCell(int denomination) {
        balanceOfCell = denomination * count;
    }

}
