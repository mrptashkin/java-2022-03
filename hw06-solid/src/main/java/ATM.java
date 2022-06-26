import java.util.*;

public class ATM implements CashOperation {
    private final Account account;
    private final Cell cell;
    private int balanceATM;

    public ATM(Account account, Cell cell) {
        this.account = account;
        this.cell = cell;
        balanceATM = cell.getBalance();

    }


    public void acceptAddCash() {
        System.out.println("Внесение наличных:");
        Scanner scanner = new Scanner(System.in);
        TreeMap<Integer, Integer> countsOfDenominations = new TreeMap<>();
        int currentCount;
        int sumCash = 0;
        for (int count : cell.getCells().keySet()) {
            System.out.printf("Количество купюр номиналом %d\n", count);
            currentCount = scanner.nextInt();
            countsOfDenominations.put(count, currentCount);
            sumCash += count * currentCount;
        }
        cell.acceptAdd1Cash(countsOfDenominations);
        System.out.printf("Вы внесли %d БАНКОМАТ", sumCash);
        account.setBalance(account.getBalance() + sumCash);
        balanceATM = cell.getBalance();
    }

    public void showBalance(Account account) {
        System.out.println("\nОстаток на вашем счете: " + account.getBalance());
    }

    public boolean canIGiveCash(int sum) {
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

    @Override
    public void giveCash(int sum) {
        if (canIGiveCash(sum)) {
            cell.giveCash(sum);
            account.setBalance(account.getBalance() - sum);
        }
        balanceATM = cell.getBalance();
    }


    public void informAboutATM() {
        System.out.println("Количество купюр каждого номинала: " + cell.getCells());
        System.out.println("Денег в банкомате:" + balanceATM);
    }
}

