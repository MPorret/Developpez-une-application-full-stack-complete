import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LayoutwithheaderComponent } from './layoutwithheader.component';

describe('LayoutwithheaderComponent', () => {
  let component: LayoutwithheaderComponent;
  let fixture: ComponentFixture<LayoutwithheaderComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LayoutwithheaderComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(LayoutwithheaderComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
