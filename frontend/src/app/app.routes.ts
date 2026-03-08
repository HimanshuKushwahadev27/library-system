import { Routes } from '@angular/router';

export const routes: Routes = [
 {path : 'genres', 
  loadComponent: () =>   import('./features/catalog/pages/genre-page/genre-page.component')
      .then(m => m.GenrePageComponent)
 }
];
