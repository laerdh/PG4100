package no.westerdals.PG4100.innlevering2.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;


@DatabaseTable(tableName = "bokliste")
public class Book {
    private static final String AUTHOR_FIELD_NAME = "forfatter";
    private static final String TITLE_FIELD_NAME = "tittel";
    private static final String ISBN_FIELD_NAME = "ISBN";
    private static final String PAGES_FIELD_NAME = "sider";
    private static final String RELEASED_FIELD_NAME = "utgitt";

    @DatabaseField(columnName = AUTHOR_FIELD_NAME)
    private String author;

    @DatabaseField(columnName = TITLE_FIELD_NAME)
    private String title;

    @DatabaseField(id = true, columnName = ISBN_FIELD_NAME)
    private String isbn;

    @DatabaseField(columnName = PAGES_FIELD_NAME)
    private int pages;

    @DatabaseField(columnName = RELEASED_FIELD_NAME)
    private int release_date;

    public Book() {

    }

    public Book(String author, String title, String isbn, int pages, int release_date) {
        setAuthor(author);
        setTitle(title);
        setIsbn(isbn);
        setPages(pages);
        setReleaseDate(release_date);
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setReleaseDate(int release_date) {
        this.release_date = release_date;
    }

    public String getAuthor() {
        return author;
    }

    public String getTitle() {
        return title;
    }

    public String getIsbn() {
        return isbn;
    }

    public int getPages() {
        return pages;
    }

    public int getReleaseDate() {
        return release_date;
    }

    public boolean equals(Object other) {
        if (!(other instanceof Book))
            return false;

        if (this == other)
            return true;

        Book b = (Book) other;
        return this.getIsbn().equals(((Book) other).getIsbn());
    }

}
