package com.example.demo.controllers;

public class Note {
    int id;
    String date, time, noteContent;

    public int getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public String getTime() {
        return time;
    }

    public String getNoteContent() {
        return noteContent;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setNoteContent(String noteContent) {
        this.noteContent = noteContent;
    }

    public Note(int id, String date, String time, String noteContent){
        this.id = id;
        this.date = date;
        this.time = time;
        this.noteContent = noteContent;
    }

    @Override
    public String toString() {
        return "Note properties: " + this.id + " " + this.date + " " + this.time + " " + this.noteContent;
    }
}
