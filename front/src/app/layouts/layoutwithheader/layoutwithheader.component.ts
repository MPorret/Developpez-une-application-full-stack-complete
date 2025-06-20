import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { MenuComponent } from "../../commons/menu/menu.component";
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-layoutwithheader',
  templateUrl: './layoutwithheader.component.html',
  styleUrl: './layoutwithheader.component.scss',
  imports: [MatSidenavModule, MatIconModule, MenuComponent, RouterModule, CommonModule]
})
export class LayoutwithheaderComponent implements OnInit {
  @ViewChild('sidenav')
  sidenav!: MatSidenav;
  isMobile = false;

  constructor(){}

  public open(): void {
    this.sidenav.open();
  }

  ngOnInit() {
    this.checkScreenSize();
  }

  @HostListener('window:resize', [])
  onResize() {
    this.checkScreenSize();
  }

  private checkScreenSize() {
    this.isMobile = window.innerWidth < 850;
  }
}
