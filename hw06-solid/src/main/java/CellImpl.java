public class CellImpl implements Cell {

    private final int denomination;

    private int count;

    private int balanceOfCell;

    public CellImpl(int denomination, int count) {
        this.denomination = denomination;
        this.count = count;
        checkBalanceOfCell(denomination);
    }

    public CellImpl(int denomination) {
        this.denomination = denomination;
        count = 100;
        checkBalanceOfCell(denomination);
    }

    @Override
    public int getCount() {
        return count;
    }

    @Override
    public int getBalanceOfCell() {
        return balanceOfCell;
    }

    @Override
    public void checkBalanceOfCell(int denomination) {
        balanceOfCell = denomination * count;
    }

    @Override
    public void setCount(int count) {
        this.count = count;
    }
    @Override
    public int getDenomination() {
        return denomination;
    }
}
