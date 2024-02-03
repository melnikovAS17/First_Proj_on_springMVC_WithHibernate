package ru.melnikov.projOnSpringMVC.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.melnikov.projOnSpringMVC.DAO.LibDAO;
import ru.melnikov.projOnSpringMVC.models.Book;


@Component
public class MySpringValidatorBook implements Validator {
    LibDAO libDAO;
    @Autowired
    public MySpringValidatorBook(LibDAO libDAO) {
        this.libDAO = libDAO;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Book.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Book book = (Book) o;
        if(libDAO.titleUniqueLib(book.getTitle()).isPresent())
            errors.rejectValue("title","","this title already taken");
    }
}
