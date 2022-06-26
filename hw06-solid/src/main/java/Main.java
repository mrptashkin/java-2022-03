import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startATM();
        menuATM();
    }


    static void startATM() {
        System.out.println("\nWelcome to ATM!\n");
        System.out.println("Выберите действие:\n1 -  Показать баланс счета\n2 - Внести наличные\n3 - Снять наличные\n4 - Информация о наличии купюр в банкомате\n----------\n0 -  Выход");
    }

    static void menuATM() {
        Account account = new Account(1_000_000);
        Scanner scanner = new Scanner(System.in);
        Cell cell = new Cell();
        ATM atm = new ATM(account,cell);
        label:
        while (true) {
            try {

                switch (scanner.nextInt()) {
                    case 1 -> atm.showBalance(account);

                    case 2 -> atm.acceptAddCash();

                    case 3 -> {
                        System.out.println("Введите сумму к выдаче:\n");
                        atm.giveCash(scanner.nextInt());
                    }
                    case 4 -> atm.informAboutATM();
                    case 0 -> {
                        System.out.println("Сеанс завершен");
                        break label;
                    }
                }
                startATM();
            } catch (Throwable e) {
                System.err.println("Введен неверный символ");
                break;
            }
        }
    }
}
