import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { Observable, of } from 'rxjs';
import { CardComponent } from 'src/app/commons/card/card.component';
import { Topic } from 'src/app/interface/topic.interface';
import { TopicService } from 'src/app/services/topic.service';

@Component({
  selector: 'app-topics',
  imports: [CommonModule, CardComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent {
  public topics$: Observable<Topic[]> = this.topicService.all();
  public buttonText = "S'abonner";

  constructor(
    private topicService: TopicService
  ) { }

  onSubscribe(topic: Topic): void {
    this.topicService.subscribe(topic.id).subscribe({
      next: (data) => {
        this.topics$ = of(data);
        console.table(data);
      },
      error: (err) => {
        console.error('Subscription failed', err);
      },
    });
  }

  getOnSubscribeHandler(topic: Topic): () => void {
    return () => this.onSubscribe(topic);
  }
}
