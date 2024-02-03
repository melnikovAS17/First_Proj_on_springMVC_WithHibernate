package ru.melnikov.projOnSpringMVC.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.melnikov.projOnSpringMVC.DAO.PersonDAO;
import ru.melnikov.projOnSpringMVC.models.Person;
@Component
public class MySpringValidatorPerson implements Validator {
    PersonDAO personDAO;
    @Autowired
    public MySpringValidatorPerson(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Person.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
    Person person = (Person) o;
    if(personDAO.nameUniquePerson(person.getName()).isPresent())
        errors.rejectValue("name","","this name already taken");
    }
}
