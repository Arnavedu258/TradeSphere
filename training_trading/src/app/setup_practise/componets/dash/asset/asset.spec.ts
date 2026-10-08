import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Asset } from './asset';

describe('Asset', () => {
  let component: Asset;
  let fixture: ComponentFixture<Asset>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Asset],
    }).compileComponents();

    fixture = TestBed.createComponent(Asset);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
