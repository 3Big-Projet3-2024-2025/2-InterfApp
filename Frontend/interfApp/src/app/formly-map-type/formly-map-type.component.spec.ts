import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormlyMapTypeComponent } from './formly-map-type.component';

describe('FormlyMapTypeComponent', () => {
  let component: FormlyMapTypeComponent;
  let fixture: ComponentFixture<FormlyMapTypeComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormlyMapTypeComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormlyMapTypeComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
