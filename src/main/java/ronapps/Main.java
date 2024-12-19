package ronapps;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Imports used for JSON
import com.fasterxml.jackson.databind.ObjectMapper;

/* TODO: add a while loop so that the task tracker does not terminate after
         accepting user input the first time around */

/* TODO: find out how to save the tasks created by the user to the JSON file */

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] avaliableCommands = new String[] {"add",
                "update",
                "delete",
                "mark-in-progress",
                "mark-done",
                "list",
                "list-done",
                "list-todo",
                "list-in-progress"};


        for (int i = 0; i < avaliableCommands.length; i++) {
            System.out.println("available commands to use: " + avaliableCommands[i]);
        }

        // taking user input
        System.out.println("Enter a command: ");
        String userInput = scanner.nextLine();
        String[] command = userInput.split(" ");

        Pattern task = Pattern.compile("\"(.*)\"");
        Matcher findInStr = task.matcher(userInput);

        ObjectMapper objectMapper = new ObjectMapper();
        // creating the JSON file
        File f = new File("Data.json");


        switch (command[0]) {
            case "add":
                if (findInStr.find()) {
                    try {
                        add(findInStr.group(1), objectMapper, f);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                break;
            case "update":
                update();
                break;
            case "delete":
                delete();
                break;
            case "mark-in-progress":
                markInProgress();
            case "mark-done":
                markDone();
            case "list":
                list();
        }
    }

    public static void add(String description, ObjectMapper objectMapper, File file) throws IOException{
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String currentTime = date + " " + time;
        int id = 1;
        Data data = new Data(id, description, currentTime);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, data);
        id += 1;
    }

    public static void update() {
        System.out.println("Updated a task");
    }

    public static void delete() {
        System.out.println("Deleted a task");
    }

    public static void markInProgress() {
        System.out.println("Marked task as in progress");
    }

    public static void markDone() {
        System.out.println("Marked task as done");
    }

    public static void list() {
        System.out.println("Listed task");
    }
}