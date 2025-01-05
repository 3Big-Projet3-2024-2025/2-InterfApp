import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ListGroupPageComponent } from './list-group-page.component';

describe('ListGroupPageComponent', () => {
  let component: ListGroupPageComponent;
  let fixture: ComponentFixture<ListGroupPageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ListGroupPageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(ListGroupPageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
