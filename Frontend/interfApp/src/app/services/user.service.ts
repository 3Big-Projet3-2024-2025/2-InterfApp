import { HttpClient } from '@angular/common/http';
import { Inject, Injectable } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { jwtDecode } from 'jwt-decode';
import { CookieService } from 'ngx-cookie-service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private apiUrl = 'http://localhost:8080/api/users';

  constructor(private http: HttpClient, @Inject(CookieService) private cookieService : CookieService) { }

  get tokenJWT() : any{
    if(this.cookieService.get('jwt') == ""){
    return "";
    }
    return jwtDecode(this.cookieService.get('jwt'));
  }

  isAuthenticated() :boolean{
    return this.tokenJWT != "" ? true : false;
  }

  hasRole( role: string): boolean {
    if (!this.isAuthenticated){
      return false;
    }
    return this.tokenJWT.roles.includes(role);
  }

  register(userData: any): Observable<any> {
    return this.http.post(this.apiUrl, userData);
  }

  login(userData: any): Observable<any> {

    return this.http.post(this.apiUrl+"/login", userData);
  }

  saveJwt(jwt: string, rememberMe: boolean) {
    const expirationTime = rememberMe ? 7 : 0.02083; // 30 minutes in days
    this.cookieService.set('jwt', jwt, expirationTime, '/');
  }


  isTokenExpired(token: string): boolean {
    if (!this.tokenJWT || !this.tokenJWT.exp) {
      return true; // Si le token ne contient pas d'expiration, on considère qu'il est expiré
    }
    const currentTime = Math.floor(Date.now() / 1000); // Temps actuel en secondes
    return this.tokenJWT.exp < currentTime;
  }

  logout():void{
    this.cookieService.delete('jwt','/');
    console.log("User logout");
  }


}

