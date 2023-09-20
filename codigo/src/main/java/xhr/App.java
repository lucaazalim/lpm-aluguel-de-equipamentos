package xhr;

import com.opencsv.exceptions.CsvException;
import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Scanner SCANNER;

    public static void main(String[] args) throws IOException, CsvException {

        DataManager.setup();

        SCANNER = new Scanner(System.in);

        menu();

        SCANNER.close();

    }

    /**
     * Displays the menu and handles user input.
     *
     * @throws IOException  if an I/O error occurs
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
        } catch (IOException ignored) {
        }

        menu();

    }

    /**
     * Registers a new client.
     *
     * @throws IOException  if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public static void registerClient() throws IOException, CsvException {

        System.out.println("Digite o nome do cliente: ");
        String name = SCANNER.nextLine();

        Client latestClient = DataManager.readLatestObject(Client.CLIENT_DATA_PATH, Client::new);
        int clientId = latestClient == null ? 1 : latestClient.getId() + 1;

        Client client = new Client(clientId, name);
        client.save();

        System.out.println("Cliente registrado com ID " + client.getId() + ".");

    }

    /**
     * Retrieves a client and prints its information.
     *
     * @throws IOException  if an I/O error occurs
     * @throws CsvException if an error occurs while reading or writing a CSV file
     */
    public static void retrieveClient() throws IOException, CsvException {

        System.out.println("Digite o ID ou Nome do cliente: ");
        String input = SCANNER.nextLine();

        List<Client> clients;

        try {
            clients = List.of(Client.searchById(Integer.parseInt(input)));
        } catch (NumberFormatException exception) {
            clients = Client.searchByName(input);
        }

        if (clients.isEmpty()) {
            System.out.println("Nenhum cliente encontrado.");
            return;
        }

        for(Client client : clients) {
            System.out.println(client);
        }

    }

    /**
     * Registers a new equipment.
     */
    public static void registerEquipment() {

        System.out.println("Digite o nome do equipamento: ");
        String name = SCANNER.nextLine();

        System.out.println("Digite o preço diário do equipamento: ");
        double dailyPrice = SCANNER.nextDouble();

        Equipment latestEquipment = DataManager.readLatestObject(Equipment.EQUIPMENT_DATA_PATH, Equipment::new);
        int equipmentId = latestEquipment == null ? 1 : latestEquipment.getId() + 1;

        Equipment equipment = new Equipment(equipmentId, name, dailyPrice);
        equipment.save();
        
    }

    /**
     * Retrieves an equipment and prints its information.
     */
    public static void retrieveEquipment() {

        System.out.println("Digite o ID do equipamento: ");
        int id = SCANNER.nextInt();

        Equipment equipment = null; // TODO buscar equipamento

        if (equipment == null) {
            System.out.println("Equipamento não encontrado.");
            return;
        }

        // TODO printar dados do equipamento e dados de seus aluguéis

    }

    /**
     * Registers a new rent.
     *
     * @throws IOException  if an I/O error occurs
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

        Equipment equipment = Equipment.searchById(equipmentId); // TODO buscar equipment

        if(equipment.isRented(startDate, endDate)) {
            System.out.println("O equipamento já está alugado durante o período informado.");
            return;
        }

        Rent latestRent = DataManager.readLatestObject(
                Rent.RENT_DATA_PATH,
                fields -> new Rent(Integer.parseInt(fields[0]), LocalDate.parse(fields[1], DATE_FORMATTER), LocalDate.parse(fields[2], DATE_FORMATTER), client, equipment)
        );

        int rentId = latestRent == null ? 1 : latestRent.getId() + 1;
        Rent rent = new Rent(rentId, startDate, endDate, client, equipment);

        client.addRent(rent);

    }

    /**
     * Retrieves a rent and prints its information.
     */
    public static void retrieveRent() {

        System.out.println("Digite o ID do aluguel: ");
        int rentId = SCANNER.nextInt();

        Rent rent = Rent.searchById(rentId); // TODO buscar aluguel

        if (rent == null) {
            System.out.println("Aluguel não encontrado.");
            return;
        }

        System.out.println(rent);

    }

}
