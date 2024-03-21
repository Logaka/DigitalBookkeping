package project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.dao.PersonBookDAO;
import project.dao.PersonDAO;
import project.entity.Person;
import project.utill.PersonValidator;

@Controller
@RequestMapping("/people")
public class PersonController {
    private final PersonDAO personDAO;
    private final PersonBookDAO personBookDAO;
    private final PersonValidator personValidator;

    @Autowired
    public PersonController(PersonDAO personDAO, PersonBookDAO personBookDAO, PersonValidator personValidator) {
        this.personDAO = personDAO;
        this.personBookDAO = personBookDAO;
        this.personValidator = personValidator;
    }

    //create REST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("people", personDAO.list());
        return "people/list";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.index(id))
                .addAttribute("ownBooks", personBookDAO.searchByPersonId(id));
        return "people/index";
    }

    @GetMapping("/new")
    public String newPerson(@ModelAttribute("person") Person person) {
        return "people/newPerson";
    }

    @PostMapping
    public String create(@ModelAttribute("person") @Valid Person person,
                         BindingResult bindingResult) {
        personValidator.validate(person,bindingResult);
        if (bindingResult.hasErrors())
            return "people/newPerson";
        personDAO.create(person);
        return "redirect: /people";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("person", personDAO.index(id));
        return "people/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute("person") @Valid Person editPerson,
                         BindingResult bindingResult) {
        personValidator.validate(editPerson,bindingResult);
        if (bindingResult.hasErrors())
            return "people/edit";
        personDAO.edit(editPerson);
        return "redirect: /people";
    }

    @DeleteMapping("{id}/delete")
    public String delete(@PathVariable("id") int id) {
        personDAO.delete(id);
        return "redirect: /people";
    }
}
