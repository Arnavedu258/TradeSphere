import { TestBed } from '@angular/core/testing';

import { WithdrawlService } from './withdrawl-service';

describe('WithdrawlService', () => {
  let service: WithdrawlService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WithdrawlService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
