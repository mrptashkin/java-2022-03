import java.util.*;

public class ATM implements CashOperation {
    private int balanceATM;
    private int balanceAccount;
    private final Map<Integer, CellImpl> cells;

    public ATM() {
        balanceAccount = 1_000_000;
        cells = new HashMap<>();
        balanceATM = 0;
        for (Denominations denominations : Denominations.values()) {
            var currentCell = new CellImpl(denominations.getTitle());
            cells.put(denominations.getTitle(), currentCell);
            balanceATM += currentCell.getBalanceOfCell();
        }
    }

    @Override
    public void acceptCash() {
        System.out.println("Внесение наличных:");
        var scanner = new Scanner(System.in);
        int currentCount;
        int sumCash = 0;
        for (int denomination : cells.keySet()) {
            System.out.printf("Количество купюр номиналом %d\n", denomination);
            var tempCellImpl = cells.get(denomination);
            int temporaryCellImplCount = tempCellImpl.getCount();
            currentCount = scanner.nextInt();
            tempCellImpl.setCount(currentCount + temporaryCellImplCount);
            sumCash += denomination * currentCount;
        }
        System.out.printf("Вы внесли %d", sumCash);
        balanceAccount += sumCash;
        balanceATM += sumCash;
    }

    @Override
    public void giveCash(int sum) {
        if (canIGiveCash(sum)) {
            Map<Integer, CellImpl> notes = calculateNumberOfNotes(sum);
            assert notes != null;
            for (int denomination : notes.keySet()) {
                var tempCellImpl = cells.get(denomination);
                int temporaryCellImplCount = tempCellImpl.getCount();
                var tempNotes = notes.get(denomination);
                int tempNotesCount = tempNotes.getCount();
                tempCellImpl.setCount(temporaryCellImplCount - tempNotesCount);
            }
            balanceAccount -= sum;
            balanceATM -= sum;
        }
    }

    public boolean canIGiveCash(int sum) {
        if (sum > balanceATM) {
            System.out.println("Указанная сумма отсутствует в  ATM\n");
            return false;
        }
        if (balanceAccount < sum) {
            System.out.println("На вашем балансе недостаточно средств\n");
            return false;
        }
        if (sum % 50 != 0) {
            System.out.println("Вы ввели сумму, не кратную 50\n");
            return false;
        }
        return true;
    }

    private Map<Integer, CellImpl> calculateNumberOfNotes(int sum) {
        try {
            Map<Integer, CellImpl> notes = new TreeMap<>(Comparator.reverseOrder());
            for (var denominations : Denominations.values()) {
                notes.put(denominations.getTitle(), new CellImpl(denominations.getTitle(), 0));
            }
            for (int denomination : notes.keySet()) {
                if (sum >= denomination) {
                    int notesCount = sum / denomination;
                    if (notesCount <= cells.get(denomination).getCount()) {  // реализация выдачи суммы более мелкими купюрами при отсутствии или ограниченном количестве крупных
                        notes.replace(denomination, new CellImpl(denomination, notesCount));
                        sum -= notesCount * denomination;
                    } else {
                        notes.replace(denomination, cells.get(denomination));
                        sum -= cells.get(denomination).getCount() * denomination;
                    }
                }
            }
            System.out.println(" Количество выдаваемых купюр по номиналам: ");
            for (int denominations : notes.keySet()) {
                System.out.println(denominations + " = " + notes.get(denominations).getCount());
            }
            return notes;
        } catch (Throwable e) {
            System.err.println("Введен неверный символ");
            return null;
        }
    }

    public void informAboutATM() {
        for (int denomination : cells.keySet()) {
            System.out.println(denomination + " = " + cells.get(denomination).getCount());
        }
        System.out.println("Денег в банкомате:" + balanceATM);
    }

    public void showBalance() {
        System.out.println("\nОстаток на вашем счете: " + balanceAccount);
    }


}