import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ModifFormPageComponent } from './modif-form-page.component';

describe('ModifFormPageComponent', () => {
  let component: ModifFormPageComponent;
  let fixture: ComponentFixture<ModifFormPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ModifFormPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ModifFormPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
