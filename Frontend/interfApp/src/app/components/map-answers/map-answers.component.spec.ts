import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MapAnswersComponent } from './map-answers.component';

describe('MapAnswersComponent', () => {
  let component: MapAnswersComponent;
  let fixture: ComponentFixture<MapAnswersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [MapAnswersComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(MapAnswersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
