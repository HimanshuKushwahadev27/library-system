import { Component} from '@angular/core';
import { AuthService } from '../../core/auth/service/auth.service';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import {MatMenuModule} from '@angular/material/menu';
import {MatDividerModule} from '@angular/material/divider';

@Component({
  selector: 'app-auth-button',
    standalone: true,
  imports: [MatIconModule, MatButtonModule , MatMenuModule, MatDividerModule],
  templateUrl: './auth-button.component.html',
  styleUrl: './auth-button.component.scss',
})
export class AuthButtonComponent {

  constructor(public authService: AuthService) {}

  login(){
    this.authService.login();
  }

  logout(){
    this.authService.logout();
  }

}
