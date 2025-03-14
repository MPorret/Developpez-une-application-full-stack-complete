import { Component, OnInit } from '@angular/core';
import { User } from 'src/app/interfaces/user.interface';
import { AuthService } from './auth/services/auth.service';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = 'front';
  constructor(
    private authService: AuthService,
    private sessionService: SessionService,
  ){}

  public ngOnInit(): void {
    this.autoLog();
  }

  public autoLog(): void {
    // this.authService.me().subscribe(
    //   (user: User) => {
    //     this.sessionService.logIn(user);
    //   },
    //   (_) => {
    //     this.sessionService.logOut();
    //   }
    // )
    const token = localStorage.getItem('token');
    if (token) {
      this.sessionService.isLogged = true;
    }
  }
}
