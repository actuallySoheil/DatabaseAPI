package me.wixi.database;

import java.util.Collections;

public class DatabaseContainer {

    private final String[] strings;

    public DatabaseContainer(String... strings) {
        this.strings = strings;
    }

    public String getColumnsAndQuestionMarks() {
        return "(uniqueId, " + String.join(", ", this.strings) + ") VALUES (" + String.join(", ", Collections.nCopies(this.strings.length + 1, "?")) + ')';
    }

}