import { CommonModule } from '@angular/common';
import { Component, OnDestroy, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { CardComponent } from 'src/app/commons/card/card.component';
import { Topic } from 'src/app/interface/topic.interface';
import { TopicService } from 'src/app/services/topic.service';
import { SubscribeButtonComponent } from "../../commons/subscribe-button/subscribe-button.component";

@Component({
  selector: 'app-topics',
  imports: [CommonModule, CardComponent, SubscribeButtonComponent],
  templateUrl: './topics.component.html',
  styleUrl: './topics.component.scss'
})
export class TopicsComponent implements OnInit, OnDestroy {
  public topics!: Topic[];
  public subscriptions: Subscription[] = [];

  constructor(
    private topicService: TopicService
  ) { }

  onSubscribe(topic: Topic): void {
    this.subscriptions.push(this.topicService.subscribe(topic.id).subscribe({
      next: (data) => {
        this.topics = data;
      },
      error: (err) => {
        console.error('Subscription failed', err);
      },
    }));
  }

  ngOnInit(): void {
    this.subscriptions.push(this.topicService.all().subscribe({
      next:  (data) => {
        this.topics = data;
      },
      error: (err) => {
        console.error('Subscription failed', err);
      },
    }))
  }

  ngOnDestroy(): void {
    this.subscriptions.forEach((subscription) => {
      subscription.unsubscribe
    })
  }
}
