import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModalInviteMembersComponent } from './modal-invite-members.component';

describe('ModalInviteMembersComponent', () => {
  let component: ModalInviteMembersComponent;
  let fixture: ComponentFixture<ModalInviteMembersComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModalInviteMembersComponent]
    })
    .compileComponents();
    
    fixture = TestBed.createComponent(ModalInviteMembersComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
