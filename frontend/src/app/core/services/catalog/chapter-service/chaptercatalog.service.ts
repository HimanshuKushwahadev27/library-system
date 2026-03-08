import { HttpClient, HttpParams } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { UUID } from 'crypto';
import { Observable } from 'rxjs';
import { GetChapter } from '../../../interface/Catalog/chapter/chapter.catalog.interface';

@Injectable({
  providedIn: 'root',
})
export class ChaptercatalogService {
  
  private http = inject(HttpClient);

    getChaptersByIds(chapterIds: UUID[]): Observable<GetChapter[]>{
      const params = new HttpParams({
        fromObject:{
          chapterIds: chapterIds
        }
      })
      return this.http.get<GetChapter[]>(`/api/book/contents/ContentIds`, {params});
    }
  
    getMultipleChaptersByBookID(bookId : UUID): Observable<GetChapter[]>{
      return this.http.get<GetChapter[]>(`/api/book/contents/bookId/${bookId}`);
    }

    deleteChaptersByIds(chapterIds: UUID[], authorId: UUID){
      const params = new HttpParams({
        fromObject:{
          chapterIds: chapterIds
        }
      })
     return this.http.delete<string>(`/api/bookcontents/contentIds/${authorId}`, {params});
    }

    deleteByBookId(authorId: UUID , bookId:UUID): Observable<string>{
    return this.http.delete<string>(`/api/book/contents/${bookId}/${authorId}`);
  }
}
