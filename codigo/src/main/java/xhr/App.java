package xhr;

import com.opencsv.exceptions.CsvException;
import xhr.data.DataManager;
import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.Rent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class App {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static Scanner SCANNER;

    public static Path DATA_PATH = Path.of("data");

    public static void main(String[] args) throws IOException, CsvException {

        Files.createDirectory(DATA_PATH);

        Client.DATA.load();
        Equipment.DATA.load();
        Rent.DATA.load();

        SCANNER = new Scanner(System.in);

        menu();

        SCANNER.close();

        Client.DATA.save();
        Equipment.DATA.save();
        Rent.DATA.save();

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
        boolean exiting = false;

        switch (option) {
            case 0 -> exiting = true;
            case 1 -> registerClient();
            case 2 -> retrieveClient();
            case 3 -> registerEquipment();
            case 4 -> retrieveEquipment();
            case 5 -> registerRent();
            case 6 -> retrieveRent();
        }

        if(!exiting) {

            System.out.println("Pressione ENTER para voltar ao menu...");

            try {
                System.in.read();
                SCANNER.nextLine();
            } catch (IOException ignored) {
            }

            menu();

        }

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

        Client client = new Client(Client.DATA.getNextId(), name);
        Client.DATA.add(client);

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

        Set<Client> clients;

        try {
            clients = Set.of(Client.DATA.getById(Integer.parseInt(input)));
        } catch (NumberFormatException exception) {
            clients = Client.DATA.getByNameFragment(input);
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

        Equipment equipment = new Equipment(Equipment.DATA.getNextId(), name, dailyPrice);
        Equipment.DATA.add(equipment);

    }

    /**
     * Retrieves an equipment and prints its information.
     */
    public static void retrieveEquipment() {

        System.out.println("Digite o ID do equipamento: ");
        String id = SCANNER.nextLine();

        Equipment equipment = Equipment.DATA.getById(Integer.parseInt(id));

        if (equipment == null) {
            System.out.println("Equipamento não encontrado.");
            return;
        }

        System.out.println(equipment);

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

        Client client = Client.DATA.getById(clientId);

        System.out.println("Digite o ID do equipamento: ");
        int equipmentId = SCANNER.nextInt();

        Equipment equipment = Equipment.DATA.getById(equipmentId);

        if(equipment.isRented(startDate, endDate)) {
            System.out.println("O equipamento já está alugado durante o período informado.");
            return;
        }

        Rent rent = new Rent(Rent.DATA.getNextId(), startDate, endDate, client, equipment);
        Rent.DATA.add(rent);

        client.addRent(rent);

    }

    /**
     * Retrieves a rent and prints its information.
     */
    public static void retrieveRent() {

        System.out.println("Digite o ID do aluguel: ");
        int rentId = SCANNER.nextInt();

        Rent rent = Rent.DATA.getById(rentId);

        if (rent == null) {
            System.out.println("Aluguel não encontrado.");
            return;
        }

        System.out.println(rent);

    }

}
