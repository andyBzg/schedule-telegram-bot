package org.crazymages.classes;

import lombok.Getter;

@Getter
public class Holiday {

    private String subject;
    private String startDate;
    private String endDate;


    public Holiday(String subject, String startDate, String endDate) {
        this.subject = subject;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static Holiday mapToEntity(String line) {
        String[] strings = line.split("\t");
        String regex = "\\d+:\\d+";
        String subject = strings.length >= 1 ? strings[0] : "";
        String startDate = strings.length >= 1 ? strings[1].replaceAll(regex, "") : "";
        String endDate = strings.length >= 1 ? strings[2].replaceAll(regex, "") : "";
        return new Holiday(
                subject,
                startDate,
                endDate
        );
    }

    @Override
    public String toString() {
        return subject + "\n" + startDate + "- " + endDate;
    }
}
