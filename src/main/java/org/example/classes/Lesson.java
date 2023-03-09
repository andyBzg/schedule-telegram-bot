package org.example.classes;

public class Lesson {

    private String subject;
    private String teacher;
    private String date;


    public Lesson(String subject, String teacher, String date) {
        this.subject = subject;
        this.teacher = teacher;
        this.date = date;
    }

    public static Lesson mapToEntity(String line) {
        String[] strings = line.split("\t");
        String subject = strings.length >= 1 ? strings[0] : "";
        String teacher = strings.length >= 1 ? strings[1] : "";
        String date = strings.length >= 1 ? strings[2] : "";
        return new Lesson(
                subject,
                teacher,
                date
        );
    }

    @Override
    public String toString() {
        return subject + " | " + teacher + "\n" + date + " (GMT +01:00)";
    }
}
