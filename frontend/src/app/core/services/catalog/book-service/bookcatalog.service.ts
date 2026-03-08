import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { UUID } from 'crypto';
import { GetBook, UpdateBookRequest } from '../../../interface/Catalog/book/book.catalog.interface';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class BookcatalogService {
  
  private http = inject(HttpClient);

  getSingleBook(bookId : UUID): Observable<GetBook>{
    return this.http.get<GetBook>(`/api/book/${bookId}`);
  }

  getMultipleBooks(bookIds: UUID[]): Observable<GetBook[]>{
    const params = new HttpParams({
      fromObject:{
        bookIds: bookIds
      }
    })
    return this.http.get<GetBook[]>(`/api/book`, {params});
  }

  updateBook(request : UpdateBookRequest, authorId: UUID): Observable<GetBook>{
    return this.http.patch<GetBook>(`/api/book/update/${authorId}`, request);
  }

  deleteBook(authorId: UUID , bookId:UUID): Observable<string>{
    return this.http.delete<string>(`/api/book/${bookId}/${authorId}`);
  }
}
