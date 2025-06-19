/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package UI;

/**
 *
 * @author Lucas
 */

import Models.*;
import Data.FileManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ConsoleUI {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-M-d HH:mm");
    private static List<Event> events;
    private static final List<User> users = new ArrayList<>();
    private static User currentUser;

    public static void start() {
        events = FileManager.loadEvents();

        System.out.println("=== Bem-vindo ao Sistema de Eventos da Cidade ===");
        createUser();

        int option;
        do {
            System.out.println("\nMenu:");
            System.out.println("1 - Cadastrar Evento");
            System.out.println("2 - Listar Eventos");
            System.out.println("3 - Confirmar Presença");
            System.out.println("4 - Cancelar Presença");
            System.out.println("5 - Ver Meus Eventos Confirmados");
            System.out.println("6 - Ver Eventos Ocorrendo Agora");
            System.out.println("7 - Ver Eventos Passados");
            System.out.println("0 - Sair");
            System.out.print("Escolha uma opção: ");
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1 -> createEvent();
                case 2 -> listEvents();
                case 3 -> confirmPresence();
                case 4 -> cancelPresence();
                case 5 -> listUserEvents();
                case 6 -> listOngoingEvents();
                case 7 -> listPastEvents();
                case 0 -> {
                    System.out.println("Salvando dados...");
                    FileManager.saveEvents(events);
                    System.out.println("Encerrando...");
                }
                default -> System.out.println("Opção inválida!");
            }
        } while (option != 0);
    }

    private static void createUser() {
        System.out.print("Digite seu nome: ");
        String name = scanner.nextLine();
        System.out.print("Digite seu email: ");
        String email = scanner.nextLine();
        System.out.print("Digite sua cidade: ");
        String city = scanner.nextLine();

        currentUser = new User(name, email, city);
        users.add(currentUser);

        System.out.println("Usuário criado com sucesso: " + currentUser.getName());
    }

    private static void createEvent() {
        System.out.print("Nome do evento: ");
        String name = scanner.nextLine();
        System.out.print("Endereço: ");
        String address = scanner.nextLine();

        System.out.println("Categorias:");
        for (EventCategory cat : EventCategory.values()) {
            System.out.println("- " + cat);
        }
        System.out.print("Categoria: ");
        String catStr = scanner.nextLine();
        EventCategory category;
        try {
            category = EventCategory.valueOf(catStr.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.out.println("Categoria inválida!");
            return;
        }

        System.out.print("Data e hora (yyyy-M-d HH:mm): ");
        String dtStr = scanner.nextLine();
        LocalDateTime dateTime;
        try {
            dateTime = LocalDateTime.parse(dtStr, formatter);
        } catch (Exception e) {
            System.out.println("Data/hora inválida!");
            return;
        }

        System.out.print("Descrição: ");
        String description = scanner.nextLine();

        Event event = new Event(name, address, category, dateTime, description);
        events.add(event);
        FileManager.saveEvents(events);

        System.out.println("Evento cadastrado com sucesso!");
    }

    private static void listEvents() {
        List<Event> upcomingEvents = events.stream()
            .filter(e -> !e.isPast())
            .sorted(Comparator.comparing(Event::getDateTime))
            .toList();

        if (upcomingEvents.isEmpty()) {
            System.out.println("Nenhum evento futuro cadastrado.");
            return;
        }

        System.out.println("Próximos eventos cadastrados:");
        upcomingEvents.forEach(e -> System.out.printf("%s | %s | %s | %s\n", e.getName(), e.getCategory(), e.getDateTime().format(formatter), e.getAddress()));
    }
    
    private static void confirmPresence() {
        List<Event> upcomingEvents = events.stream()
            .filter(e -> !e.isPast())
            .sorted(Comparator.comparing(Event::getDateTime))
            .toList();

        if (upcomingEvents.isEmpty()) {
            System.out.println("Não há eventos futuros para confirmar presença.");
            return;
        }

        System.out.println("Eventos disponíveis para confirmação:");
        upcomingEvents.forEach(e ->
            System.out.printf("%s | %s | %s\n", e.getName(), e.getCategory(), e.getDateTime().format(formatter))
        );

        System.out.print("Digite o nome do evento para confirmar presença: ");
        String name = scanner.nextLine();

        upcomingEvents.stream()
            .filter(e -> e.getName().equalsIgnoreCase(name))
            .findFirst()
            .ifPresentOrElse(e -> {
                e.addParticipant(currentUser);
                FileManager.saveEvents(events);
                System.out.println("Presença confirmada em: " + e.getName());
            }, () -> System.out.println("Evento não encontrado."));
    }

    private static void cancelPresence() {
        List<Event> userEvents = getUserEvents().stream()
            .filter(e -> !e.isPast())
            .toList();

        if (userEvents.isEmpty()) {
            System.out.println("Você não confirmou presença em nenhum evento futuro.");
            return;
        }

        userEvents.forEach(e -> System.out.printf("- %s | %s\n", e.getName(), e.getDateTime().format(formatter)));
        System.out.print("Digite o nome do evento para cancelar presença: ");
        String name = scanner.nextLine();

        userEvents.stream()
            .filter(e -> e.getName().equalsIgnoreCase(name))
            .findFirst()
            .ifPresentOrElse(e -> {
                e.removeParticipant(currentUser);
                FileManager.saveEvents(events);
                System.out.println("Presença cancelada de: " + e.getName());
            }, () -> System.out.println("Evento não encontrado."));
    }
    
    private static void listUserEvents() {
        List<Event> userEvents = getUserEvents();
        if (userEvents.isEmpty()) {
            System.out.println("Você não confirmou presença em nenhum evento.");
            return;
        }

        System.out.println("Seus eventos confirmados:");
        userEvents.forEach(e -> System.out.printf("- %s | %s\n", e.getName(), e.getDateTime().format(formatter)));
    }

    private static List<Event> getUserEvents() {
        List<Event> result = new ArrayList<>();
        for (Event e : events) {
            if (e.getParticipants().stream().anyMatch(u -> u.getEmail().equalsIgnoreCase(currentUser.getEmail()))) {
                result.add(e);
            }
        }
        return result;
    }

    private static void listOngoingEvents() {
        System.out.println("Eventos em andamento:");
        events.stream()
            .filter(Event::isOngoing)
            .forEach(e -> System.out.printf("- %s (%s)\n", e.getName(), e.getDateTime().format(formatter)));
    }

    private static void listPastEvents() {
        System.out.println("Eventos passados:");
        events.stream()
            .filter(Event::isPast)
            .forEach(e -> System.out.printf("- %s (%s)\n", e.getName(), e.getDateTime().format(formatter)));
    }
    
}
