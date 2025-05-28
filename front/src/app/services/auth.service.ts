import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { RegisterDTO, User } from '../interface/user.interface';
import { Observable } from 'rxjs';
import { SessionInformation } from '../interface/sessionInformation.interface';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private pathService = "/api/auth";

  constructor(private httpClient: HttpClient) { }

  public register(register: RegisterDTO): Observable<SessionInformation> {
      return this.httpClient.post<SessionInformation>(`${this.pathService}/register`, register);
    }
}
