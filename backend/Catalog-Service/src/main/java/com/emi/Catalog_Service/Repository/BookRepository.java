package com.emi.Catalog_Service.Repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emi.Catalog_Service.Entity.Book;

public interface BookRepository extends JpaRepository<Book, UUID> {

	
}
