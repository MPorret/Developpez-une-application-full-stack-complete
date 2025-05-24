import { CommonModule } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';
import { Topic } from 'src/app/interface/topic.interface';
import { TopicService } from 'src/app/services/topic.service';
import { CardComponent } from "../../commons/card/card.component";

@Component({
  selector: 'app-topics',
  imports: [CommonModule, CardComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent implements OnInit {
  public topics$!: Observable<Topic[]>;

  constructor(
    private topicService: TopicService
  ) { }

  public ngOnInit(): void {
    this.topics$ = this.topicService.all();
  }
}
