import { Component, OnInit } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { TopicsService } from 'src/app/services/topics/topics.service';

@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {

  topics: Topic[] = [];

  constructor(private topicsService: TopicsService) { }

  ngOnInit(): void {
    this.topicsService.getAllTopics().subscribe(
      (data: Topic[]) => {
        console.log("Données reçues :", data);
        this.topics = data;
      },
      (error: any) => {
        console.error("Can't fetch topics", error);
      }
    );
  }
}
