import { Component, Input } from '@angular/core';
import { Topic } from 'src/app/interface/topic.interface';
import { SubscribeButtonComponent } from '../subscribe-button/subscribe-button.component';

@Component({
  selector: 'app-card',
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss',
  imports: [SubscribeButtonComponent]
})
export class CardComponent {
  @Input()
  data: Record<string, string> | Topic = {};
  @Input()
  buttonText: string | undefined = undefined;
}
