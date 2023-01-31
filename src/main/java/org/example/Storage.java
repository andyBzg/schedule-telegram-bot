package org.example;

import java.util.ArrayList;

public class Storage {

    private ArrayList<String> lessonList;
    private ArrayList<String> electiveList;
    private ArrayList<String> additionalList;

    public Storage() {
        lessonList = new ArrayList<>();
        //TODO изменить спопоб добавления уроков
        lessonList.add("Java - 30.01 start at 18:30 \nTkachenko Daniil");
        lessonList.add("Java - 01.02 start at 18:30 \nTkachenko Daniil");
        lessonList.add("Algorithms - 03.02 start at 19:00 \nBarseghyan Gegham");

        electiveList = new ArrayList<>();
        electiveList.add("Организация тестирования в команде разработчиков, коммуникация и взаимодействие - " +
                "30.01 start at 15:00 \nSorokin Stepan");
        electiveList.add("Нейронные сети и их применение - 30.01 start at 19:00 \nGribkov Aleksander");
        electiveList.add("FTP and HTTP services of the Internet. (in English) - " +
                "31.01 start at  19:00 \nZakoyan Tigran");
        electiveList.add("Нейронные сети и их применение. - 01.02 start at 15:00 \nMoiseev Ilya");
        electiveList.add("Организация тестирования в команде разработчиков, коммуникация и взаимодействие - " +
                "01.02 start at 19:00 \nMiadzvedzeva Tatsiana");
        electiveList.add("Информационные технологии в различных сферах деятельности - " +
                "02.02 start at 15:00 \nAkerov Kunerkhan");
        electiveList.add("QR-коды. Их создание и применение. - 02.02 start at 19:00 \nDomasevich Andrey");


        additionalList = new ArrayList<>();
        additionalList.add("Java consultation - 04.02 start at 13:00 \nEgorov Mikhail");
        additionalList.add("FE consultation - 04.02 start at 13:00 \nMovsisyan Tigran");
        additionalList.add("Linux consultation - 04.02 start at 15:00 \nBakhtinov Andrei");
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
        for (String s : additionalList) {
            additional += s + " \n\n";
        }
        return additional;
    }

    //TODO реализовать добавление лекций в листы
    public void setNextLesson(String incomingMsg) {
        lessonList.add(incomingMsg);
    }

}
