import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { User } from 'src/app/interfaces/user.interface';
import { SessionService } from 'src/app/services/session.service';
import { AuthSuccess } from '../../interfaces/authSuccess.interface';
import { LoginRequest } from '../../interfaces/loginRequest.interface';
import { AuthService } from '../../services/auth.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  public hide = true;
  public onError = false;
  public form!: FormGroup;
  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  ngOnInit(): void {
    this.form = this.fb.group({
      identifier: ['', [Validators.required]],
      password: ['', [Validators.required, Validators.minLength(3)]]
    });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  public submit(): void {
    if (this.form.invalid) {
      this.onError = true;
      return;
    }

    const loginRequest = this.form.value as LoginRequest;
    this.authService.login(loginRequest).pipe(takeUntil(this.destroy$)).subscribe(
      (response: AuthSuccess) => {
        localStorage.setItem('token', response.token);
        this.authService.me().pipe(takeUntil(this.destroy$)).subscribe(
          (user: User) => {
            this.sessionService.logIn(user);
            this.router.navigate(['/posts']);
          },
          error => {
            console.error('Error fetching user details:', error);
          }
        );
        this.router.navigate(['/posts']);
      },
      error => {
        console.error('Login failed:', error);
        this.onError = true;
      }
    );
  }
}
