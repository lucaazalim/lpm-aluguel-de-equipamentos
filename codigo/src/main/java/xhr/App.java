package xhr;

import com.opencsv.exceptions.CsvException;
import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class App {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Scanner SCANNER;

    public static void main(String[] args) throws IOException, CsvException {

        DataManager.setup();

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Consultar cliente e seus aluguéis");
        System.out.println("\t3. Cadastrar equipamento");
        System.out.println("\t4. Consultar equipamento e seus aluguéis");
        System.out.println("\t5. Cadastrar aluguel");
        System.out.println("\t6. Consultar aluguel");

        SCANNER = new Scanner(System.in);

            int option = Integer.parseInt(SCANNER.nextLine());

            switch (option) {
                case 1 -> registerClient();
                case 2 -> retrieveClient();
                case 3 -> registerEquipment();
                case 4 -> retrieveEquipment();
                case 5 -> registerRent();
                case 6 -> retrieveRent();
            }

        SCANNER.close();

    }

    public static void registerClient() throws IOException {

        System.out.println("Digite o nome do cliente: ");
        String name = SCANNER.nextLine();

        Client client = new Client(name);
        client.writeToFile();

        System.out.println("Cliente registrado com ID " + client.getId() + ".");

    }

    public static void retrieveClient() throws IOException, CsvException {

        System.out.println("Digite o ID do cliente: ");
        int id = SCANNER.nextInt();

        Client client = Client.readFromFile(id);

        System.out.println(client.toString());

    }

    public static void registerEquipment() {

        System.out.println("Digite o nome do equipamento: ");
        String name = SCANNER.nextLine();

        System.out.println("Digite o preço diário do equipamento: ");
        double dailyPrice = SCANNER.nextDouble();

        Equipment equipment = new Equipment(name, dailyPrice); // TODO = new Equipment(name, dailyPrice);

    }

    public static void retrieveEquipment() {

        System.out.println("Digite o ID do equipamento: ");
        int id = SCANNER.nextInt();

        Equipment equipment; // TODO buscar equipamento

        // TODO printar dados do equipamento e dados de seus aluguéis

    }

    public static void registerRent() {

        System.out.println("Digite a data de início do aluguel: ");
        LocalDate startDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);

        System.out.println("Digite a data de fim do aluguel: ");
        LocalDate endDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);

        System.out.println("Digite o ID do cliente: ");
        int clientId = SCANNER.nextInt();

        Client client = null; // TODO buscar client

        System.out.println("Digite o ID do equipamento: ");
        int equipmentId = SCANNER.nextInt();

        Equipment equipment = null; // TODO buscar equipment

        Rent rent = new Rent(startDate, endDate, client, equipment);

        client.rentEquipment(rent);

    }

    public static void retrieveRent() {

        System.out.println("Digite o ID do aluguel: ");
        int id = SCANNER.nextInt();

        Rent rent = null; // TODO buscar aluguel

        System.out.println(rent);

    }
    
}
