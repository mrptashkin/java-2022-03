import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        startATM();
        menuATM();
    }


    static void startATM() {
        System.out.println("\nWelcome to ATM!\n");
        System.out.println("Выберите действие:\n1 - Показать баланс счета\n2 - Внести наличные\n3 - Снять наличные\n4 - Информация о наличии купюр в банкомате\n----------\n0 -  Выход");
    }

    static void menuATM() {
        Scanner scanner = new Scanner(System.in);
        Cells cell = new Cells();
        ATM atm = new ATM(cell);
        label:
        while (true) {
            try {

                switch (scanner.nextInt()) {
                    case 1 -> atm.showBalance();

                    case 2 -> atm.acceptCash();

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
