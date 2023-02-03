package org.example;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Storage {

    private ArrayList<String> lessonList;
    private ArrayList<String> electiveList;
    private ArrayList<String> consultationsList;

    public Storage() {
        lessonList = new ArrayList<>();
        addLecturesFromFile(lessonList, "Timetable.txt");

        electiveList = new ArrayList<>();
        addLecturesFromFile(electiveList, "Elective.txt");

        consultationsList = new ArrayList<>();
        addLecturesFromFile(consultationsList, "Consultations.txt");
    }

    private void addLecturesFromFile(ArrayList<String> list, String fileName) {
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

    public String showAllLectures() {
        String allLectures = "Все лекции: \n\n";
        for (String s : lessonList) {
            allLectures += s + " \n\n";
        }
        return allLectures;
    }

    public String showElectiveLectures() {
        String elective = "Выборочные: \n\n";
        for (String s : electiveList) {
            elective += s + " \n\n";
        }
        return elective;
    }

    public String showAdditional() {
        String additional = "Консультации: \n\n";
        for (String s : consultationsList) {
            additional += s + " \n\n";
        }
        return additional;
    }

}
