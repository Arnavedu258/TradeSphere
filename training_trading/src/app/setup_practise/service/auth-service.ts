import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { environment } from '../../../environment/environment';
@Injectable({
  providedIn: 'root'
})
export class AuthService {
      private API = environment.authApi;

  constructor(private http: HttpClient) {}

  login(body:any){
    return this.http.post(`${this.API}/getaccess`, body);
  }

  register(body:any){
    return this.http.post(`${this.API}/register`, body);
  }

  googleLogin(){
    window.location.href = `${this.API}/oauth2/authorization/google`;
  }
}
