package com.example.demo.repository;


import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.example.demo.domain.Book;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;



@Repository
public class BookRepositoryImpl implements BookRepository{
  

  @Autowired
  private final JdbcTemplate jdbcTemplate;

  @Autowired
  public BookRepositoryImpl(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }


  @Override
  public List<Book> findAll() {

    String sql = "SELECT book.id, title, author, publisher, buy_date, release_date, over_view FROM Book";

    return jdbcTemplate.query(sql,new BeanPropertyRowMapper<Book>(Book.class));
  }


    @Override
    public Optional<Book> findOne(int id) {
      
      String sql = "SELECT book.id, title, author, publisher, buy_date, release_date, over_view FROM book "
        + "WHERE id = ?";
        
      Map<String, Object> result = jdbcTemplate.queryForMap(sql, id);

        Book book = new Book();
        book.setId((int)result.get("id"));
        book.setTitle((String)result.get("title"));
        book.setAuthor((String)result.get("author"));
        book.setPublisher((String)result.get("publisher"));
        book.setBuyDate((Date)result.get("buy_date"));
        book.setReleaseDate((Date)result.get("release_date"));
        book.setOverView((String)result.get("over_view"));
        System.out.println(result);
        //bookをOptionalでラップする
        Optional<Book> bookOpt = Optional.ofNullable(book);
        return bookOpt;
	}



      @Override
      public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, publisher, buy_date, release_date, over_view) VALUES(?, ?, ?, ?, ?, ?)",
        book.getTitle(), book.getAuthor(), book.getPublisher(), book.getBuyDate(), book.getReleaseDate(), book.getOverView() );
      }
      
      @Override
      public int update(Book book) {
        return 	jdbcTemplate.update("UPDATE book SET (title, author, publisher, buy_date, release_date, over_view) VALUES(?, ?, ?, ?, ?, ?)  WHERE id = ? ",
        book.getTitle(), book.getAuthor(), book.getPublisher(), book.getBuyDate(), book.getReleaseDate(), book.getOverView() );
    }

      @Override
      public int delete(int id) {
        return jdbcTemplate.update("DELETE FROM book WHERE id = ?", id);
      }

    }