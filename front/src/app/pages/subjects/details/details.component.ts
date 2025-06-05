import { Component, OnDestroy, OnInit } from '@angular/core';
import { BackButtonComponent } from "../../../commons/back-button/back-button.component";
import { SubjectService } from 'src/app/services/subject.service';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MatDividerModule } from '@angular/material/divider';
import { Subscription } from 'rxjs';
import { Subject } from 'src/app/interface/subject.interface';

@Component({
  selector: 'app-details',
  imports: [BackButtonComponent, CommonModule, MatDividerModule],
  templateUrl: './details.component.html',
  styleUrl: './details.component.scss'
})
export class DetailsComponent implements OnInit, OnDestroy {
  public subject!: Subject;
  public subscription!: Subscription;

  constructor(private subjectService: SubjectService, private route: ActivatedRoute) { }

  ngOnInit(): void {
    this.subscription = this.subjectService.getSubjectById(this.route.snapshot.params['id']).subscribe({
      next: (subject) => {
        this.subject = subject;
      },
      error: (err) => {
        console.error('Error fetching subject details:', err);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    }
  }
}
