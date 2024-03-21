package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.entity.Book;
import project.entity.Person;

import java.util.List;

@Component
public class PersonBookDAO {
    private final JdbcTemplate jdbcTemplate;
    private final PersonDAO personDAO;
    private final BookDAO bookDAO;

    @Autowired
    public PersonBookDAO(JdbcTemplate jdbcTemplate, PersonDAO personDAO, BookDAO bookDAO) {
        this.jdbcTemplate = jdbcTemplate;
        this.personDAO = personDAO;
        this.bookDAO = bookDAO;
    }

    public Person searchByBookId(int bookId) {
        try {
            int idPerson = jdbcTemplate.queryForObject(
                    "SELECT idperson FROM personbook WHERE idbook = ?",
                    new Object[]{bookId}, Integer.class
            );
            return personDAO.index(idPerson);
        } catch (EmptyResultDataAccessException ex) {
            return null;
        }
    }

    public void save(int idPerson, int idBook) {
        jdbcTemplate.update("INSERT INTO personbook(idperson, idbook) VALUES (?,?)", idPerson, idBook);
    }

    public void delete(int bookId) {
        jdbcTemplate.update("DELETE FROM personbook WHERE idbook=?", bookId);
    }

    public List<Book> searchByPersonId(int idPerson) {
        List<Integer> list = jdbcTemplate.queryForList("SELECT idbook FROM personbook WHERE idperson=?",
                new Object[]{idPerson}, Integer.class);
        return bookDAO.queryForList(list);
    }
}
