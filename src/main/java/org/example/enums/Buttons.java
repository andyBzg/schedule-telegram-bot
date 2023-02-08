package org.example.enums;

public enum Buttons {

    REQUIRED_BUTTON_TEXT("Необходимые"),
    REQUIRED_BUTTON("REQUIRED_BUTTON_TOKEN"),
    ELECTIVE_BUTTON_TEXT("Выборочные"),
    ELECTIVE_BUTTON("ELECTIVE_BUTTON_TOKEN"),
    CONSULT_BUTTON_TEXT("Консультации"),
    CONSULT_BUTTON("CONSULT_BUTTON_TOKEN");


    private String button;


    Buttons(String button) {
        this.button = button;
    }

    @Override
    public String toString() {
        return button;
    }
}
