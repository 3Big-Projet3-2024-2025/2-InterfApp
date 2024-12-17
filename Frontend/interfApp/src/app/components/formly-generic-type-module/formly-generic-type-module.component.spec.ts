import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FormlyGenericTypeModuleComponent } from './formly-generic-type-module.component';

describe('FormlyGenericTypeModuleComponent', () => {
  let component: FormlyGenericTypeModuleComponent;
  let fixture: ComponentFixture<FormlyGenericTypeModuleComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FormlyGenericTypeModuleComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(FormlyGenericTypeModuleComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
