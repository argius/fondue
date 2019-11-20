package app.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.jdbc.SQL;
import org.apache.ibatis.session.RowBounds;
import app.model.Book;

@Mapper
public interface BookDao {

    @SelectProvider(type = SqlProvider.class, method = "countBy")
    long countByKeywords(@Param("keywords") List<String> keywords);

    @SelectProvider(type = SqlProvider.class, method = "findBy")
    @ResultMap("app.dao.BookMapper.BaseResultMap")
    List<Book> selectByKeywordsWithRowbounds(@Param("keywords") List<String> keywords, RowBounds rowBounds);

    public static final class SqlProvider {
        public SqlProvider() {
        }
        public String countBy(@Param("keywords") List<String> keywords) {
            return new SqlBuilder(keywords, true).toString();
        }
        public String findBy(@Param("keywords") List<String> keywords) {
            return new SqlBuilder(keywords, false).toString();
        }
    }

    static final class SqlBuilder extends SQL {
        SqlBuilder(List<String> keywords, boolean asCountStatement) {
            SELECT(getColumns(asCountStatement));
            FROM("app1.books");
            WHERE("disabled = '0'");
            for (int i = 0, n = keywords.size(); i < n; i++) {
                WHERE("(title like '%'||#{keywords[" + i + "]}||'%' or author like '%'||#{keywords[" + i + "]}||'%')");
            }
            WHERE("1 = 1");
            if (!asCountStatement) {
                ORDER_BY("updated desc");
            }
        }
        String[] getColumns(boolean asCountStatement) {
            if (asCountStatement) {
                return new String[] {"count(*)"};
            }
            return new String[] {
                "ID",
                "CREATED",
                "UPDATED",
                "DISABLED",
                "TITLE",
                "AUTHOR",
                "BOOK_NUMBER",
                "SUMMARY",
                "NOTE",
            };
        }
    }
}
