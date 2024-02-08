package ru.melnikov.projOnSpringMVC.models;

import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "person")
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    @NotEmpty(message = "Name shouldn't be empty")
    private String name;
    @Column(name = "dateOfBirth")
    @NotEmpty(message = "data of birth shouldn't be empty")
    @Pattern(regexp = "^(0[1-9]|1\\d|2\\d|3[01])\\.(0[1-9]|1[0-2])\\.\\d{4}$", message = "data od birth should be correct: dd.mm.year 12.12.2012")
    private String dateOfBirth;

    //Устанавиваем загрузку -EAGER тк в методе проверки на наличие книг мы используем метод getBooks()
    //и связ-е об-ты не подгрузяться нв Lazy и будут находиться в состоянии detached соотв мы не сможем получить
    //List<Books> но мы могли использовать Hibernate.initialize
    //В spring session сразу закомитется после запроса (наверное :-))
    @OneToMany(mappedBy = "person", fetch = FetchType.EAGER)
    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    private List<Book> books;

    public Person(String name, String dateOfBirth) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
    }
    public Person(){}


    public int getId() {
        return id;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", dateOfBirth='" + dateOfBirth + '\'' +
                ", books=" + books +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return id == person.id && Objects.equals(name, person.name) && Objects.equals(dateOfBirth, person.dateOfBirth) && Objects.equals(books, person.books);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, dateOfBirth, books);
    }
}
