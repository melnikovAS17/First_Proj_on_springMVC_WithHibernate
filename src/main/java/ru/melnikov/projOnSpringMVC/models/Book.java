package ru.melnikov.projOnSpringMVC.models;


import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class Book {
    private int id;

    @NotEmpty(message = "Title shouldn't be empty")
    @Size(min = 2, max = 70, message = "Title should be between 2 and 70 character")
    private String title;
    @NotEmpty(message = "Author field shouldn't be empty")
    @Size(min = 2, max = 70, message = "Author's name should be between 2 and 70 character")
    @Pattern(regexp = "[A-Z]\\w+ [A-z]\\w+", message = "Author's name should be correct: Mikhail Mikhalkov")
    private String author;
    @Pattern(regexp = "\\d{4}", message = "Data should be correct: 1940")
    private String date;

   /* public Book(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }
    public Book(){}*/

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
