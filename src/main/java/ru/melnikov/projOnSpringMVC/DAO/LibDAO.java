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
public class LibDAO {
    JdbcTemplate jdbcTemplate;
    @Autowired
    public LibDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }
    public List<Book> showAllLib(){
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book showBookInfo(int id){
        return jdbcTemplate.query("SELECT * FROM books WHERE id = ?", new Object[]{id},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void saveBook(Book book){
        jdbcTemplate.update("INSERT INTO books(title,author,date) VALUES(?,?,?)",book.getTitle(),book.getAuthor(),
        book.getDate());
    }

    public void updateBook(int id, Book book){
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, date = ? WHERE id = ?", book.getTitle(),book.getAuthor(),
                book.getDate(),id);
    }

    public void deleteBook(int id){
        jdbcTemplate.update("DELETE FROM books WHERE id = ?",id);
    }
    //Method validator for title - UNIQUE
    public Optional<Book> titleUniqueLib(String title){
        return jdbcTemplate.query("SELECT * FROM books WHERE title = ?", new Object[]{title},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }

    //Метод проверяет читает книгу кто-то или нет(передаём в параметр id книги)
    public Optional<Person> checkOnIdPresent(int id){
        //из-за перестановки book.idperson = person.id - не работал корректно метод назначения книги читателю
        //поменял местами, после чего поменял всё обратно - заработало!) (2 дня поиска проблемы)
      return jdbcTemplate.query("SELECT person.* FROM books join person on person.id = books.idperson " +
                              " where books.id =?",
                      new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
              .stream().findAny();
    }
    //Метод назначет человеку книгу (устанавливает айди человека в таблице книг в колонке idperson)
    public void appointReader(int idBook ,Person person) {
        jdbcTemplate.update("UPDATE books SET idperson = ? WHERE id = ?", person.getId(), idBook);
    }
    //Метод освобождает книгу
    public void cancelBook(int id){
        jdbcTemplate.update("UPDATE books SET idperson = null where id = ?",id);
    }
}

