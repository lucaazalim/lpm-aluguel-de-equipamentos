package xhr;

import com.opencsv.exceptions.CsvException;
import xhr.data.DataManager;
import xhr.exceptions.EquipmentAlreadyRentedInPeriodException;
import xhr.exceptions.PriorityEquipmentRentPeriodExceededException;
import xhr.modules.Client;
import xhr.modules.Equipment;
import xhr.modules.PriorityEquipment;
import xhr.modules.Rent;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class App {

    public static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    public static final DateTimeFormatter YEAR_MONTH_FORMATTER = DateTimeFormatter.ofPattern("MM/yyyy");
    private static Scanner SCANNER;

    public static Path DATA_PATH = Path.of("data");

    public static void main(String[] args) throws IOException, CsvException {

        if (Files.notExists(DATA_PATH)) {
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
        System.out.println("\t7. Gerar relatório mensal de aluguéis");

        int option;

        try {
            option = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            option = -1;
        }

        boolean exiting = false;

        switch (option) {
            case 0 -> saveAndExit();
            case 1 -> registerClient();
            case 2 -> retrieveClient();
            case 3 -> registerEquipment();
            case 4 -> retrieveEquipment();
            case 5 -> registerRent();
            case 6 -> retrieveRent();
            case 7 -> generateMonthlyReport();
            default -> System.out.println("A opção informada é inválida.");
        }

        if (!exiting) {

            System.out.println();
            System.out.println("Pressione ENTER para voltar ao menu...");

            try {
                System.in.read();
            } catch (IOException ignored) {
            }

            menu();

        }

    }

    /**
     * Saves all data and exits the program.
     *
     * @throws IOException if an I/O error occurs
     * @see DataManager#save()
     */
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

        if (name.isBlank()) {
            System.out.println("Informe um nome válido para o cliente.");
            return;
        }

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

        if (input.isBlank()) {
            System.out.println("Informe um ID ou nome válido de cliente.");
            return;
        }

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

        for (Client client : clients) {

            System.out.println(" - " + client);

            for (Rent rent : client.getRents()) {
                System.out.println("\t - " + rent);
            }

        }

    }

    /**
     * Registers a new equipment.
     */
    public static void registerEquipment() {

        System.out.println("Digite o nome do equipamento: ");
        String name = SCANNER.nextLine();

        if (name.isBlank()) {
            System.out.println("Informe um nome válido para o equipamento.");
            return;
        }

        System.out.println("Digite o preço diário do equipamento: ");
        double dailyPrice;

        try {
            dailyPrice = Double.parseDouble(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("O preço diário informado é inválido.");
            return;
        }

        System.out.println("O equipamento é prioritário? (S/N)");
        boolean priority = SCANNER.nextLine().equalsIgnoreCase("S");

        Equipment equipment;
        int nextId = Equipment.DATA.getNextId();

        if (priority) {
            equipment = new PriorityEquipment(nextId, name, dailyPrice);
        } else {
            equipment = new Equipment(nextId, name, dailyPrice);
        }

        Equipment.DATA.add(equipment);

        System.out.println("Equipamento registrado com o ID " + equipment.getId() + ".");

    }

    /**
     * Retrieves an equipment and prints its information.
     */
    public static void retrieveEquipment() {

        System.out.println("Digite o ID ou nome do equipamento: ");
        String input = SCANNER.nextLine();

        if (input.isBlank()) {
            System.out.println("Informe um ID ou nome válido de equipamento.");
            return;
        }

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

        for (Equipment equipment : equipments) {
            System.out.println(" - " + equipment);

            for (Rent rent : equipment.getRents()) {
                System.out.println("\t - " + rent);
            }
        }

    }

    /**
     * Registers a new rent.
     */
    public static void registerRent() {

        System.out.println("Digite a data de início do aluguel: ");
        LocalDate startDate;

        try {
            startDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            System.out.println("A data informada para início do aluguel é inválida.");
            return;
        }

        System.out.println("Digite a data de fim do aluguel: ");
        LocalDate endDate;

        try {
            endDate = LocalDate.parse(SCANNER.nextLine(), DATE_FORMATTER);
        } catch (DateTimeParseException exception) {
            System.out.println("A data informada para fim do aluguel é inválida.");
            return;
        }

        System.out.println("Digite o ID do cliente: ");
        int clientId;

        try {
            clientId = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("O ID de cliente informado é inválido.");
            return;
        }

        Client client = Client.DATA.getById(clientId);

        if (client == null) {
            System.out.println("Cliente não encontrado.");
            return;
        }

        System.out.println("Digite o ID do equipamento: ");
        int equipmentId;

        try {
            equipmentId = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("O ID de equipamento informado é inválido.");
            return;
        }

        Equipment equipment = Equipment.DATA.getById(equipmentId);
        Rent rent = new Rent(Rent.DATA.getNextId(), startDate, endDate, client, equipment);

        try {
            equipment.addRent(rent);
        } catch (PriorityEquipmentRentPeriodExceededException ignored) {
            System.out.println("Um equipamento prioritário não pode ser alugado por mais de 10 dias.");
            return;
        } catch (EquipmentAlreadyRentedInPeriodException ignored) {
            System.out.println("O equipamento já está alugado durante o período informado.");
            return;
        }

        System.out.println("Aluguel registrado com o ID " + rent.getId() + ".");
        System.out.println("O valor total do aluguel é:  " + Utils.formatCurrency(rent.getPrice()));

        Rent.DATA.add(rent);
        client.addRent(rent);

    }

    /**
     * Retrieves a rent and prints its information.
     */
    public static void retrieveRent() {

        System.out.println("Digite o ID do aluguel: ");
        int rentId;

        try {
            rentId = Integer.parseInt(SCANNER.nextLine());
        } catch (NumberFormatException exception) {
            System.out.println("O ID de aluguel informado é inválido.");
            return;
        }

        Rent rent = Rent.DATA.getById(rentId);

        if (rent == null) {
            System.out.println("Aluguel não encontrado.");
            return;
        }

        System.out.println(rent);

    }

    /**
     * Generates a monthly report.
     */
    private static void generateMonthlyReport() {

        System.out.println("Informe o mês e o ano (MM/yyyy): ");
        String input = SCANNER.nextLine();

        YearMonth yearMonth = YearMonth.parse(input, YEAR_MONTH_FORMATTER);

        List<Rent> matchingRents = new ArrayList<>();
        double totalIncome = 0;

        for (Rent rent : Rent.DATA.getAll()) {

            YearMonth rentYearMonth = YearMonth.from(rent.getStartDate());

            if (yearMonth.equals(rentYearMonth)) {

                totalIncome += rent.getPrice();
                matchingRents.add(rent);

            }

        }

        String formattedYearMonth = yearMonth.format(YEAR_MONTH_FORMATTER);

        if (matchingRents.isEmpty()) {
            System.out.println("Nenhum aluguel encontrado para o mês " + formattedYearMonth + ".");
            return;
        }

        System.out.println("Foram encontrados " + matchingRents.size() + " aluguel(is) para o mês " + formattedYearMonth + ": ");

        for (Rent rent : matchingRents) {
            System.out.println(" - " + rent);
        }

        System.out.println();
        System.out.println("Faturamento total do mês " + formattedYearMonth + ": " + Utils.formatCurrency(totalIncome));

    }

}
