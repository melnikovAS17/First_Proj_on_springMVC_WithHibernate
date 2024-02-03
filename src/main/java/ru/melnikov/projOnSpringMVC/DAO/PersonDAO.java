package ru.melnikov.projOnSpringMVC.DAO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import ru.melnikov.projOnSpringMVC.models.Book;
import ru.melnikov.projOnSpringMVC.models.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> showAllPerson(){
        return jdbcTemplate.query("SELECT * FROM person", new BeanPropertyRowMapper<>(Person.class));
    }

    public Person showPersonInfo(int id){
        return jdbcTemplate.query("SELECT * FROM person WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void savePerson (Person person){
        jdbcTemplate.update("INSERT INTO person(name, dateofbirth) VALUES (?,?)", person.getName(),
                person.getDateOfBirth());
    }

    public void updatePerson (int id, Person person){
        jdbcTemplate.update("UPDATE person SET name = ?, dateofbirth = ? WHERE id = ?",person.getName(),
                person.getDateOfBirth(),id);
    }

    public void deletePerson(int id){
        jdbcTemplate.update("DELETE FROM person WHERE id = ?",id);
    }

    //Method validator name - unique
    public Optional<Person> nameUniquePerson(String name){
        return jdbcTemplate.query("SELECT * FROM person WHERE name = ?", new Object[]{name},
                new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    //Метод проверяет читает ли данный человек книгу или нет
    public List<Book> checkOnIdPresent(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE idperson=?",
                        new Object[]{id},new BeanPropertyRowMapper<>(Book.class));
    }
}
