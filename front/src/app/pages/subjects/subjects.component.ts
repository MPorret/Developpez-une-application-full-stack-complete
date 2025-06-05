import { Component, OnDestroy, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { SubscribeButtonComponent } from 'src/app/commons/subscribe-button/subscribe-button.component';
import { SubjectService } from 'src/app/services/subject.service';
import { CardComponent } from "../../commons/card/card.component";
import { CommonModule } from '@angular/common';
import { MatIconModule } from '@angular/material/icon';
import { Subject } from 'src/app/interface/subject.interface';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-subjects',
  imports: [SubscribeButtonComponent, CardComponent, CommonModule, MatIconModule],
  templateUrl: './subjects.component.html',
  styleUrl: './subjects.component.scss'
})
export class SubjectsComponent implements OnInit, OnDestroy {
  public subjects!: Subject[];
  public subscription!: Subscription;
  public sort = 'south';

  constructor(private router: Router, private subjectService: SubjectService) { }

  public onClickCreate() {
    this.router.navigate(['/articles/create']);
  }

  public onClick(id: number) {
    this.router.navigate(['/articles/' + id]);
  }

  public onSort() {
    if (this.sort === 'south') {
      this.subjects.sort((a, b) => new Date(b.created_at).getTime() - new Date(a.created_at).getTime());
      this.sort = 'north';
    }
    else {
      this.subjects.sort((a, b) => new Date(a.created_at).getTime() - new Date(b.created_at).getTime());
      this.sort = 'south';
    }
  }

  ngOnInit(): void {
    this.subscription = this.subjectService.getSubscribedSubjects().subscribe({
      next: (subjects: Subject[]) => {
        this.subjects = subjects;
        this.subjects.sort((a, b) => new Date(a.created_at).getTime() - new Date(b.created_at).getTime());
        this.sort = 'south';
      },
      error: (err) => {
        console.error('Error fetching subjects:', err);
      }
    });
  }

  ngOnDestroy(): void {
    if (this.subscription) {
      this.subscription.unsubscribe();
    } 
  }

}
