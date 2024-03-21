package project.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import project.dao.BookDAO;
import project.dao.PersonBookDAO;
import project.dao.PersonDAO;
import project.entity.Book;
import project.entity.Person;

@Controller
@RequestMapping("/books")
public class BookController {
    private final BookDAO bookDAO;
    private final PersonDAO personDAO;
    private final PersonBookDAO personBookDAO;

    @Autowired
    public BookController(BookDAO bookDAO, PersonDAO personDAO, PersonBookDAO personBookDAO) {
        this.bookDAO = bookDAO;
        this.personDAO = personDAO;
        this.personBookDAO = personBookDAO;
    }

    // do REST
    @GetMapping
    public String list(Model model) {
        model.addAttribute("books", bookDAO.list());
        return "books/list";
    }

    @GetMapping("/{id}")
    public String index(@PathVariable("id") int id, Model model, @ModelAttribute("person") Person person) {
        Person bookOwner = personBookDAO.searchByBookId(id);

        model.addAttribute("book", bookDAO.index(id))
                .addAttribute("people", personDAO.list())
                .addAttribute("bookOwner", bookOwner);
        return "books/index";
    }

    @GetMapping("/new")
    public String newBook(@ModelAttribute("book") Book book) {
        return "books/newBook";
    }

    @PostMapping
    public String create(@ModelAttribute("book") @Valid Book book,
                         BindingResult bindingResult) {
        if(bindingResult.hasErrors())
            return "books/newBook";
        bookDAO.create(book);
        return "redirect: /books";
    }

    @GetMapping("/{id}/edit")
    public String edit(@PathVariable("id") int id, Model model) {
        model.addAttribute("book", bookDAO.index(id));
        return "books/edit";
    }

    @PostMapping("/{id}")
    public String update(@ModelAttribute @Valid Book editedBook,
                         BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "books/edit";
        bookDAO.update(editedBook);
        return "redirect: /books";
    }

    @DeleteMapping("/{id}/delete")
    public String delete(@PathVariable("id") int id) {
        bookDAO.delete(id);
        return "redirect: /books";
    }


    @PostMapping("{id}/owner")
    public String ownBook(@PathVariable("id") int idBook,
                          @ModelAttribute("person") Person person) {
        personBookDAO.save(person.getId(), idBook);
        return "redirect: /books";
    }

    @DeleteMapping("/{id}/owner/delete")
    public String deleteBookOwner(@PathVariable("id") int bookId){
        personBookDAO.delete(bookId);
        return "redirect: /books";
    }
}
