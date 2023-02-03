package org.example;

public enum ServiceCommand {

    START("/start"),
    HELP("/help"),
    REQUIRED("/required"),
    ELECTIVE("/elective"),
    CONSULTATION("/consultation");

    private String cmd;

    ServiceCommand(String cmd) {
        this.cmd = cmd;
    }

    @Override
    public String toString() {
        return cmd;
    }

    public boolean equals(String cmd) {
        return this.toString().equals(cmd);
    }
}
