import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SummaryAnswersComponent } from './summary-answers.component';

describe('SummaryAnswersComponent', () => {
  let component: SummaryAnswersComponent;
  let fixture: ComponentFixture<SummaryAnswersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SummaryAnswersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(SummaryAnswersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
