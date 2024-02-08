package ru.melnikov.projOnSpringMVC.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.projOnSpringMVC.models.Book;
import ru.melnikov.projOnSpringMVC.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class LibDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    public LibDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Transactional(readOnly = true)
    public List<Book> showAllLib(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("select b from Book b", Book.class).getResultList();
    }

    @Transactional(readOnly = true)
    public Book showBookInfo(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Book.class,id);
    }

    @Transactional
    public void saveBook(Book book){
        Session session = sessionFactory.getCurrentSession();
        session.save(book);
    }

    @Transactional
    public void updateBook(int id, Book book){
        Session session = sessionFactory.getCurrentSession();
        Book bookToUpdate = session.get(Book.class,id);
        bookToUpdate.setTitle(book.getTitle());
        bookToUpdate.setAuthor(book.getAuthor());
        bookToUpdate.setDate(book.getDate());
        bookToUpdate.setPerson(book.getPerson());
    }

    @Transactional
    public void deleteBook(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Book.class,id));
    }
    //Method validator for title - UNIQUE
   /* public Optional<Book> titleUniqueLib(String title){
        return jdbcTemplate.query("SELECT * FROM books WHERE title = ?", new Object[]{title},
                new BeanPropertyRowMapper<>(Book.class)).stream().findAny();
    }*/

    //Метод проверяет читает книгу кто-то или нет(передаём в параметр id книги)
    /*public Optional<Person> checkOnIdPresent(int id){
        //из-за перестановки book.idperson = person.id - не работал корректно метод назначения книги читателю
        //поменял местами, после чего поменял всё обратно - заработало!) (2 дня поиска проблемы)
      return jdbcTemplate.query("SELECT person.* FROM books join person on person.id = books.idperson " +
                              " where books.id =?",
                      new Object[]{id},new BeanPropertyRowMapper<>(Person.class))
              .stream().findAny();
    }*/
    //Метод назначет человеку книгу (устанавливает айди человека в таблице книг в колонке idperson)
    @Transactional
    public void appointReader(int idBook ,Person person) {
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,idBook);
        Person person1 = session.get(Person.class, person.getId());
        if(person1.getBooks().isEmpty()){
            person1.setBooks(new ArrayList<>(Collections.singletonList(book)));
        }else {
            person1.getBooks().add(book);
        }
        book.setPerson(person);

    }
    //Метод освобождает книгу
    @Transactional
    public void cancelBook(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,id);
        Person person = book.getPerson();
        person.getBooks().remove(book);
        book.setPerson(null);

    }
}

