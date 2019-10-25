package fondue.gen;

import java.util.List;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;

public final class MyBatisMapperXmlBean extends BaseBean {

    private ResultMap resultMap;

    public ResultMap getResultMap() {
        return resultMap;
    }

    public void setResultMap(ResultMap resultMap) {
        this.resultMap = resultMap;
    }

    /**
     * An element ("resultMap") in MyBatis Mapper XML.
     */
    public static final class ResultMap extends BaseBean {

        private String type;
        private Result id;
        private List<Result> results;

        @XmlAttribute
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public Result getId() {
            return id;
        }

        public void setId(Result id) {
            this.id = id;
        }

        @XmlElement(name = "result")
        public List<Result> getResults() {
            return results;
        }

        public void setResults(List<Result> results) {
            this.results = results;
        }

    }

    /**
     * An element ("resultMap") in MyBatis Mapper XML.
     */
    public static final class Result extends BaseBean {

        private String column;
        private String jdbcType;
        private String property;

        @XmlAttribute
        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        @XmlAttribute
        public String getJdbcType() {
            return jdbcType;
        }

        public void setJdbcType(String jdbcType) {
            this.jdbcType = jdbcType;
        }

        @XmlAttribute
        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public Class<?> getJavaType() {
            return SqlTypes.toClass(jdbcType);
        }
    }
}
