package org.crazymages.classes;

import lombok.extern.log4j.Log4j2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Year;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.*;


@Log4j2
public class Storage {

    private List<Lesson> requiredList;
    private List<Lesson> electiveList;
    private List<Lesson> consultationsList;
    private List<Lesson> holidaysList;
    private List<String> lessonsLinesFromFile;
    private List<String> holidaysLinesFromFile;
    private final String timetablePath = "src/main/resources/Timetable.txt";
    private final String holidaysPath = "src/main/resources/Holidays.txt";


    public Storage() {
        requiredList = new ArrayList<>();
        electiveList = new ArrayList<>();
        consultationsList = new ArrayList<>();
        holidaysList = new ArrayList<>();
        lessonsLinesFromFile = new ArrayList<>();
        addStringsFromFile(lessonsLinesFromFile, timetablePath);
//        addStringsFromFile(holidaysLinesFromFile, holidaysPath);
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
            log.error("Error occurred " + e.getMessage());
        }
    }

    public String showRequiredLectures() {
        requiredList = lessonsLinesFromFile.stream()
                .filter(e -> e.matches(".*\\bRequired\\b.*"))
                .map(Lesson::mapToEntity)
                .filter(e -> !getLocalDateFromSource(e.getDate()).isBefore(LocalDate.now()))
                .limit(5)
                .toList();

        return getString("Необходимые: \n\n", requiredList);
    }

    public String showElectiveLectures() {
        electiveList = lessonsLinesFromFile.stream()
                .filter(e -> !e.matches(".*\\bRequired\\b.*"))
                .filter(e -> !e.matches(".*\\bConsultation\\b.*"))
                .map(Lesson::mapToEntity)
                .filter(e -> !getLocalDateFromSource(e.getDate()).isBefore(LocalDate.now()))
                .limit(5)
                .toList();

        return getString("Выборочные: \n\n", electiveList);
    }

    public String showConsultations() {
        consultationsList = lessonsLinesFromFile.stream()
                .filter(e -> e.matches(".*\\bConsultation\\b.*"))
                .map(Lesson::mapToEntity)
                .filter(e -> !getLocalDateFromSource(e.getDate()).isBefore(LocalDate.now()))
                .limit(3)
                .toList();

        return getString("Консультации: \n\n", consultationsList);
    }

    public String showHolidays() {

//        work in progress...

        return getString("Каникулы: \n\n", holidaysList);
    }

    private String getString(String str, List<Lesson> lessonsList) {
        StringBuilder lessonsString = new StringBuilder(str);
        for (Lesson s : lessonsList) {
            lessonsString.append(s).append(" \n\n");
        }

        return lessonsString + Bot.getStartCommand();
    }

    private LocalDate getLocalDateFromSource(String source) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder()
                .appendPattern("dd.MM")
                .parseDefaulting(ChronoField.YEAR, Year.now().getValue())
                .toFormatter(Locale.getDefault());

        String date = Arrays.stream(source.split(" "))
                .findFirst()
                .orElseGet(() -> new SimpleDateFormat("dd.MM").format(new Date()));

        return LocalDate.parse(date, formatter);
    }

}
