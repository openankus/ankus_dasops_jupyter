package org.ankus.service;

import java.util.Date;

public enum CodeSearchOption {
    Name("title"),
    Comment ("codeComment"),
    Writer ("writer"),
    Tag("tags");

    CodeSearchOption(String name) {
        this.column = name;
    }
    final private String column;

    public String getColumnName() {
        return this.column;
    }
}
