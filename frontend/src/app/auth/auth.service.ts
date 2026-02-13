import { Injectable, inject } from '@angular/core';
import { OAuthService } from 'angular-oauth2-oidc';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private oauthService = inject(OAuthService);

  login() {
    this.oauthService.initCodeFlow();
  }

  register() {
     this.oauthService.initCodeFlow(undefined, {
    kc_action: 'register'
  });
  }

  logout() {
    this.oauthService.logOut();
  }

  isLoggedIn(): boolean {
    return this.oauthService.hasValidAccessToken();
  }

  getUserName(): string | null {
    const claims: any = this.oauthService.getIdentityClaims();
    return claims?.preferred_username ?? null;
  }
}
