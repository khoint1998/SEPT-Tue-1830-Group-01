package com.rmit.sept.bk_bookservices;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.rmit.sept.bk_bookservices.model.Book;
import com.rmit.sept.bk_bookservices.services.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
public class bookController_test {

    private MockMvc mvc;
    @Autowired
    private WebApplicationContext wac;
    @Autowired
    private BookService bookService;

    @Test
    void createCopy() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("author");
        book.setPublisher("publisher");

        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();

        mvc = MockMvcBuilders.webAppContextSetup(wac).build();

        try {
            String url = "http://localhost:8081/bookeroo/books/create";
            String response = mvc.perform(post(url).contentType(MediaType.APPLICATION_JSON)
                    .content(objectWriter.writeValueAsString(book))).andReturn().getResponse().getContentAsString();
            assertNotNull(response);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBookByTitleAndISBN() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("author");
        book.setPublisher("publisher");
        bookService.createABook(book);

        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RequestBuilder request = get("http://localhost:8081/bookeroo/books/get/book/title-isbn?title=java&isbn=isbn");
        try {
            String response = mvc.perform(request).andReturn().getResponse().getContentAsString();
            String expected = "{\"bookId\":1,\"isbn\":\"isbn\",\"title\":\"java\",\"author\":\"author\",\"description\":\"description\",\"category\":\"category\",\"publisher\":\"publisher\",\"copies\":[]}";
            assertThat(response).isEqualTo(expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBooksByTitle() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("author");
        book.setPublisher("publisher");
        bookService.createABook(book);

        Book book2 = new Book();
        book2.setBookId(2L);
        book2.setTitle("java");
        book2.setIsbn("isbn2");
        book2.setCategory("category2");
        book2.setDescription("description2");
        book2.setAuthor("author2");
        book2.setPublisher("publisher2");

        bookService.createABook(book2);

        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RequestBuilder request = get("http://localhost:8081/bookeroo/books/get/books/title?title=JAVA");
        try {
            String response = mvc.perform(request).andReturn().getResponse().getContentAsString();
            String expected = "[{\"bookId\":1,\"isbn\":\"isbn\",\"title\":\"java\",\"author\":\"author\",\"description\":\"description\",\"category\":\"category\",\"publisher\":\"publisher\",\"copies\":[]},{\"bookId\":2,\"isbn\":\"isbn2\",\"title\":\"java\",\"author\":\"author2\",\"description\":\"description2\",\"category\":\"category2\",\"publisher\":\"publisher2\",\"copies\":[]}]";
            assertThat(response).isEqualTo(expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBooksByAuthor() {

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("chen");
        book.setPublisher("publisher");
        bookService.createABook(book);

        Book book2 = new Book();
        book2.setBookId(2L);
        book2.setTitle("java");
        book2.setIsbn("isbn2");
        book2.setCategory("category2");
        book2.setDescription("description2");
        book2.setAuthor("chen");
        book2.setPublisher("publisher2");

        bookService.createABook(book2);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RequestBuilder request = get("http://localhost:8081/bookeroo/books/get/books/author?author=chen");
        try {
            String response = mvc.perform(request).andReturn().getResponse().getContentAsString();
            String expected = "[{\"bookId\":1,\"isbn\":\"isbn\",\"title\":\"java\",\"author\":\"chen\",\"description\":\"description\",\"category\":\"category\",\"publisher\":\"publisher\",\"copies\":[]},{\"bookId\":2,\"isbn\":\"isbn2\",\"title\":\"java\",\"author\":\"chen\",\"description\":\"description2\",\"category\":\"category2\",\"publisher\":\"publisher2\",\"copies\":[]}]";
            assertThat(response).isEqualTo(expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBookByIsbn() {

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("author");
        book.setPublisher("publisher");
        bookService.createABook(book);

        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RequestBuilder request = get("http://localhost:8081/bookeroo/books/get/book/isbn?isbn=isbn");
        try {
            String response = mvc.perform(request).andReturn().getResponse().getContentAsString();
            String expected = "{\"bookId\":1,\"isbn\":\"isbn\",\"title\":\"java\",\"author\":\"author\",\"description\":\"description\",\"category\":\"category\",\"publisher\":\"publisher\",\"copies\":[]}";
            assertThat(response).isEqualTo(expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBooksByIdList() {

        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("chen");
        book.setPublisher("publisher");
        bookService.createABook(book);

        Book book2 = new Book();
        book2.setBookId(2L);
        book2.setTitle("java");
        book2.setIsbn("isbn2");
        book2.setCategory("category2");
        book2.setDescription("description2");
        book2.setAuthor("chen");
        book2.setPublisher("publisher2");

        List<Long> bookIdList = new ArrayList<>();
        bookIdList.add(1L);
        bookIdList.add(2L);
        ObjectWriter objectWriter = new ObjectMapper().writer().withDefaultPrettyPrinter();
        bookService.createABook(book2);
        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        String url = "http://localhost:8081/bookeroo/books/get/book/bookIdList";
        try {
            String response = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON)
                    .content(objectWriter.writeValueAsString(bookIdList))).andReturn().getResponse().getContentAsString();
            String expected = "[{\"bookId\":1,\"isbn\":\"isbn\",\"title\":\"java\",\"author\":\"chen\",\"description\":\"description\",\"category\":\"category\",\"publisher\":\"publisher\",\"copies\":[]},{\"bookId\":2,\"isbn\":\"isbn2\",\"title\":\"java\",\"author\":\"chen\",\"description\":\"description2\",\"category\":\"category2\",\"publisher\":\"publisher2\",\"copies\":[]}]";
            assertThat(response).isEqualTo(expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void getBooksByTitleAuthorISBN() {
        Book book = new Book();
        book.setBookId(1L);
        book.setTitle("java");
        book.setIsbn("isbn");
        book.setCategory("category");
        book.setDescription("description");
        book.setAuthor("chen");
        book.setPublisher("publisher");
        bookService.createABook(book);

        mvc = MockMvcBuilders.webAppContextSetup(wac).build();
        RequestBuilder request = get("http://localhost:8081/bookeroo/books/get/book/title-author-isbn?searchResult=isbn");
        try {
            String response = mvc.perform(request).andReturn().getResponse().getContentAsString();
            String expected = "[{\"bookId\":1,\"isbn\":\"isbn\",\"title\":\"java\",\"author\":\"chen\",\"description\":\"description\",\"category\":\"category\",\"publisher\":\"publisher\",\"copies\":[]}]";
            assertThat(response).isEqualTo(expected);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
