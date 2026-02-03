
--BOOK RELATED
CREATE UNIQUE INDEX idx_books_isbn ON books(isbn);

--GENRE RELATED
CREATE UNIQUE INDEX idx_genre_name ON genres(name);

--GENRE SNAPSHOT RELATED
CREATE INDEX idx_book_genres_genre_id ON book_genres(genre_id);


--AUTHOR SNAPSHOT RELATED
CREATE INDEX idx_book_authors_author_id ON book_authors(author_id);

--BOOK_CONTENT RELATED
CREATE INDEX idx_book_contents_book_id ON book_contents(book_id);
CREATE UNIQUE INDEX idx_book_contents_book_chapter
	ON book_contents(book_id, chapterNumber);