import java.util.*;

public class Cells implements CellsOperation {
    private final Map<Integer, Integer> cells;
    private final int NUMBER_OF_NOTES = 500;
    private int balanceCells;

    public Cells() {
        cells = new TreeMap<>();
        for (Denominations d : Denominations.values()) {
            cells.put(d.getTitle(), NUMBER_OF_NOTES);
            balanceCells += d.getTitle() * NUMBER_OF_NOTES;
        }
    }

    public Map<Integer, Integer> getCells() {
        return cells;
    }


    public int getBalance() {
        return balanceCells;
    }

    @Override
    public void acceptCash(TreeMap<Integer, Integer> resultNotes) {
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
                notes.put(d.getTitle(), 0);
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