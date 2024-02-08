package ru.melnikov.projOnSpringMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.projOnSpringMVC.DAO.LibDAO;
import ru.melnikov.projOnSpringMVC.DAO.PersonDAO;
import ru.melnikov.projOnSpringMVC.models.Book;
import ru.melnikov.projOnSpringMVC.models.Person;
import ru.melnikov.projOnSpringMVC.utils.MySpringValidatorBook;

import javax.validation.Valid;
import java.util.Optional;

@Controller
    public class LibController {
    LibDAO libDAO;
    PersonDAO personDAO;
    MySpringValidatorBook mySpringValidatorBook;
    @Autowired
    public LibController(LibDAO libDAO,PersonDAO personDAO, MySpringValidatorBook mySpringValidatorBook) {
        this.libDAO = libDAO;
        this.personDAO = personDAO;
        this.mySpringValidatorBook = mySpringValidatorBook;
    }

    @GetMapping("/lib")
        public String showAllBooks(Model model){
            model.addAttribute("lib",libDAO.showAllLib());
            return "bookViews/showAllBooks";
        }

        @GetMapping("/lib/{id}")
        public String showBookInfo(@PathVariable("id") int id, Model model,
                                   @ModelAttribute("person") Person person){
        model.addAttribute("book",libDAO.showBookInfo(id));
            /*Optional<Person> idIsPresent = libDAO.checkOnIdPresent(id);*//*
            if(idIsPresent.isPresent()){
                model.addAttribute("getName",idIsPresent.get().getName());
            }else {*/
                model.addAttribute("people",personDAO.showAllPerson());

            return "bookViews/showBookInfo";
        }

        @GetMapping("/lib/new")
        public String getFormForCreateNewBook(Model model){
        model.addAttribute("book", new Book());
            return "forms/formForCreateNewBook";
        }

        @PostMapping("/lib")
        public String createNewBook(@ModelAttribute("book") @Valid Book book, BindingResult bindingResult){
        mySpringValidatorBook.validate(book,bindingResult);
        if(bindingResult.hasErrors()) return "forms/formForCreateNewBook";
        libDAO.saveBook(book);
            return "redirect:/lib";
        }

        @GetMapping("/lib/{id}/edit")
        public String getFormForEditBook(@PathVariable("id") int id, Model model){
        model.addAttribute("book",libDAO.showBookInfo(id));
            return "forms/formForEditBook";
        }

        @PatchMapping("/lib/{id}")
        public String editBook(@PathVariable("id") int id, @ModelAttribute("book")
                               @Valid Book book, BindingResult bindingResult){
            if(bindingResult.hasErrors()) return "forms/formForEditBook";
            libDAO.updateBook(id,book);
            return "redirect:/lib";
        }

        @DeleteMapping("/lib/{id}")
        public String deleteBook(@PathVariable("id") int id){
        libDAO.deleteBook(id);
            return "redirect:/lib";
        }
        // Метод назначения книги какому-то человеку
        @PatchMapping("/lib/{id}/appoint")
        public String appointBook(@ModelAttribute("person") Person person,@PathVariable("id") int id){
            libDAO.appointReader(id,person);
            return "redirect:/lib";
        }
        //Метод освобождает книгу
        @PatchMapping("/lib/{id}/cancel")
        public String cancelBook( @PathVariable("id") int id){
        libDAO.cancelBook(id);
        return "redirect:/lib";
        }


    }
