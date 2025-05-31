import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { RegisterDTO, User } from 'src/app/interface/user.interface';
import { UserService } from 'src/app/services/user.service';
import { BackButtonComponent } from "../../commons/back-button/back-button.component";
import { SubscribeButtonComponent } from "../../commons/subscribe-button/subscribe-button.component";
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { SessionService } from 'src/app/services/session.service';
import {MatDividerModule} from '@angular/material/divider';
import { CardComponent } from "../../commons/card/card.component";
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-profile',
  imports: [BackButtonComponent, MatCardModule, MatDividerModule, MatFormFieldModule, MatInputModule, ReactiveFormsModule, SubscribeButtonComponent, FormsModule, CardComponent],
  templateUrl: './profile.component.html',
  styleUrl: './profile.component.scss'
})
export class ProfileComponent implements OnInit, OnDestroy {
  public user!: User | undefined;
  private subscription!: Subscription;
  public onError: boolean = false;
  public onSuccess: boolean = false;

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
              Validators.pattern(new RegExp("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&-+=()!?])(?=\\S+$).{8,255}$")),
            ]
          ]
        });

  constructor(
    private fb: FormBuilder,
    private userService: UserService,
    private sessionService: SessionService,
    private topicService: TopicService) {}

  public onSubmit(): void{
    const updateRequest = this.form.value as RegisterDTO;
    this.subscription = this.userService.updateUserInfo(updateRequest).subscribe({
      next: (user: User) => {
        this.sessionService.logIn({token: user.token});
        localStorage.setItem('token', user.token);
        this.form.setValue({
          name: user.name || '',
          email: user.email || '',
          password: ''
        })
        this.onSuccess = true;
        setTimeout(() => {
          this.onSuccess = false;
        }
        , 3000);
      },
      error: error => this.onError = true,
    });
  }

  public onUnsubscribe(topicId: number): void {
    this.subscription = this.topicService.unsubscribe(topicId).subscribe({
      next: (data) => {
        this.user!.topics = data;
      },
      error: (err) => {
        console.error('Unsubscription failed', err);
      },
    });
  }

  public handleOnUnsubscribe(topicId: number): () => void {
    return () => this.onUnsubscribe(topicId);
  }

  ngOnInit(): void {
    this.subscription = this.userService.getUserInfo().subscribe({
      next: (user: User) => {
        this.user = user;
        this.form.setValue({
          name: user.name || '',
          email: user.email || '',
          password: ''
        })
      },
      error: (err: any) => {
        console.error('Error fetching user info:', err);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
