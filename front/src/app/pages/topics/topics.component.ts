import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { Topic } from 'src/app/models/topic.model';
import { TopicsService } from 'src/app/services/topics/topics.service';
import { SubscriptionService } from 'src/app/services/subscriptions/subscriptions.service';
import { Subscription } from 'src/app/models/subscription.model';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';
@Component({
  selector: 'app-topics',
  templateUrl: './topics.component.html',
  styleUrls: ['./topics.component.scss']
})
export class TopicsComponent implements OnInit {

  topics: Topic[] = [];
  userSubscriptions: Subscription[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private topicsService: TopicsService,
    private subscriptionService: SubscriptionService,
    private cdr: ChangeDetectorRef
  ) { }

  ngOnInit(): void {
    this.topicsService.getAllTopics().pipe(takeUntil(this.destroy$)).subscribe(
      (data: Topic[]) => {
        this.topics = data;
      },
      (error: any) => {
        console.error("Can't fetch topics", error);
      }
    );

    this.subscriptionService.getUsersSubscriptions().pipe(takeUntil(this.destroy$)).subscribe(
      (subscriptions: Subscription[]) => {
        this.userSubscriptions = subscriptions;
      },
      (error: any) => {
        console.error("Can't fetch subscriptions", error);
      }
    );
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  isSubscribed(topicId: number): boolean {
    return this.userSubscriptions.some(sub => sub.topicId === topicId);
  }

  subscribeToTopic(topicId: number): void {
    this.subscriptionService.createSubscription(topicId).pipe(takeUntil(this.destroy$)).subscribe(
      (newSubscription: Subscription) => {
        this.userSubscriptions.push(newSubscription);
        this.cdr.markForCheck();
      },
      (error: any) => {
        console.error("Can't subscribe to topic", error);
      }
    );
  }

  unsubscribeFromTopic(topicId: number): void {
    this.subscriptionService.deleteSubscription(topicId).pipe(takeUntil(this.destroy$)).subscribe(
      () => {
        this.userSubscriptions = this.userSubscriptions.filter(sub => sub.topicId !== topicId);
        this.cdr.markForCheck();
      },
      (error: any) => {
        console.error("Can't unsubscribe", error);
      }
    );
  }

}
