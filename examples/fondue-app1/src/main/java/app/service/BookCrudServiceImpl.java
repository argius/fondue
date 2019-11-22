package app.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import fondue.fw.PP;
import app.dao.BookDao;
import app.dao.BookMapper;
import app.model.Book;

@Component
public class BookCrudServiceImpl implements BookCrudService {

    @Autowired
    private BookMapper mapper;

    @Autowired
    private BookDao dao;

    @Override
    public List<Book> getBooks(PP page) {
        String q = page.getQuery();
        List<String> keywords = (q == null) ? Collections.emptyList() : Arrays.asList(q.split("\\s"));
        page.setTotalCount(dao.countByKeywords(keywords));
        RowBounds rowBounds = new RowBounds(page.offset(), page.limit());
        return dao.selectByKeywordsWithRowbounds(keywords, rowBounds);
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
