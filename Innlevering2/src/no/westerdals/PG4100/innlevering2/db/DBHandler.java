package no.westerdals.PG4100.innlevering2.db;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import no.westerdals.PG4100.innlevering2.model.Book;

import java.sql.SQLException;
import java.util.List;


public class DBHandler implements AutoCloseable {
    private String dbURL = "jdbc:mysql://localhost/pg4100innlevering2";
    private String username = "root";
    private String password = "root";
    private ConnectionSource connection;
    private Dao<Book, String> bookDao;

    public DBHandler() throws SQLException {
        connection = new JdbcConnectionSource(dbURL, username, password);
        bookDao = DaoManager.createDao(connection, Book.class);
    }

    public List<Book> getAll() throws SQLException {
        return bookDao.queryForAll();
    }

    public void close() {
        try {
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

