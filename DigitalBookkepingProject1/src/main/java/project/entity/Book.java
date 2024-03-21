package project.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    @NotEmpty(message = "Book Name not should be empty")
    @Size(min = 2, max = 30, message = "Name`s length should be between 2 and 30 characters")
    private String name;
    @NotEmpty(message = "Need to specify autor")
    @Size(min = 2, max = 30, message = "Name`s length should be between 2 and 30 characters")
    private String autor;
    @Min(value = 1500, message = "year should be more than 1500")
    private int year;

    public Book() {
    }

    public Book(int id, String name, String autor, int year) {
        this.id = id;
        this.name = name;
        this.autor = autor;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
