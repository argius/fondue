package app.controller;

import javax.validation.constraints.*;
import fondue.fw.FormMethods;
import app.model.Book;

public final class BookForm implements FormMethods {

    private String id;
    private String created;
    private String updated;
    private String disabled;
    @Size(message = "{title.Size}", min = 1)
    private String title;
    private String author;
    @Pattern(message = "{bookNumber.Pattern}", regexp = "^\\d*$")
    @Size(message = "{bookNumber.Size}", max = 16)
    private String bookNumber;
    private String summary;
    private String note;

    public String getId() {
        return this.id;
    }

    public void setId(String value) {
        this.id = value;
    }

    public String getCreated() {
        return this.created;
    }

    public void setCreated(String value) {
        this.created = value;
    }

    public String getUpdated() {
        return this.updated;
    }

    public void setUpdated(String value) {
        this.updated = value;
    }

    public String getDisabled() {
        return this.disabled;
    }

    public void setDisabled(String value) {
        this.disabled = value;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String value) {
        this.title = value;
    }

    public String getAuthor() {
        return this.author;
    }

    public void setAuthor(String value) {
        this.author = value;
    }

    public String getBookNumber() {
        return this.bookNumber;
    }

    public void setBookNumber(String value) {
        this.bookNumber = value;
    }

    public String getSummary() {
        return this.summary;
    }

    public void setSummary(String value) {
        this.summary = value;
    }

    public String getNote() {
        return this.note;
    }

    public void setNote(String value) {
        this.note = value;
    }

    public void applyModel(Book model) {
        id = convertFrom(java.lang.Long.class, model.getId());
        created = convertFrom(java.sql.Timestamp.class, model.getCreated());
        updated = convertFrom(java.sql.Timestamp.class, model.getUpdated());
        disabled = convertFrom(java.lang.Short.class, model.getDisabled());
        title = convertFrom(java.lang.String.class, model.getTitle());
        author = convertFrom(java.lang.String.class, model.getAuthor());
        bookNumber = convertFrom(java.lang.String.class, model.getBookNumber());
        summary = convertFrom(java.lang.String.class, model.getSummary());
        note = convertFrom(java.lang.String.class, model.getNote());
    }

    public Book toModel() {
        Book o = new Book();
        o.setId(convertTo(java.lang.Long.class, this.id));
        o.setCreated(convertTo(java.sql.Timestamp.class, this.created));
        o.setUpdated(convertTo(java.sql.Timestamp.class, this.updated));
        o.setDisabled(convertTo(java.lang.Short.class, this.disabled));
        o.setTitle(convertTo(java.lang.String.class, this.title));
        o.setAuthor(convertTo(java.lang.String.class, this.author));
        o.setBookNumber(convertTo(java.lang.String.class, this.bookNumber));
        o.setSummary(convertTo(java.lang.String.class, this.summary));
        o.setNote(convertTo(java.lang.String.class, this.note));
        return o;
    }

    @Override
    public String toString() {
        return toString(this);
    }
}
