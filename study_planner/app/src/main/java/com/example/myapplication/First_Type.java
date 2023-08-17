package com.example.myapplication;

public class First_Type {
    private String date;
    private String name;
    private String time;
    private String topics;
    private String course;

    public First_Type(String date, String name, String time, String topics,String course){
        this.date=date;
        this.name=name;
        this.time=time;
        this.topics=topics;
        this.course=course;
    }
    public First_Type(){}

    public String getDate() {
        return date;
    }
    public void setDate(String date){this.date = date;}

    public String getName() {
        return name;
    }
public void setName(String name){this.name=name;}
    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTopics() {
        return topics;
    }

    public void setTopics(String topics) {
        this.topics = topics;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }
}
