package org.example;

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

        String allLectures = "Все лекции: \n\n";
        for (String s : lessonList) {
            allLectures += s + " \n\n";
        }
        return allLectures;
    }

    public String showElectiveLectures() {
        electiveList = new ArrayList<>();
        addLecturesFromFile(electiveList, "Elective.txt");

        String elective = "Выборочные: \n\n";
        for (String s : electiveList) {
            elective += s + " \n\n";
        }
        return elective;
    }

    public String showConsultations() {
        consultationsList = new ArrayList<>();
        addLecturesFromFile(consultationsList, "Consultations.txt");

        String additional = "Консультации: \n\n";
        for (String s : consultationsList) {
            additional += s + " \n\n";
        }
        return additional;
    }

}
