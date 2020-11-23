import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NotebookDisplayComponent } from './notebook-display.component';

describe('NotebookDisplayComponent', () => {
  let component: NotebookDisplayComponent;
  let fixture: ComponentFixture<NotebookDisplayComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NotebookDisplayComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NotebookDisplayComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
