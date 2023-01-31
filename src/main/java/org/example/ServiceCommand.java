package org.example;

public enum ServiceCommand {

    START("/start"),
    INFO("/info"),
    REQUIRED("/required"),
    ELECTIVE("/elective"),
    ADDITIONAL("/additional");

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
