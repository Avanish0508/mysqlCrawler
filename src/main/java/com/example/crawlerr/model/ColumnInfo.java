package com.example.crawlerr.model;

public class ColumnInfo {
    private String name;
    private String type;
    private int size;
    private boolean nullable;

    public ColumnInfo() {}

    public ColumnInfo(String name, String type, int size, boolean nullable) {
        this.name = name;
        this.type = type;
        this.size = size;
        this.nullable = nullable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
