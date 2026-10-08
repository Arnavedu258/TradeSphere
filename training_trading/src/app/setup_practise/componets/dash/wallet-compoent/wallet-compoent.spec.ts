import { ComponentFixture, TestBed } from '@angular/core/testing';

import { WalletCompoent } from './wallet-compoent';

describe('WalletCompoent', () => {
  let component: WalletCompoent;
  let fixture: ComponentFixture<WalletCompoent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WalletCompoent],
    }).compileComponents();

    fixture = TestBed.createComponent(WalletCompoent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
