CREATE EXTENSION IF NOT EXISTS pgcrypto;

--BOOK
CREATE TABLE books(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	title VARCHAR(26) NOT NULL,
	description VARCHAR(2000) NOT NULL,
	isbn VARCHAR(15) NOT NULL,
	price NUMERIC(10,2) NOT NULL,
	free_preview BOOLEAN NOT NULL,
	isDeleted BOOLEAN DEFAULT FALSE
);

--GENRE 
CREATE TABLE genres(
	id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
	name VARCHAR(100) NOT NULL,
	description VARCHAR(2000) NOT NULL
);

--BOOK_GENRE  --GENRE SNAPSHOT 
CREATE TABLE book_genres(
	genre_id UUID NOT NULL,
	book_id UUID NOT NULL,
	name VARCHAR(100) NOT NULL,
	
		PRIMARY KEY(genre_id , book_id),
		CONSTRAINT fk_book_genre_book
			FOREIGN KEY(book_id) REFERENCES books(id)
);

--BOOK_AUTHORS --AUTHOR SNAPSHOT
CREATE TABLE book_authors(
	author_id UUID NOT NULL,
	author_name VARCHAR(100) NOT NULL,
	book_id UUID NOT NULL,
	
		PRIMARY KEY (book_id,author_id),
		CONSTRAINT fk_book_author_book
			FOREIGN KEY(book_id) REFERENCES books(id)
);


--BOOK_CONTENT
CREATE TABLE book_contents(
    id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    book_id UUID NOT NULL,
	title VARCHAR(300) NOT NULL,
	chapterNumber INT NOT NULL,
	price NUMERIC(10,2) NOT NULL,
	free_preview BOOLEAN NOT NULL,
	content TEXT NOT NULL,
	
		CONSTRAINT fk_book_contents_book
			FOREIGN KEY(book_id) REFERENCES books(id)
);
