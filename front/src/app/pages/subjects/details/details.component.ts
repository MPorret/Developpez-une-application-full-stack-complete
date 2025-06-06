import { Component, OnDestroy, OnInit } from '@angular/core';
import { BackButtonComponent } from "../../../commons/back-button/back-button.component";
import { CommentDto, SubjectService } from 'src/app/services/subject.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { Subscription } from 'rxjs';
import { Subject } from 'src/app/interface/subject.interface';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInput, MatInputModule } from '@angular/material/input';
import { MatIconModule } from '@angular/material/icon';
import { FormBuilder, FormsModule, ReactiveFormsModule, Validators } from '@angular/forms';
import { UserService } from 'src/app/services/user.service';
import { User } from 'src/app/interface/user.interface';

@Component({
  selector: 'app-details',
  imports: [BackButtonComponent, CommonModule, ReactiveFormsModule, FormsModule, MatDividerModule, MatFormFieldModule, MatInputModule, MatIconModule],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent implements OnInit, OnDestroy {
  public subject!: Subject;
  public subscription!: Subscription;
  public userId!: number;
  public onError: boolean = false;

  public form = this.fb.group({
          comment: [
            '',
            [Validators.required]
          ]
        });

  constructor(
    private fb: FormBuilder,
    private subjectService: SubjectService,
    private userService: UserService,
    private route: ActivatedRoute) { }

  public addComment(): void {
    this.onError = false;
    const comment: CommentDto = {
      comment: this.form.value.comment!,
      author_id: this.userId,
      subject_id: this.subject.id
    }
    this.subscription = this.subjectService.addComment(comment).subscribe({
      next: (subject) => {
        this.subject = subject;
        this.form.reset();
      },
      error: (err) => {
        this.onError = true;
      }
    });
  }

  ngOnInit(): void {
    this.subscription = this.subjectService.getSubjectById(this.route.snapshot.params['id']).subscribe({
      next: (subject) => {
        this.subject = subject;
      },
      error: (err) => {
        console.error('Error fetching subject details:', err);
      }
    });

    this.subscription = this.userService.getUserInfo().subscribe({
      next: (user: User) => {
        this.userId = user.id;
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
