package ru.melnikov.projOnSpringMVC.DAO;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.melnikov.projOnSpringMVC.models.Book;
import ru.melnikov.projOnSpringMVC.models.Person;

import java.util.List;
import java.util.Optional;


@Component
public class PersonDAO {
    private final SessionFactory sessionFactory;
    @Autowired
    public PersonDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Transactional(readOnly = true)
    public List<Person> showAllPerson(){
        Session session = sessionFactory.getCurrentSession();
        return session.createQuery("SELECT p from Person p", Person.class)
                .getResultList();
    }

    @Transactional(readOnly = true)
    public Person showPersonInfo(int id){
        Session session = sessionFactory.getCurrentSession();
        return session.get(Person.class,id);
    }

    @Transactional
    public void savePerson (Person person){
        Session session = sessionFactory.getCurrentSession();
        session.save(person);
    }

    @Transactional
    public void updatePerson (int id, Person person){
        Session session = sessionFactory.getCurrentSession();
        Person personToUpdate = session.get(Person.class,id);
        personToUpdate.setName(person.getName());
        personToUpdate.setDateOfBirth(person.getDateOfBirth());
        personToUpdate.setBooks(person.getBooks());

    }

    @Transactional
    public void deletePerson(int id){
        Session session = sessionFactory.getCurrentSession();
        session.remove(session.get(Person.class,id));
    }

    //Method validator name - unique
    //*************************Hibernate не может вернуть optional - советуют использовать DTO

   /* public Optional<Person> nameUniquePerson(String name){
        Session session = sessionFactory.getCurrentSession();
        List<Person> people = session.createQuery("select p from Person p where Person .name = name").getResultList();
        return Optional.ofNullable(Person.toOptional(people));
    }*/
    //Метод проверяет читает ли данный человек книгу или нет
    @Transactional(readOnly = true)
    public List<Book> checkOnIdPresent(int id){
        Session session = sessionFactory.getCurrentSession();
        Person person = session.get(Person.class,id);

        return person.getBooks();
    }
}
