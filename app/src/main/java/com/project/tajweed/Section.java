package com.project.tajweed;

public class Section {

    private  String desc = "";
    private String name= "";
    private String path= "";

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getName() {
        return name;
    }

    public String getPath() {
        return path;
    }

    @Override
    public String toString() {
        return "Section{" +
                "desc='" + desc + '\'' +
                ", name='" + name + '\'' +
                ", path='" + path + '\'' +
                '}';
    }
}