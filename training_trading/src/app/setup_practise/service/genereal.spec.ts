import { TestBed } from '@angular/core/testing';

import { Genereal } from './genereal';

describe('Genereal', () => {
  let service: Genereal;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Genereal);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
