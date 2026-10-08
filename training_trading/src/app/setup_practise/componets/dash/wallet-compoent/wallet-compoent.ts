import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { WalletService } from '../../../service/wallet-service';
import {
  faWallet, faCircleCheck, faShieldHalved,
  faDownload, faPaperPlane, faCreditCard,
  faChevronRight, faPlus, faMoneyBillTransfer, faCheck
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-wallet-compoent',
  standalone: true,
  imports: [CommonModule, FormsModule, FontAwesomeModule],
  templateUrl: './wallet-compoent.html',
  styleUrl: './wallet-compoent.scss'
})
export class WalletCompoent implements OnInit {

  constructor(
    private walletService: WalletService,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  userId = 1;

  wallet = {
    id: 0,
    balance: 0
  };

  transactions: any[] = [];

  showDeposit = false;
  showTransfer = false;
  showPay = false;
  showSuccess = false;
  loading = false;

  receiverId = 0;
  transferAmount = 0;
  depositAmount = 0;
  orderId = 0;

  lastAmount = 0;
  lastType = '';
  transactionId = '';
  today = new Date();
  totalDeposit = 0;
depositCount = 0;
transferCount = 0;

  faWallet = faWallet;
  faCircleCheck = faCircleCheck;
  faShield = faShieldHalved;
  faDownload = faDownload;
  faPaperPlane = faPaperPlane;
  faCreditCard = faCreditCard;
  faChevronRight = faChevronRight;
  faPlus = faPlus;
  faMoneyTransfer = faMoneyBillTransfer;
  faCheck = faCheck;

  ngOnInit(): void {
    this.loadWallet();
    this.loadTransactions();
  }


  loadWallet(): void {
    this.walletService.getWallet(this.userId).subscribe({
      next: (res: any) => {
        this.zone.run(() => {
          this.wallet = {
            id: res.id,
            balance: Number(res.balance)
          };
          this.cdr.markForCheck();
        });
      },
      error: (err) => console.error(err)
    });
  }

   
deposit(): void {

  if (this.depositAmount <= 0) return;

  this.loading = true;

  this.walletService.deposit(this.userId, this.depositAmount).subscribe({

    next: () => {
      this.loading = false;
      this.showDeposit = false;

      this.lastAmount = this.depositAmount;
      this.lastType = 'Deposit Successful';
      this.transactionId = 'DEP-' + Date.now();

      this.loadWallet();
      this.loadTransactions();

      this.showSuccess = true;
      this.depositAmount = 0;
      this.cdr.markForCheck();
    },

    error: (err) => {
      this.loading = false;
      alert(err.error?.message || 'Deposit Failed');
      this.cdr.markForCheck();
    }

  });

}

transfer(): void {

  if (this.receiverId <= 0 || this.transferAmount <= 0) return;

  this.loading = true;

  this.walletService
    .transfer(this.userId, this.receiverId, this.transferAmount)
    .subscribe({

      next: () => {
        this.loading = false;
        this.showTransfer = false;

        this.lastAmount = this.transferAmount;
        this.lastType = 'Transfer Successful';
        this.transactionId = 'TRF-' + Date.now();

        this.loadWallet();
        this.loadTransactions();

        this.showSuccess = true;
        this.cdr.markForCheck();
      },

      error: (err) => {
        this.loading = false;
        alert(err.error?.message || 'Transfer Failed');
        this.cdr.markForCheck();
      }

    });

}
  // Add below wallet declaration

loadTransactions(): void {

  this.walletService.loadTransaction(this.userId).subscribe({
    next: (res: any[]) => {

      this.transactions = res;

      this.totalDeposit = res
        .filter(x => x.walletTransactionType === 'DEPOSIT')
        .reduce((sum, x) => sum + x.amount, 0);

      this.depositCount = res.filter(
        x => x.walletTransactionType === 'DEPOSIT'
      ).length;

    this.transferCount = res.filter(
  x => x.walletTransactionType === 'WALLET_TRANSFER'
).length;

      this.cdr.markForCheck();
    }
  });

}

payOrder(): void {

  if (this.orderId <= 0) return;

  this.loading = true;

  this.walletService.payOrder(this.userId, this.orderId).subscribe({

    next: () => {
      this.loading = false;
      this.showPay = false;

      this.lastAmount = 0;
      this.lastType = 'Order Payment';
      this.transactionId = 'PAY-' + Date.now();

      this.loadWallet();
      this.loadTransactions();

      this.showSuccess = true;
      this.cdr.markForCheck();
    },

    error: (err) => {
      this.loading = false;
      alert(err.error?.message || 'Payment Failed');
      this.cdr.markForCheck();
    }

  });

}
}