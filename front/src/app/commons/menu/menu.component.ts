import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { RouterLink, RouterLinkActive } from '@angular/router';
import { Observable } from 'rxjs';
import { SessionService } from 'src/app/services/session.service';

@Component({
  selector: 'app-menu',
  imports: [RouterLink, RouterLinkActive, CommonModule ],
  templateUrl: './menu.component.html',
  styleUrl: './menu.component.scss'
})
export class MenuComponent {
  
    constructor(private sessionService: SessionService
    ){}
  
    public $isLogged(): Observable<boolean> {
      return this.sessionService.$isLogged();
    }
}
