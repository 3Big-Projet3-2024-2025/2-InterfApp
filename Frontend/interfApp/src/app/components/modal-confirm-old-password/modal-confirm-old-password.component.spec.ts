import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalConfirmOldPasswordComponent } from './modal-confirm-old-password.component';

describe('ModalConfirmOldPasswordComponent', () => {
  let component: ModalConfirmOldPasswordComponent;
  let fixture: ComponentFixture<ModalConfirmOldPasswordComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalConfirmOldPasswordComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalConfirmOldPasswordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
