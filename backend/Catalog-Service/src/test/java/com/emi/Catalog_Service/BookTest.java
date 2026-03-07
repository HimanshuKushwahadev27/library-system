package com.emi.Catalog_Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.TimeZone;
import java.util.UUID;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.emi.Catalog_Service.Entity.Book;
import com.emi.Catalog_Service.Entity.Book_Content;
import com.emi.Catalog_Service.Entity.Genre;
import com.emi.Catalog_Service.Repository.BookContentRepo;
import com.emi.Catalog_Service.Repository.BookRepository;
import com.emi.Catalog_Service.Repository.GenreRepo;
import com.emi.Catalog_Service.RequestDtos.RequestBookCreationDto;
import com.emi.Catalog_Service.RequestDtos.RequsestBookUpdateDto;
import com.emi.Catalog_Service.ResponseDtos.ResponseBookDto;
import com.emi.Catalog_Service.ResponseDtos.ResponseFullBookDto;
import com.emi.Catalog_Service.Services.BookContentService;
import com.emi.Catalog_Service.Snapshots.AuthorSnapshots;
import com.emi.Catalog_Service.Snapshots.GenreSnapshot;
import com.emi.Catalog_Service.enums.BookChapter_Status;
import com.emi.Catalog_Service.enums.BookLifeCycleStatus;
import com.emi.Catalog_Service.enums.BookVisibilityStatus;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;


