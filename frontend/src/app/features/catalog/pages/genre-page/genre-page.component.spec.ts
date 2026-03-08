import { ComponentFixture, TestBed } from '@angular/core/testing';

import { GenrePageComponent } from './genre-page.component';

describe('GenrePageComponent', () => {
  let component: GenrePageComponent;
  let fixture: ComponentFixture<GenrePageComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [GenrePageComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(GenrePageComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
