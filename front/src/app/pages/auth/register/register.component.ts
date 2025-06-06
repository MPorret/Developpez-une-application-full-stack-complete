import { Component, OnDestroy } from '@angular/core';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { RegisterDTO } from 'src/app/interface/user.interface';
import { AuthService } from 'src/app/services/auth.service';
import { SessionService } from 'src/app/services/session.service';
import { SessionInformation } from 'src/app/interface/sessionInformation.interface';
import {MatCardModule} from '@angular/material/card';
import {MatFormFieldModule} from '@angular/material/form-field';
import { SubscribeButtonComponent } from 'src/app/commons/subscribe-button/subscribe-button.component';
import {MatInputModule} from '@angular/material/input';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
  imports: [MatCardModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, SubscribeButtonComponent, FormsModule]
})
export class RegisterComponent implements OnDestroy {
  public onError: boolean = false;
  subscription!: Subscription;

  public form = this.fb.group({
    name: [
      '',
      [
        Validators.required
      ]
    ],
    email: [
      '',
      [
        Validators.required,
        Validators.email
      ]
    ],
    password: [
      '',
      [
        Validators.required,
        Validators.pattern(new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!?])(?=\\S+$).{8,255}$"))
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
    const registerRequest = this.form.value as RegisterDTO;
    this.subscription = this.authService.register(registerRequest).subscribe({
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
