import java.util.HashMap;
import java.util.Scanner;

public class ATM {
    private final Account account;
    private final HashMap<Integer, Integer> cells;
    private final int NUMBER_OF_NOTES = 500;
    private final int[] DENOMINATIONS = {50, 100, 500, 1000, 5000};
    private int balanceATM;

    public ATM(Account account) {
        this.account = account;
        cells = new HashMap<>();
        for (int denomination : DENOMINATIONS) {
            cells.put(denomination, NUMBER_OF_NOTES);
            balanceATM += denomination * NUMBER_OF_NOTES;
        }
    }


    void acceptCash() {
        System.out.println("Внесение наличных:");
        Scanner scanner = new Scanner(System.in);
        int countOfDenominations;
        int sumCash = 0;
        for (int denomination : DENOMINATIONS) {
            System.out.printf("Количество купюр номиналом %d\n", denomination);
            countOfDenominations = scanner.nextInt();
            cells.put(denomination, NUMBER_OF_NOTES + countOfDenominations);
            sumCash += countOfDenominations * denomination;
        }
        System.out.printf("Вы внесли %d", sumCash);
        account.setBalance(account.getBalance() + sumCash);
    }

    void showBalance(Account account) {
        System.out.println("\nОстаток на вашем счете: " + account.getBalance());
    }

    HashMap<Integer, Integer> calculateNumberOfNotes(int sum) {
        try {
            HashMap<Integer, Integer> notes = new HashMap<>();
            if (canIGiveCash(sum)) {
                for (int i = DENOMINATIONS.length - 1; i >= 0; i--) {
                    if (sum >= DENOMINATIONS[i]) {
                        int notesCount = sum / DENOMINATIONS[i];
                        if (notesCount <= cells.get(DENOMINATIONS[i])) {  // реализация выдачи суммы более мелкими купюрами при отсутствии или ограниченном количестве крупных
                            notes.put(DENOMINATIONS[i], notesCount);
                            sum -= notesCount * DENOMINATIONS[i];
                        } else {
                            notes.put(DENOMINATIONS[i], cells.get(DENOMINATIONS[i]));
                            sum -= cells.get(DENOMINATIONS[i]) * DENOMINATIONS[i];
                        }
                    }
                }
            }
            System.out.println(" Количество выдаваемых купюр по номиналам: " + notes);
            return notes;

        }
        catch (Throwable e)
        {
            System.err.println("Введен неверный символ");
            return null;
        }
    }

    boolean canIGiveCash(int sum) {
        if (sum > balanceATM) {
            System.out.println("Указанная сумма отсутствует в  ATM\n");
            return false;
        }
        if (account.getBalance() < sum) {
            System.out.println("На вашем балансе недостаточно средств\n");
            return false;
        }
        if (sum % 50 != 0) {
            System.out.println("Вы ввели сумму, не кратную 50\n");
            return false;
        }
        return true;
    }

    void giveCash(int sum) {
        HashMap<Integer, Integer> notes = calculateNumberOfNotes(sum);
        for (int key : notes.keySet()) {
            cells.put(key, cells.get(key) - notes.get(key));
        }
        balanceATM -= balanceATM - sum;
        account.setBalance(account.getBalance() - sum);
        System.out.println("Вам выдано " + sum);
    }

    void informAboutATM() {
        System.out.println("Количество купюр каждого номинала: " + cells);
        System.out.println("Денег в банкомате:" + balanceATM);
    }
}

