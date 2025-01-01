package ronapps;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

// Imports used for JSON
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] avaliableCommands = new String[] {
                "add",
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
        boolean running = true;

        // Holds data
        ArrayList<Data> dataObjs = new ArrayList<>();

        while (running) {
            //System.out.println("Enter a command: ");
            String userInput = scanner.nextLine();
            String[] command = userInput.split(" ");

            // regex matching for the user wanting to add a task
            Pattern task = Pattern.compile("\"(.*)\"");
            Matcher taskMatcher = task.matcher(userInput);

            // regex matching to find all
            Pattern updateTask = Pattern.compile("\\b(\\d+)\\b");
            Matcher updateTaskMatcher = updateTask.matcher(userInput);

            ObjectMapper objectMapper = new ObjectMapper();
            // creating the JSON file
            File f = new File("Data.json");

            if (userInput.equals("exit")) {
                running = false;
            }

            switch (command[0]) {
                case "add":
                    if (taskMatcher.find()) {
                        try {
                            add(taskMatcher.group(1), objectMapper, f, dataObjs);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    break;
                case "update":
                    if (updateTaskMatcher.find() && taskMatcher.find()) {
                        update((updateTaskMatcher.group()), taskMatcher.group(1), dataObjs);
                    }
                    break;
                case "delete":
                    delete();
                    break;
                case "mark-in-progress":
                    markInProgress();
                case "mark-done":
                    markDone();
                case "list":
                    list(dataObjs);
            }
        }
    }

    public static void add(String description, ObjectMapper objectMapper, File file, ArrayList<Data> dataObjs) throws IOException{
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String currentTime = date + " " + time;

        int nextID = dataObjs.stream().map(Data::getID).max(Integer::compareTo).orElse(0) + 1;

        // creating a new task
        Data data = new Data(nextID, description, currentTime);
        dataObjs.add(data);

        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, dataObjs);
    }

    public static void update(String taskID, String newDescription, ArrayList<Data> dataObjs) {
        System.out.println("Updated a task");
        int ID = Integer.valueOf(taskID.trim());
        System.out.println(ID);
        dataObjs.get(ID).setDescription(newDescription);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String updatedTime = date + " " + time;
        dataObjs.get(ID).setUpdateTime(updatedTime);
    }

    public static void list(ArrayList<Data> dataObjs) {
        System.out.println("------------ALL TASKS------------");
        for (int i = 0; i < dataObjs.size(); i++) {
            System.out.println(dataObjs.get(i).getDescription());
        }
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
}