@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment=SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BookTest {
	
	@MockitoBean
	private BookContentService contentService;
	
	@Autowired
	private BookContentRepo contentRepo;
	
	@Autowired
	private  BookRepository bookRepo;
	
	@Autowired
	private  GenreRepo genreRepo;

	@LocalServerPort
	private Integer port;
	
	private UUID genreId1;
	private UUID genreId2;
	private UUID bookId;
	private UUID authorId = UUID.randomUUID();
	
	@BeforeAll
	static void setUpZone() {
	    TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	}
	
	@BeforeEach
	void setUp() {
		RestAssured.baseURI="http://localhost";
		RestAssured.port=port;
	}
	
	@BeforeEach
	void setupVerify() {
	    when(contentService.deleteBookContentByBookId(any(), any()))
	            .thenReturn("Deleted");
	}
	
	@BeforeEach
	void globalSetUp() {
		contentRepo.deleteAll();
		genreRepo.deleteAll();
		bookRepo.deleteAll();
		
		Genre g1 =new Genre();
		g1.setName("Genre1");
		g1.setDescription("very well defined genre1 is not here");
		
		Genre g2 = new Genre();
		g2.setName("Genre2");
		g2.setDescription("very well defined genre2 is not here");
		
		genreRepo.saveAll(List.of(g1, g2));
		
		  genreId1 = g1.getId();
		  genreId2 = g2.getId();
		  
	   Book book = new Book();
	    book.setTitle("Spring Boot Internals");
	    book.setDescription("Deep dive into Spring Boot");
	    book.setISBN("9780132334584");
	    book.setPrice(new BigDecimal("499.99"));
	    book.setFreePreview(true);
	    book.setCreatedAt(Instant.now());
	    book.setUpdatedAt(Instant.now());
	    book.setStatusLifecycle(BookLifeCycleStatus.ONGOING);
	    book.setStatusVisible(BookVisibilityStatus.PUBLIC);
	    book.setTotalChapters(2);
	    book.setDeleted(false);
	
	    AuthorSnapshots author = new AuthorSnapshots();
	    author.setId(authorId);
	    author.setName("John Doe");
	
	    book.getAuthorSnapshots().add(author);
	
	    GenreSnapshot gs1 = new GenreSnapshot();
	    gs1.setId(g1.getId());
	    gs1.setName(g1.getName());
	
	    GenreSnapshot gs2 = new GenreSnapshot();
	    gs2.setId(g2.getId());
	    gs2.setName(g2.getName());
	
	    book.getGenreIds().add(gs1);
	    book.getGenreIds().add(gs2);
	
	    book = bookRepo.save(book);
	
	    bookId = book.getId();
	    
        Book_Content chapter1 = new Book_Content();
        chapter1.setBookId(bookId);
        chapter1.setTitle("Chapter 1");
        chapter1.setChapterNumber(1);
        chapter1.setPrice(new BigDecimal("10.00"));
        chapter1.setContent("This is chapter 1 content");
        chapter1.setFreePreview(true);
        chapter1.setStatus(BookChapter_Status.PUBLISHED);
        chapter1.setDeleted(false);
        chapter1.setCreatedAt(Instant.now());
        chapter1.setUpdatedAt(Instant.now());

        Book_Content chapter2 = new Book_Content();
        chapter2.setBookId(bookId);
        chapter2.setTitle("Chapter 2");
        chapter2.setChapterNumber(2);
        chapter2.setPrice(new BigDecimal("15.00"));
        chapter2.setContent("This is chapter 2 content");
        chapter2.setFreePreview(false);
        chapter2.setStatus(BookChapter_Status.PUBLISHED);
        chapter2.setDeleted(false);
        chapter2.setCreatedAt(Instant.now());
        chapter2.setUpdatedAt(Instant.now());
        
        contentRepo.saveAll(List.of(chapter1, chapter2));
	}
	
	@Test
	void shouldCreateBook() {
		   RequestBookCreationDto request = TestFactory.validBookRequest(genreId1, genreId2);

		    UUID bookId =
		            RestAssured
		                .given()
		                    .contentType(ContentType.JSON)
		                    .body(request)
		                .when()
		                    .post("/api/book/create")
		                .then()
		                    .statusCode(200)
		                    .extract()
		                    .as(UUID.class);

		  

		    assertNotNull(bookId);
		    assertTrue(bookRepo.findById(bookId).isPresent());
	}
	
	@Test
	void getBook_success() {

	    ResponseFullBookDto response =
	            RestAssured
	                .when()
	                .get("/api/book/{bookId}", bookId)
	                .then()
	                .statusCode(200)
	                .extract()
	                .as(ResponseFullBookDto.class);

	    assertEquals("Spring Boot Internals", response.title());
	    assertEquals("Deep dive into Spring Boot", response.description());
	    assertEquals("9780132334584", response.ISBN());
	    assertEquals(new BigDecimal("499.99"), response.price());
	    assertEquals(BookLifeCycleStatus.ONGOING, response.lifeCycleStatus());
	    assertEquals(BookVisibilityStatus.PUBLIC, response.visibilityStatus());
	    assertEquals(2, response.totalChapters());
	    assertTrue(response.freePreview());

	    assertEquals(1, response.authorIds().size());
	    assertEquals("John Doe", response.authorIds().get(0).getName());

	    assertEquals(2, response.genreIds().size());

	    List<String> genreNames =
	            response.genreIds()
	                    .stream()
	                    .map(GenreSnapshot::getName)
	                    .toList();

	    assertTrue(genreNames.contains("Genre1"));
	    assertTrue(genreNames.contains("Genre2"));
	}
	
	@Test
	void getBooksByIds_success() {

	    List<ResponseFullBookDto> response =
	            RestAssured
	                .given()
	                    .queryParam("bookIds", bookId)
	                .when()
	                    .get("/api/book")
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .jsonPath()
	                    .getList(".", ResponseFullBookDto.class);

	    assertEquals(1, response.size());

	    ResponseFullBookDto book = response.get(0);

	    assertEquals("Spring Boot Internals", book.title());
	    assertEquals("9780132334584", book.ISBN());
	    assertEquals(new BigDecimal("499.99"), book.price());
	}
	
	
	@Test
	void updateBook_success() {

	    RequsestBookUpdateDto request =
	            new RequsestBookUpdateDto(
	                    bookId,
	                    new BigDecimal("799.99"),
	                    "Updated advanced Spring Boot guide",
	                    true,
	                    BookLifeCycleStatus.COMPLETED,
	                    BookVisibilityStatus.PUBLIC
	            );

	    ResponseBookDto response =
	            RestAssured
	                .given()
	                    .contentType(ContentType.JSON)
	                    .body(request)
	                .when()
	                    .patch("/api/book/update/{authorId}", authorId)
	                .then()
	                    .statusCode(200)
	                    .extract()
	                    .as(ResponseBookDto.class);

	    assertEquals(bookId, response.bookId());
	    assertEquals(new BigDecimal("799.99"), response.price());
	    assertEquals("Updated advanced Spring Boot guide", response.description());
	    assertEquals(BookLifeCycleStatus.COMPLETED, response.lifeCycleStatus());
	    assertEquals(BookVisibilityStatus.PUBLIC, response.visibilityStatus());
	   
	    Book updatedBook = bookRepo.findById(bookId).orElseThrow();

	    assertEquals(new BigDecimal("799.99"), updatedBook.getPrice());
	    assertEquals("Updated advanced Spring Boot guide", updatedBook.getDescription());
	    assertEquals(BookLifeCycleStatus.COMPLETED, updatedBook.getStatusLifecycle());
	    assertEquals(BookVisibilityStatus.PUBLIC, updatedBook.getStatusVisible());
	    assertTrue(updatedBook.getFreePreview());
	    assertNotNull(updatedBook.getUpdatedAt());
	}
	
	
	@Test
	void deleteBook_success() {

	    RestAssured
	        .given()
	        .when()
	            .delete("/api/book/{bookId}/{authorId}", bookId, authorId)
	        .then()
	            .statusCode(200);
	    
	    verify(contentService)
        .deleteBookContentByBookId(bookId, authorId);
	    Book deletedBook = bookRepo.findById(bookId).orElseThrow();

	    assertTrue(deletedBook.isDeleted());
	    assertEquals(BookVisibilityStatus.DELETE, deletedBook.getStatusVisible());
	}
	
	@Test
	void deleteBook_notFound_shouldReturn404() {

	    RestAssured
	        .given()
	        .when()
	            .delete("/api/book/{bookId}/{authorId}",
	                    UUID.randomUUID(),
	                    authorId)
	        .then()
	            .statusCode(404);
	}
	
	@Test
	void deleteBook_wrongAuthor_shouldReturn410() {

	    UUID wrongAuthor = UUID.randomUUID();

	    RestAssured
	        .given()
	        .when()
	            .delete("/api/book/{bookId}/{authorId}",
	                    bookId,
	                    wrongAuthor)
	        .then()
	            .statusCode(410);
	}
	
	
	@Test
	void deleteBook_alreadyDeleted_shouldFail() {

	    RestAssured
	        .when()
	        .delete("/api/book/{bookId}/{authorId}", bookId, authorId)
	        .then()
	        .statusCode(200);
	    verify(contentService)
        .deleteBookContentByBookId(bookId, authorId);
	    RestAssured
	        .when()
	        .delete("/api/book/{bookId}/{authorId}", bookId, authorId)
	        .then()
	        .statusCode(410); 
	}
	
}
