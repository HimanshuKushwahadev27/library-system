import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { GenreCreate, GenreResponse } from '../../../interface/Catalog/genre/genre.catalog.interface';
import { Observable } from 'rxjs';
import { UUID } from 'crypto';

@Injectable({
  providedIn: 'root',
})
export class GenrecatalogService {
  
  private http = inject(HttpClient);

  createGenre(request : GenreCreate): Observable<GenreResponse>{
   return  this.http.post<GenreResponse>('/api/book/genres/create', request);
  }

  getAllGenres$ = this.http.get<GenreResponse[]>('/api/book/genres/allGenres');


  getGenreById(genreId: UUID): Observable<GenreResponse>{
    return this.http.get<GenreResponse>(`/api/book/genreId/${genreId}`);
  }

  getGenreByName(genreName: string): Observable<GenreResponse>{
    return this.http.get<GenreResponse>(`/api/books/genres/name/${genreName}`);
  }

}
