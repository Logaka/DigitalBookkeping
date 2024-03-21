package project.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import project.entity.Person;

import java.util.List;
import java.util.Optional;

@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // create CRUD

    public List<Person> list() {
        return jdbcTemplate.query("SELECT * FROM person",
                new BeanPropertyRowMapper<>(Person.class));
    }


    public void create(Person person) {
        jdbcTemplate.update("INSERT INTO person(fullName, birthday) VALUES(?,?)",
                person.getFullName(), person.getBirthday());
    }

    public Person index(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?", new Object[]{id}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny().orElse(null);
    }

    public void edit(Person editPerson) {
        jdbcTemplate.update("UPDATE person SET fullName=?,birthday=? WHERE id=?",
                editPerson.getFullName(),editPerson.getBirthday(),editPerson.getId());
    }

    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE id=?", id);
    }

    public Optional<Person> show(String fullName){
        return jdbcTemplate.query("SELECT * FROM person WHERE fullName=?",
                new Object[]{fullName}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
    public Optional<Person> show(int personId){
        return jdbcTemplate.query("SELECT * FROM person WHERE id=?",
                new Object[]{personId}, new BeanPropertyRowMapper<>(Person.class)).stream().findAny();
    }
}
