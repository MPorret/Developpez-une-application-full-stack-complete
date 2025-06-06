import { Component, HostListener, OnInit, ViewChild } from '@angular/core';
import { MatIconModule } from '@angular/material/icon';
import { MatSidenav, MatSidenavModule } from '@angular/material/sidenav';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { BackButtonComponent } from "../../commons/back-button/back-button.component";

@Component({
  selector: 'app-layoutforauth',
  templateUrl: './layoutforauth.component.html',
  styleUrl: './layoutforauth.component.scss',
  imports: [MatSidenavModule, MatIconModule, RouterModule, CommonModule, BackButtonComponent]
})
export class LayoutForAuthComponent implements OnInit {
  @ViewChild('sidenav')
  sidenav!: MatSidenav;
  isMobile = false;

  constructor(){}

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
