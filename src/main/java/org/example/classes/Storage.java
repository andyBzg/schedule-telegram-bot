package org.example.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Storage {

    private List<Lesson> requiredList;
    private List<Lesson> electiveList;
    private List<Lesson> consultationsList;
    private List<String> linesFromFile;


    public Storage() {
        requiredList = new ArrayList<>();
        electiveList = new ArrayList<>();
        consultationsList = new ArrayList<>();
        linesFromFile = new ArrayList<>();
        addStringsFromFile(linesFromFile, "Timetable.txt");
    }

    private void addStringsFromFile(List<String> list, String fileName) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line;
            while ((line = reader.readLine()) != null) {
                list.add(line);
            }
            reader.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String showRequiredLectures() {
        requiredList = linesFromFile.stream()
                .filter(e -> e.matches(".*\\bRequired\\b.*"))
                .limit(5)
                .map(Lesson::mapToEntity)
                .toList();

        return getString("Необходимые: \n\n", requiredList);
    }

    public String showElectiveLectures() {
        electiveList = linesFromFile.stream()
                .filter(e -> !e.matches(".*\\bRequired\\b.*"))
                .filter(e -> !e.matches(".*\\bConsultation\\b.*"))
                .limit(5)
                .map(Lesson::mapToEntity)
                .toList();

        return getString("Выборочные: \n\n", electiveList);
    }

    public String showConsultations() {
        consultationsList = linesFromFile.stream()
                .filter(e -> e.matches(".*\\bConsultation\\b.*"))
                .limit(3)
                .map(Lesson::mapToEntity)
                .toList();

        return getString("Консультации: \n\n", consultationsList);
    }

    private String getString(String str, List<Lesson> lessonsList) {
        StringBuilder lessonsString = new StringBuilder(str);
        for (Lesson s : lessonsList) {
            lessonsString.append(s).append(" \n\n");
        }

        return lessonsString + "" + Bot.getStartCommand();
    }

}
