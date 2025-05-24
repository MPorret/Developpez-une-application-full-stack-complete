import { Component, Input } from '@angular/core';
import { Topic } from 'src/app/interface/topic.interface';
import {MatButtonModule} from '@angular/material/button';

@Component({
  selector: 'app-card',
  imports: [MatButtonModule],
  templateUrl: './card.component.html',
  styleUrl: './card.component.scss'
})
export class CardComponent {
  @Input()
  data: Record<string, string> | Topic = {};
  @Input()
  button: boolean = false;  
}
