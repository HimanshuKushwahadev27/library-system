import { Component } from '@angular/core';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MatIconModule } from '@angular/material/icon';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatButtonModule } from '@angular/material/button';
import { FormControl, ReactiveFormsModule } from '@angular/forms';
import { MatAutocompleteModule } from '@angular/material/autocomplete';

import { AuthButtonComponent } from '../auth-button/auth-button.component';
import { RouterLink} from '@angular/router';

@Component({
  selector: 'app-header',
    standalone: true,
  imports: [
     AuthButtonComponent,
     MatIconModule,
     MatInputModule,
     MatFormFieldModule,
     MatToolbarModule,
     MatButtonModule,
     ReactiveFormsModule,
     MatAutocompleteModule,
     RouterLink
  ],
  templateUrl: './header.component.html',
  styleUrl: './header.component.scss',
})
export class HeaderComponent {

  searchControl = new FormControl('');
  results: any[] = [];

}
