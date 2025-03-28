import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';
import { AuthService } from 'src/app/auth/services/auth.service';
import { UserDetail } from 'src/app/models/userDetail.model';
import { User } from 'src/app/interfaces/user.interface';
import { FormBuilder, FormGroup } from '@angular/forms';
import { Topic } from 'src/app/models/topic.model';
import { Subscription } from 'src/app/models/subscription.model';
import { TopicsService } from 'src/app/services/topics/topics.service';
import { SubscriptionService } from 'src/app/services/subscriptions/subscriptions.service';
import { Subject } from 'rxjs';
import { takeUntil } from 'rxjs/operators';

@Component({
  selector: 'app-user-details',
  templateUrl: './userDetails.component.html',
  styleUrls: ['./userDetails.component.scss']
})
export class UserDetailsComponent implements OnInit {

  userForm!: FormGroup;
  currentUserId: number = 0;
  userDetails: UserDetail | null = null;
  user: User | null = null;
  topics: Topic[] = [];
  userSubscriptions: Subscription[] = [];
  filteredTopics: Topic[] = [];
  private destroy$ = new Subject<void>();

  constructor(
    private authService: AuthService,
    private userService: UserService,
    private fb: FormBuilder,
    private topicsService: TopicsService,
    private subscriptionService: SubscriptionService
  ) {}

  ngOnInit(): void {

    this.userForm = this.fb.group({
      username: [''],
      email: [''],
      password: ['']
    });

    this.authService.me().pipe(takeUntil(this.destroy$)).subscribe(
      (user: User) => {
        this.currentUserId = user.id;
        this.loadUserDetails();
      },
      (error: Error) => {
        console.error("Erreur lors de la récupération de l'utilisateur", error);
      }
    );

    this.topicsService.getAllTopics().pipe(takeUntil(this.destroy$)).subscribe(
      (data: Topic[]) => {
        this.topics = data;
        this.updateFilteredTopics()
      },
      (error: any) => {
        console.error("Can't fetch topics", error);
      }
    );

    this.subscriptionService.getUsersSubscriptions().pipe(takeUntil(this.destroy$)).subscribe(
      (subscriptions: Subscription[]) => {
        this.userSubscriptions = subscriptions;
        this.updateFilteredTopics()
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

  ngOnChange(){
    this.updateFilteredTopics()
    return this.topics.filter(topic => this.isSubscribed(topic.id));
  }

  updateFilteredTopics(): void {
    this.filteredTopics = this.topics.filter(topic => this.isSubscribed(topic.id));
  }

  loadUserDetails(): void {
    this.userService.getUserById(this.currentUserId).pipe(takeUntil(this.destroy$)).subscribe(
      (data: User) => {
        this.userForm.patchValue({
          username: data.username,
          email: data.email,
          password: ''
        });
        this.user = data;
      },
      (error: Error) => {
        console.error('Erreur lors de la récupération des détails utilisateur', error);
      }
    );
  }

  onSubmit(): void {
    if (this.userForm.valid) {
      const updatedUser: User = {
        ...this.userForm.value,
        id: this.currentUserId
      };

      this.userService.updateUserDetails(updatedUser).pipe(takeUntil(this.destroy$)).subscribe(
        () => {
          console.log('Utilisateur mis à jour avec succès');
        },
        (error: Error) => {
          console.error('Erreur lors de la mise à jour de l’utilisateur', error);
        }
      );
    }
  }

  // Méthode pour vérifier si l'utilisateur est déjà abonné
    isSubscribed(topicId: number): boolean {
      return this.userSubscriptions.some(sub => sub.topicId === topicId);
    }

    subscribeToTopic(topicId: number): void {
      this.subscriptionService.createSubscription(topicId).pipe(takeUntil(this.destroy$)).subscribe(
        (newSubscription: Subscription) => {
          this.userSubscriptions.push(newSubscription);
          this.updateFilteredTopics();
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
          this.updateFilteredTopics();
        },
        (error: any) => {
          console.error("Can't unsubscribe", error);
        }
      );
    }
}
