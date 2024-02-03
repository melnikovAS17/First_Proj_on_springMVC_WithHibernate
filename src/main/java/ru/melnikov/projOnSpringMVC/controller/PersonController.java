package ru.melnikov.projOnSpringMVC.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.melnikov.projOnSpringMVC.DAO.PersonDAO;
import ru.melnikov.projOnSpringMVC.models.Book;
import ru.melnikov.projOnSpringMVC.models.Person;
import ru.melnikov.projOnSpringMVC.utils.MySpringValidatorPerson;

import javax.validation.Valid;
import java.util.List;


@Controller
public class PersonController {
    PersonDAO personDAO;
    MySpringValidatorPerson mySpringValidatorPerson;
    @Autowired
    public PersonController(PersonDAO personDAO, MySpringValidatorPerson mySpringValidatorPerson){
        this.personDAO = personDAO;
        this.mySpringValidatorPerson = mySpringValidatorPerson;
    }
    @GetMapping("/people")
    public String showAllPeople(Model model){
        model.addAttribute("people",personDAO.showAllPerson());
        return "personViews/showAllPeople";
    }

    @GetMapping("/people/{id}")
    public String showPersonInfo(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.showPersonInfo(id));
        List<Book> getBook = personDAO.checkOnIdPresent(id);
        if(getBook.isEmpty())model.addAttribute("nothingBook","Человек не взял ни одной книги");
        else model.addAttribute("listOfBooks",getBook);
        return "personViews/showPersonInfo";
    }

    @GetMapping("/people/new")
    public String getFormForCreateNewPerson(Model model){
        model.addAttribute("person", new Person());
        return "forms/formForCreateNewPerson";
    }

    @PostMapping("/people")
    public String createNewPerson(@ModelAttribute("person") @Valid Person person,
                                  BindingResult bindingResult){
        mySpringValidatorPerson.validate(person,bindingResult);
        if (bindingResult.hasErrors()) return "forms/formForCreateNewPerson";
        personDAO.savePerson(person);
        return "redirect:/people";
    }
    @GetMapping("/people/{id}/edit")
    public String getFormForEditPerson(@PathVariable("id") int id, Model model){
        model.addAttribute("person",personDAO.showPersonInfo(id));
        return "forms/formForEditPerson";
    }
    @PatchMapping("/people/{id}")
    public String editPerson(@PathVariable("id") int id, @ModelAttribute("person") @Valid Person person,
                             BindingResult bindingResult){
        if (bindingResult.hasErrors()) return "forms/formForEditPerson";
        personDAO.updatePerson(id,person);
        return "redirect:/people";
    }
    @DeleteMapping("/people/{id}")
    public String deletePerson(@PathVariable("id") int id){
        personDAO.deletePerson(id);
        return "redirect:/people";
    }

}
