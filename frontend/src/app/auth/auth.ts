import { Component, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AuthService } from './auth.service';

@Component({
  selector: 'app-auth',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './auth.html',
  styleUrl: './auth.scss',
})
export class Auth {

   private authService = inject(AuthService);

   isLoggedIn = () => this.authService.isLoggedIn();
   userName = () => this.authService.getUserName();

   login(){
    this.authService.login();
   }

   logout(){
    this.authService.logout();
   }

   register(){
    this.authService.register();
   }
}
