package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> list() {
        return jdbcTemplate.query("SELECT * FROM book", new BeanPropertyRowMapper<>(Book.class));
    }

    public Book index(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE id=?",
                new Object[]{id}, new BeanPropertyRowMapper<>(Book.class)).stream().findAny().orElse(null);
    }

    public void create(Book book) {
        jdbcTemplate.update("INSERT INTO book(name,autor, year) values(?,?,?)",
                book.getName(), book.getAutor(), book.getYear());
    }

    public void update(Book editedBook) {
        jdbcTemplate.update("UPDATE book SET name=?,autor=?,year=? WHERE id=?",
                editedBook.getName(), editedBook.getAutor(), editedBook.getYear(), editedBook.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE id=?", id);
    }

    public List<Book> queryForList(List<Integer> list){
        if (list.isEmpty()) {
            return new ArrayList<>();
        }

        String placeholders = list.stream()
                .map(id -> "?")
                .collect(Collectors.joining(", "));

        String sql = "SELECT * FROM book WHERE id IN (" + placeholders + ")";

        return jdbcTemplate.query(sql, list.toArray(), new BeanPropertyRowMapper<>(Book.class));
    }
}
