package ru.melnikov.projOnSpringMVC.models;


import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Objects;

@Entity
@Table(name = "books")
public class Book {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    @NotEmpty(message = "Title shouldn't be empty")
    @Size(min = 2, max = 70, message = "Title should be between 2 and 70 character")
    private String title;
    @Column(name = "author")
    @NotEmpty(message = "Author field shouldn't be empty")
    @Size(min = 2, max = 70, message = "Author's name should be between 2 and 70 character")
    @Pattern(regexp = "[A-Z]\\w+ [A-z]\\w+", message = "Author's name should be correct: Mikhail Mikhalkov")
    private String author;
    @Column(name = "date")
    @Pattern(regexp = "\\d{4}", message = "Data should be correct: 1940")
    private String date;

    @ManyToOne
    @JoinColumn(name = "idPerson",referencedColumnName = "id")
    private Person person;

    public Book(String title, String author, String date) {
        this.title = title;
        this.author = author;
        this.date = date;
    }
    public Book(){}

    public int getId() {
        return id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", date='" + date + '\'' +
                ", person=" + person +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Book book = (Book) o;
        return id == book.id && Objects.equals(title, book.title) && Objects.equals(author, book.author) && Objects.equals(date, book.date) && Objects.equals(person, book.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, author, date, person);
    }
}
