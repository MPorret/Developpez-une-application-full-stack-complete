import {Injectable} from "@angular/core";
import {CanActivate, Router} from "@angular/router"; 
import { SessionService } from "../services/session.service";
import { map, Observable, of, tap } from "rxjs";

@Injectable({providedIn: 'root'})
export class UnauthGuard implements CanActivate {

  constructor( 
    private router: Router,
    private sessionService: SessionService,
  ) {
  }

  public canActivate(): Observable<boolean> {
    return this.sessionService.tokenIsValid().pipe(
      tap(isValid => {
        if (isValid) {
          this.router.navigate(['topics']);
        }
      }),
      map(isValid => !isValid)
    );
  }
}