import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatCardModule } from '@angular/material/card';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SubscribeButtonComponent } from "../../../commons/subscribe-button/subscribe-button.component";
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { BackButtonComponent } from "../../../commons/back-button/back-button.component";
import { TopicService } from 'src/app/services/topic.service';
import { CommonModule } from '@angular/common';
import { MatOptionModule } from '@angular/material/core';
import {MatSelectModule} from '@angular/material/select';
import { Router } from '@angular/router';
import { SubjectDto, SubjectService } from 'src/app/services/subject.service';
import { Subscription } from 'rxjs';
import { User } from 'src/app/interface/user.interface';
import { UserService } from 'src/app/services/user.service';
import {TextFieldModule} from '@angular/cdk/text-field';

@Component({
  selector: 'app-create',
  imports: [MatCardModule, TextFieldModule, MatFormFieldModule, MatSelectModule, MatOptionModule, MatInputModule, SubscribeButtonComponent, CommonModule, ReactiveFormsModule, FormsModule, BackButtonComponent],
  templateUrl: './create.component.html',
  styleUrl: './create.component.scss'
})
export class CreateComponent implements OnInit, OnDestroy {
  public topics$ = this.topicService.all();
  public userId!: number;
  public subscription!: Subscription;
  public onError: boolean = false;

  public form = this.fb.group({
          topic_id: [
            '',
            [Validators.required]
          ],
          title: [
            '',
            [Validators.required]
          ],
          message: [
            '',
            [Validators.required]
          ]
        });

  constructor(
    private fb: FormBuilder,
    private subjectService: SubjectService,
    private topicService: TopicService,
    private userService: UserService, 
    private router: Router 
  ) {}

  ngOnInit(): void {
    this.subscription = this.userService.getUserInfo().subscribe({
      next: (user: User) => {
        this.userId = user.id;
      }
    });
  }

  public onSubmit(): void {
    this.onError = false;
    const formValue = {
      ...this.form.value,
      topic_id: Number(this.form.value.topic_id),
      author_id: this.userId,
    } as SubjectDto;
    this.subjectService.createSubject(formValue).subscribe({
      next: () => {
        console.log('success')
        this.router.navigate(['/articles']);
      },
      error: err => {
        console.error('Error creating subject:', err);
        this.onError = true},
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
