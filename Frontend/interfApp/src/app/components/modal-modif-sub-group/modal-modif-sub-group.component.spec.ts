import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalModifSubGroupComponent } from './modal-modif-sub-group.component';

describe('ModalModifSubGroupComponent', () => {
  let component: ModalModifSubGroupComponent;
  let fixture: ComponentFixture<ModalModifSubGroupComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalModifSubGroupComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalModifSubGroupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
