import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { SessionService } from "../services/session.service";
import { map, Observable, tap } from "rxjs";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private sessionService: SessionService,
  ) {
  }

  public canActivate(): Observable<boolean> {
    return this.sessionService.tokenIsValid().pipe(
      tap(isValid => {
        if (!isValid) {
          this.router.navigate(['']);
        }
      }),
      map(isValid => isValid)
    );
  }
}