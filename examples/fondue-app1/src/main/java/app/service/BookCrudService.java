package app.service;

import java.util.List;
import org.springframework.stereotype.Service;
import fondue.fw.PP;
import app.model.Book;

@Service
public interface BookCrudService {

    default List<Book> getBooks() {
        return getBooks(new PP());
    }

    List<Book> getBooks(PP page);

    Book getBook(long id);

    Book create(Book entity);

    Book update(Book entity);

    void delete(long id);
}
