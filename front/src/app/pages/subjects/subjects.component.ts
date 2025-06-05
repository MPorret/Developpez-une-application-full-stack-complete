import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { SubscribeButtonComponent } from 'src/app/commons/subscribe-button/subscribe-button.component';
import { SubjectService } from 'src/app/services/subject.service';
import { CardComponent } from "../../commons/card/card.component";
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-subjects',
  imports: [SubscribeButtonComponent, CardComponent, CommonModule],
  templateUrl: './subjects.component.html',
  styleUrl: './subjects.component.scss'
})
export class SubjectsComponent {
  public subjects$ = this.subjectService.getSubscribedSubjects();

  constructor(private router: Router, private subjectService: SubjectService) { }

  public onClickCreate() {
    this.router.navigate(['/articles/create']);
  }

  public onClick(id: number) {
    this.router.navigate(['/articles/' + id]);
  }
}
