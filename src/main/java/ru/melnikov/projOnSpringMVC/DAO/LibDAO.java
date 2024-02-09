package ru.melnikov.projOnSpringMVC.DAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.projOnSpringMVC.models.Book;
import ru.melnikov.projOnSpringMVC.models.Person;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


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
    @Transactional(readOnly = true)
    public Person checkOnIdPresent(int id){
        Session session = sessionFactory.getCurrentSession();
        Book book = session.get(Book.class,id);
        return book.getPerson();
    }
    //Метод назначет человеку книгу (устанавливает айди человека в таблице книг в колонке idperson)
    @Transactional
    public void appointReader(int idBook ,Person person) {
        Session session = sessionFactory.getCurrentSession();
        //NullPointerException - не нашёл решение
        //************************************
       /* person = session.get(Person.class,person.getId());
        Book book = session.get(Book.class,idBook);
        if(person.getBooks().isEmpty()){
            person.setBooks(new ArrayList<>(Collections.singletonList(book)));
        }else {
            person.getBooks().add(book);
        }
        book.setPerson(person);*/
        //***********************************
       /* person = session.get(Person.class,person.getId());
        Book book = session.get(Book.class,idBook);
        person.setBooks(new ArrayList<>(Collections.singletonList(book)));
        book.setPerson(person);*/
        //***********************************
        session.createQuery("update Book set Book.person = :idPerson where Book.id = :idBook")
                .setParameter("idPerson",person.getId())
                .setParameter("idBook",idBook)
                .executeUpdate();
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

