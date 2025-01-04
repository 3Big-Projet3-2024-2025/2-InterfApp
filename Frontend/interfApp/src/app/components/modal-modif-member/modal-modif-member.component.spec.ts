import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalModifMemberComponent } from './modal-modif-member.component';

describe('ModalModifMemberComponent', () => {
  let component: ModalModifMemberComponent;
  let fixture: ComponentFixture<ModalModifMemberComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalModifMemberComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModalModifMemberComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
