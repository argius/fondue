package app.service;

import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fondue.fw.PP;
import app.dao.BookMapper;
import app.model.Book;
import app.model.BookExample;

@Component
public class BookCrudServiceImpl implements BookCrudService {

    @Autowired
    private BookMapper mapper;

    @Override
    public List<Book> getBooks(PP page) {
        BookExample example = new BookExample();
        page.setTotalCount(mapper.countByExample(example));
        RowBounds rowBounds = new RowBounds(page.offset(), page.limit());
        return mapper.selectByExampleWithRowbounds(example, rowBounds);
    }

    @Override
    public Book getBook(long id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public Book create(Book entity) {
        mapper.insertSelective(entity);
        return entity;
    }

    @Override
    public Book update(Book entity) {
        mapper.updateByPrimaryKey(entity);
        return entity;
    }

    @Override
    public void delete(long id) {
        mapper.deleteByPrimaryKey(id);
    }
}
