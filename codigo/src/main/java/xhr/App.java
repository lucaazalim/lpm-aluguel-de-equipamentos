package xhr;

import com.opencsv.exceptions.CsvException;
import xhr.data.DataManager;
import xhr.exceptions.EquipmentAlreadyRentedInPeriodException;
import xhr.exceptions.PriorityEquipmentRentPeriodExceededException;
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

        if(Files.notExists(DATA_PATH)) {
            Files.createDirectory(DATA_PATH);
        }

        Client.DATA.load();
        Equipment.DATA.load();
        Rent.DATA.load();

        SCANNER = new Scanner(System.in);

        menu();

    }

    /**
     * Displays the menu and handles user input.
     */
    public static void menu() throws IOException {

        System.out.println("Escolha uma das opções: ");
        System.out.println("\t0. Salvar e sair");
        System.out.println("\t1. Cadastrar cliente");
        System.out.println("\t2. Consultar cliente e seus aluguéis");
        System.out.println("\t3. Cadastrar equipamento");
        System.out.println("\t4. Consultar equipamento e seus aluguéis");
        System.out.println("\t5. Cadastrar aluguel");
        System.out.println("\t6. Consultar aluguel");

        int option = Integer.parseInt(SCANNER.nextLine());
        boolean exiting = false;

        switch (option) {
            case 0 -> saveAndExit();
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

    public static void saveAndExit() throws IOException {

        SCANNER.close();

        Client.DATA.save();
        Equipment.DATA.save();
        Rent.DATA.save();

        System.exit(0);

    }

    /**
     * Registers a new client.
     */
    public static void registerClient() {

        System.out.println("Digite o nome do cliente: ");
        String name = SCANNER.nextLine();

        Client client = new Client(Client.DATA.getNextId(), name);
        Client.DATA.add(client);

        System.out.println("Cliente registrado com o ID " + client.getId() + ".");

    }

    /**
     * Retrieves a client and prints its information.
     */
    public static void retrieveClient() {

        System.out.println("Digite o ID ou nome do cliente: ");
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

        System.out.println("Foram encontrados " + clients.size() + " cliente(s): ");

        for(Client client : clients) {
            System.out.println(" - " + client);
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

        System.out.println("O equipamento é prioritário? (true/false)");
        boolean priority = SCANNER.nextBoolean();

        Equipment equipment = new Equipment(Equipment.DATA.getNextId(), name, dailyPrice, priority);
        Equipment.DATA.add(equipment);

        System.out.println("Equipamento registrado com o ID " + equipment.getId() + ".");

    }

    /**
     * Retrieves an equipment and prints its information.
     */
    public static void retrieveEquipment() {

        System.out.println("Digite o ID do equipamento: ");
        String input = SCANNER.nextLine();

        Set<Equipment> equipments;

        try {
            equipments = Set.of(Equipment.DATA.getById(Integer.parseInt(input)));
        } catch (NumberFormatException exception) {
            equipments = Equipment.DATA.getByNameFragment(input);
        }

        if (equipments.isEmpty()) {
            System.out.println("Nenhum equipamento encontrado.");
            return;
        }

        System.out.println("Foram encontrados " + equipments.size() + " equipamento(s): ");

        for(Equipment equipment : equipments) {
            System.out.println(" - " + equipment);
        }

    }

    /**
     * Registers a new rent.
     */
    public static void registerRent() {

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
        Rent rent = new Rent(Rent.DATA.getNextId(), startDate, endDate, client, equipment);

        try {
            equipment.addRent(rent);
        }catch(PriorityEquipmentRentPeriodExceededException ignored) {
            System.out.println("Um equipamento prioritário não pode ser alugado por mais de 10 dias.");
            return;
        }catch(EquipmentAlreadyRentedInPeriodException ignored) {
            System.out.println("O equipamento já está alugado durante o período informado.");
            return;
        }

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
