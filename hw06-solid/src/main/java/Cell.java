import java.util.*;

public class Cell implements CashOperation {
    private final Map<Integer, Integer> cells;
    private final int NUMBER_OF_NOTES = 500;
    private int balanceCells;

    public Cell() {
        cells = new TreeMap<>();
        for (Denominations d : Denominations.values()) {
            cells.put(d.title, NUMBER_OF_NOTES);
            balanceCells += d.title * NUMBER_OF_NOTES;
        }
    }

    public Map<Integer, Integer> getCells() {
        return cells;
    }


    private enum Denominations {
        FIFTY(50), ONE_HUNDRED(100), FIVE_HUNDRED(500), ONE_THOUSAND(1000), FIVE_THOUSAND(5000);

        private final int title;

        Denominations(int title) {
            this.title = title;
        }

    }


    public int getBalance() {
        return balanceCells;
    }

    public void acceptAdd1Cash(TreeMap<Integer, Integer> resultNotes) {
        int countOfDenominations;
        for (Integer key : cells.keySet()) {
            countOfDenominations = resultNotes.get(key);
            cells.replace(key, NUMBER_OF_NOTES + countOfDenominations);
            balanceCells += countOfDenominations * key;
        }

    }

    private Map<Integer, Integer> calculateNumberOfNotes(int sum) {
        try {
            Map<Integer, Integer> notes = new TreeMap<>(Comparator.reverseOrder());
            for (Denominations d : Denominations.values()) {
                notes.put(d.title, 0);
            }
            for (Integer key : notes.keySet()) {
                if (sum >= key) {
                    int notesCount = sum / key;
                    if (notesCount <= cells.get(key)) {  // реализация выдачи суммы более мелкими купюрами при отсутствии или ограниченном количестве крупных
                        notes.replace(key, notesCount);
                        sum -= notesCount * key;
                    } else {
                        notes.replace(key, cells.get(key));
                        sum -= cells.get(key) * key;
                    }
                }
            }

            System.out.println(" Количество выдаваемых купюр по номиналам: " + notes);
            return notes;

        } catch (Throwable e) {
            System.err.println("Введен неверный символ");
            return null;
        }
    }

    @Override
    public void giveCash(int sum) {
        Map<Integer, Integer> notes = calculateNumberOfNotes(sum);
        assert notes != null;
        for (int key : notes.keySet()) {
            cells.replace(key, cells.get(key) - notes.get(key));
            balanceCells -= key * notes.get(key);
        }
    }

}
