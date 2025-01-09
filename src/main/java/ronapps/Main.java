package ronapps;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

// Imports used for JSON
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Main {
    public static void main(String[] args) throws IOException {
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

        boolean running = true;
        ObjectMapper objectMapper = new ObjectMapper();

        // Holds data
        ArrayList<Data> dataObjs = new ArrayList<>();

        // creating the JSON file
        File f = new File("Data.json");

        // check if the JSON file was empty first, if it isn't empty then add the existing tasks
        if (!(f.length() == 0)) {
            try {
                List<Data> tempList = objectMapper.readValue(f, new TypeReference<List<Data>>() {});
                for (int i = 0; i < tempList.size(); i++) {
                    dataObjs.add(tempList.get(i));
                }
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        while (running) {
            //System.out.println("Enter a command: ");
            String userInput = scanner.nextLine();
            String[] command = userInput.split(" ");

            // regex matching for the user wanting to add a task
            Pattern task = Pattern.compile("\"(.*)\"");
            Matcher taskMatcher = task.matcher(userInput);

            // regex matching to find all
            Pattern taskID = Pattern.compile("\\b(\\d+)\\b");
            Matcher idMatcher = taskID.matcher(userInput);


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
                    if (idMatcher.find() && taskMatcher.find()) {
                        update((idMatcher.group()), taskMatcher.group(1), objectMapper, f, dataObjs);
                    }
                    break;
                case "delete":
                    try {
                        if (idMatcher.find()) {
                            delete((idMatcher.group()), dataObjs, objectMapper, f);
                        }
                    } catch (IOException e) {
                        System.out.println("The ID was not found");
                    }
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
        savingData(objectMapper, file, dataObjs);
    }

    public static void update(String taskID, String newDescription, ObjectMapper objectMapper, File file, ArrayList<Data> dataObjs) throws IOException {
        System.out.println("Updated a task");
        int ID = Integer.valueOf(taskID.trim()) - 1;
        dataObjs.get(ID).setDescription(newDescription);

        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();

        String updatedTime = date + " " + time;
        dataObjs.get(ID).setUpdateTime(updatedTime);
        savingData(objectMapper, file, dataObjs);
    }

    public static void list(ArrayList<Data> dataObjs) {
        System.out.println("------------ALL TASKS------------");
        for (int i = 0; i < dataObjs.size(); i++) {
            System.out.println("ID: " + dataObjs.get(i).getID() + " Description: " + dataObjs.get(i).getDescription());
        }
    }

    public static void delete(String taskID, ArrayList<Data> dataObjs, ObjectMapper objectMapper, File file) throws IOException {
        int ID = Integer.valueOf(taskID.trim()) - 1;
        dataObjs.remove(dataObjs.get(ID));
        savingData(objectMapper, file, dataObjs);
    }

    public static void markInProgress() {
        System.out.println("Marked task as in progress");
    }

    public static void markDone() {
        System.out.println("Marked task as done");
    }

    public static void savingData(ObjectMapper objectMapper, File file, ArrayList<Data> dataObjs) throws IOException {
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, dataObjs);
    }
}