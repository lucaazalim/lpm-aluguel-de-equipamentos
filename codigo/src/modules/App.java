package modules;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    private static DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    public static void main(String[] args) {

        /*
         * Client
         * Equipment
         * Rent
         */

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Cadastrar equipamento");
        System.out.println("\t3. Cadastrar aluguel");

        try(Scanner scanner = new Scanner(System.in)) {

            int option = scanner.nextInt();

            switch(option) {

                case 1: {
                    registerClient(scanner);
                    break;
                }

                case 2: {
                    registerEquipment(scanner);
                    break;
                }

                case 3: {
                    registerRent(scanner);
                    break;
                }


            }

        }

    }

    public static void registerClient(Scanner scanner) {

        System.out.println("Digite o nome do cliente: ");
        String name = scanner.nextLine();

        Client client = new Client(name);

    }

    public static void registerEquipment(Scanner scanner) {

        System.out.println("Digite o nome do equipamento: ");
        String name = scanner.nextLine();

        System.out.println("Digite o preço diário do equipamento: ");
        double dailyPrice = scanner.nextDouble();

        Equipment equipment = new Equipment(name, dailyPrice);

    }

    public static void registerRent(Scanner scanner) {

        System.out.println("Digite a data de início do aluguel: ");
        LocalDate startDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);

        System.out.println("Digite a data de fim do aluguel: ");
        LocalDate endDate = LocalDate.parse(scanner.nextLine(), DATE_FORMATTER);

        System.out.println("Digite o ID do cliente: ");
        int clientId = scanner.nextInt();

        Client client; // TODO buscar client

        System.out.println("Digite o ID do equipamento: ");
        int equipmentId = scanner.nextInt();

        Equipment equipment; // TODO buscar equipment

        Rent rent = new Rent(startDate, endDate, client, equipment);

    }
    
}
