import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalSubGroupComponent } from './modal-sub-group.component';

describe('ModalSubGroupComponent', () => {
  let component: ModalSubGroupComponent;
  let fixture: ComponentFixture<ModalSubGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalSubGroupComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ModalSubGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
