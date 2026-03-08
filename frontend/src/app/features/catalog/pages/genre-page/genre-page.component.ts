import { Component, inject } from '@angular/core';
import { GenrecatalogService } from '../../../../core/services/catalog/genre-service/genrecatalog.service';
import { AsyncPipe } from '@angular/common';

@Component({
  selector: 'app-genre-page',
  standalone: true,
  imports: [AsyncPipe],
  templateUrl: './genre-page.component.html',
  styleUrl: './genre-page.component.scss',
})
export class GenrePageComponent  {

  public genreService = inject(GenrecatalogService);

  genres$ = this.genreService.getAllGenres$;
}
