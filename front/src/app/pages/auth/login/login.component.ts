import { Component } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { Subscription } from 'rxjs';
import { SessionInformation } from 'src/app/interface/sessionInformation.interface';
import { LoginDTO } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { SubscribeButtonComponent } from "../../../commons/subscribe-button/subscribe-button.component";
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';

@Component({
  selector: 'app-login',
  imports: [SubscribeButtonComponent, MatCardModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.scss'
})
export class LoginComponent {
  public onError: boolean = false;
  subscription!: Subscription;

  public form = this.fb.group({
    username: [
      '',
      [
        Validators.required
      ]
    ],
    password: [
      '',
      [
        Validators.required,
      ]
    ]
  });

  constructor(
    private authService: AuthService,
    private fb: FormBuilder,
    private router: Router,
    private sessionService: SessionService
  ) {}

  public onSubmit(): void{
    const loginRequest = this.form.value as LoginDTO;
    this.subscription = this.authService.login(loginRequest).subscribe({
      next: (user: SessionInformation) => {
        this.sessionService.logIn(user);
        this.router.navigate(['topics']);
      },
      error: error => this.onError = true,
    });
  }

  ngOnDestroy(): void {
    if(this.subscription){
        this.subscription.unsubscribe();
    }
  }

}
