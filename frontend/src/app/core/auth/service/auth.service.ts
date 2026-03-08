import {  computed, Injectable, signal } from '@angular/core';
import {  OAuthService } from 'angular-oauth2-oidc';
import { authConfig } from '../auth.config';

@Injectable({
  providedIn: 'root',
})

export class AuthService {
  

  isAuthenticated = signal(false);

  
  constructor(private oauthService: OAuthService) {
  }

    initLogin() {

    this.oauthService.configure(authConfig);

    this.oauthService.setupAutomaticSilentRefresh();

    this.oauthService.loadDiscoveryDocumentAndTryLogin();
    this.oauthService.loadDiscoveryDocumentAndTryLogin().then(() => {
      this.updateAuthState();
    });

    this.oauthService.events.subscribe(() => {
      this.updateAuthState();
    });
  }

    private updateAuthState() {
    this.isAuthenticated.set(
      this.oauthService.hasValidAccessToken()
    );
  }

  login(){
    this.oauthService.initCodeFlow();
  }

  logout(){
    this.oauthService.logOut();
  }

  getToken(){
    return this.oauthService.getAccessToken();
  }

  isLoggedIn(){
    return this.oauthService.hasValidAccessToken();
  }

  getUser() {
    const claims: any = this.oauthService.getIdentityClaims();
    console.log(claims);
  }

}
