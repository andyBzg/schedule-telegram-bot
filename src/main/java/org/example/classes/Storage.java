package org.example.classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Storage {

    private List<String> lessonList;
    private List<String> electiveList;
    private List<String> consultationsList;


    public Storage() {

    }

    private void addLecturesFromFile(List<String> list, String fileName) {
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
        lessonList = new ArrayList<>();
        addLecturesFromFile(lessonList, "Timetable.txt");

        StringBuilder allLectures = new StringBuilder("Необходимые: \n\n");
        for (String s : lessonList) {
            allLectures.append(s).append(" \n\n");
        }
        return allLectures + "" + Bot.START_COMMAND;
    }

    public String showElectiveLectures() {
        electiveList = new ArrayList<>();
        addLecturesFromFile(electiveList, "Elective.txt");

        StringBuilder elective = new StringBuilder("Выборочные: \n\n");
        for (String s : electiveList) {
            elective.append(s).append(" \n\n");
        }
        return elective + "" + Bot.START_COMMAND;
    }

    public String showConsultations() {
        consultationsList = new ArrayList<>();
        addLecturesFromFile(consultationsList, "Consultations.txt");

        StringBuilder additional = new StringBuilder("Консультации: \n\n");
        for (String s : consultationsList) {
            additional.append(s).append(" \n\n");
        }
        return additional + "" + Bot.START_COMMAND;
    }

}
