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

        CSVDataManager.setup();

        SCANNER = new Scanner(System.in);

        menu();

        SCANNER.close();

    }

    /**
     * Displays the menu and handles user input.
     *
     * @throws IOException if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public static void menu() throws IOException, CsvException {

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Sair");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Consultar cliente e seus aluguéis");
        System.out.println("\t3. Cadastrar equipamento");
        System.out.println("\t4. Consultar equipamento e seus aluguéis");
        System.out.println("\t5. Cadastrar aluguel");
        System.out.println("\t6. Consultar aluguel");

        int option = Integer.parseInt(SCANNER.nextLine());

        switch (option) {
            case 0 -> System.exit(0);
            case 1 -> registerClient();
            case 2 -> retrieveClient();
            case 3 -> registerEquipment();
            case 4 -> retrieveEquipment();
            case 5 -> registerRent();
            case 6 -> retrieveRent();
        }

        System.out.println("Pressione ENTER para voltar ao menu...");

        try {
            System.in.read();
            SCANNER.nextLine();
        } catch (IOException ignored) { }

        menu();

    }

    /**
     * Registers a new client.
     *
     * @throws IOException if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public static void registerClient() throws IOException, CsvException {

        System.out.println("Digite o nome do cliente: ");
        String name = SCANNER.nextLine();

        Client client = new Client(name);
        client.save();

        System.out.println("Cliente registrado com ID " + client.getId() + ".");

    }

    /**
     * Retrieves a client and prints its information.
     *
     * @throws IOException if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public static void retrieveClient() throws IOException, CsvException {

        System.out.println("Digite o ID do cliente: ");
        int id = SCANNER.nextInt();

        Client client = Client.searchById(id);

        System.out.println(client.toString());

    }

    /**
     * Registers a new equipment.
     */
    public static void registerEquipment() {

        System.out.println("Digite o nome do equipamento: ");
        String name = SCANNER.nextLine();

        System.out.println("Digite o preço diário do equipamento: ");
        double dailyPrice = SCANNER.nextDouble();

        Equipment equipment = new Equipment(name, dailyPrice);

        // TODO register equipment

    }

    /**
     * Retrieves an equipment and prints its information.
     */
    public static void retrieveEquipment() {

        System.out.println("Digite o ID do equipamento: ");
        int id = SCANNER.nextInt();

        Equipment equipment; // TODO buscar equipamento

        // TODO printar dados do equipamento e dados de seus aluguéis

    }

    /**
     * Registers a new rent.
     *
     * @throws IOException if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public static void registerRent() throws IOException, CsvException {

        System.out.println("Digite a data de início do aluguel: ");
        LocalDate startDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);

        System.out.println("Digite a data de fim do aluguel: ");
        LocalDate endDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);

        System.out.println("Digite o ID do cliente: ");
        int clientId = SCANNER.nextInt();

        Client client = Client.searchById(clientId);

        System.out.println("Digite o ID do equipamento: ");
        int equipmentId = SCANNER.nextInt();

        Equipment equipment = null; // TODO buscar equipment

        Rent rent = new Rent(startDate, endDate, client, equipment);

        client.rentEquipment(rent);

    }

    /**
     * Retrieves a rent and prints its information.
     */
    public static void retrieveRent() {

        System.out.println("Digite o ID do aluguel: ");
        int id = SCANNER.nextInt();

        Rent rent = null; // TODO buscar aluguel

        System.out.println(rent);

    }

}
