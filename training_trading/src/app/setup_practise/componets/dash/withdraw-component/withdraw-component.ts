import { CommonModule, DatePipe, DecimalPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import {
  faWallet,
  faRotateRight,
  faArrowUpRightFromSquare,
  faClockRotateLeft,
  faCircleCheck,
  faMoneyBillTransfer,
  faChevronLeft,
  faChevronRight,faBuildingColumns,faLandmark,faCreditCard,faShieldHalved
} from '@fortawesome/free-solid-svg-icons';

import { WalletService } from '../../../service/wallet-service';

import { forkJoin } from 'rxjs';
import { FormsModule } from '@angular/forms';
import { WithdrawlService } from '../../../service/withdrawl-service';
export interface Withdrawl{
  id:number;
  amount:number;
  status:string;
  date:string;
}

export interface Wallet{
  balance:number;
  bankName?:string;
  accountLast4?:string;
}
@Component({
  selector: 'app-withdraw-component',
  imports: [DecimalPipe,DatePipe,FontAwesomeModule,FormsModule,CommonModule],

  templateUrl: './withdraw-component.html',
  styleUrl: './withdraw-component.scss',
})
export class WithdrawComponent implements OnInit{
  // Logged-in user (fallback for demo)


  userId = Number(localStorage.getItem('userId')) || 1;
userName = localStorage.getItem('userName') || 'Trader';
wallet: Wallet = {
  balance: 0,
  bankName: 'HDFC Bank',
  accountLast4: '4821'
};
history: Withdrawl[] = [];
filteredHistory: Withdrawl[] = [];

amount = 0;
processingFee = 2;

totalWithdrawn = 0;
pendingCount = 0;
completedCount = 0;
rejectedCount = 0;
successRate = 0;

loading = false;
isRefreshing = false;

search = '';
currentPage = 1;

pageSize = 6;
totalAmount=0;




  // ---------------- ICONS ----------------
  faWallet = faWallet;
  faRotateRight = faRotateRight;
  faArrowUpRightFromSquare = faArrowUpRightFromSquare;
  faClockRotateLeft = faClockRotateLeft;
  faCircleCheck = faCircleCheck;
  faMoneyBillTransfer = faMoneyBillTransfer;
  faChevronLeft = faChevronLeft;
  faChevronRight = faChevronRight;
 faBuildingColumns=faBuildingColumns;

faShieldHalved = faShieldHalved;
faCreditCard = faCreditCard;
faLandmark = faLandmark; 

  constructor(
    private walletService: WalletService,
    private withdrawService: WithdrawlService
  ) {}

  ngOnInit(): void {
    this.refresh();
  }

  // =========================================
  // LOAD EVERYTHING
  // =========================================

 refresh(): void {

  this.loading = true;
  this.isRefreshing = true;

  forkJoin({
    wallet: this.walletService.getWallet(this.userId),
    history: this.withdrawService.history(this.userId)
  }).subscribe({

    
next: (res) => {

  this.wallet = res.wallet ?? { balance: 0 };

  this.history = (res.history ?? []).map((w: any) => ({
    id: w.Id,
    amount: w.amount,
    status: w.STATUS,
    date: w.Date,
    user: w.user

  }));

  this.calculateAnalytics();
  this.applyFilter();
  this.loading = false;

},

    error: () => {
      this.loading = false;
    }

  });

}

  loadWallet(): void {
    this.refresh();
  }

  // =========================================
  // ANALYTICS
  // =========================================

  calculateAnalytics(): void {

  this.totalWithdrawn = 0;
  this.pendingCount = 0;
  this.completedCount = 0;
  this.rejectedCount = 0;

  this.history.forEach(item => {

    const status = (item.status || '').toUpperCase();

    switch (status) {

      case 'SUCCESS':
      case 'ACCEPTED':
        this.completedCount++;
        this.totalWithdrawn += item.amount;
        break;

      case 'PENDING':
        this.pendingCount++;
        break;

      case 'DECLINED':
      case 'REJECTED':
        this.rejectedCount++;
        break;
    }

        this.totalAmount+=item.amount;
  });

  const total = this.completedCount + this.pendingCount + this.rejectedCount;

  this.successRate = total
    ? Math.round((this.completedCount / total) * 100)
    : 0;


}
  // =========================================
  // FILTER
  // =========================================

applyFilter(): void {

  const txt = this.search.trim().toLowerCase();

  if(!txt){

    this.filteredHistory = [...this.history];

  }else{

    this.filteredHistory = this.history.filter(item =>

      item.status.toLowerCase().includes(txt) ||

      item.id.toString().includes(txt) ||

      item.amount.toString().includes(txt)

    );

  }

  this.currentPage = 1;

}
  // =========================================
  // QUICK BUTTONS
  // =========================================

 setAmount(percent:number){

  this.amount = Number(
    ((this.wallet.balance * percent) / 100).toFixed(2)
  );

}

setMax() {
  this.amount = Number(
    Math.max(this.wallet.balance - this.processingFee, 0).toFixed(2)
  );
}

get compactBalance(): string {
  const value = this.wallet?.balance ?? 0;

  if (value >= 1_000_000_000)
    return (value / 1_000_000_000).toFixed(2) + 'B';

  if (value >= 1_000_000)
    return (value / 1_000_000).toFixed(2) + 'M';

  if (value >= 1_000)
    return (value / 1_000).toFixed(2) + 'K';

  return value.toFixed(2);
}

  // =========================================
  // NET AMOUNT
  // =========================================

get netAmount(): number{

  const value = this.amount - this.processingFee;

  return value > 0 ? value : 0;

}

onAmountChange() {
  if (this.amount < 0) {
    this.amount = 0;
  }

  if (this.amount > this.wallet.balance) {
    this.amount = this.wallet.balance;
  }
}
  // =========================================
  // WITHDRAW
  // =========================================
withdraw(){

  if(this.amount < 10){

    alert("Minimum withdrawal is $10");

    return;

  }

  if(this.amount > this.wallet.balance){

    alert("Insufficient balance");

    return;

  }

  this.loading = true;

  this.withdrawService.create(this.userId, this.amount)
    .subscribe({

      next:()=>{

        alert("Withdrawal request submitted!");

        this.amount = 0;

        this.refresh();

      },

      error:(err)=>{

        this.loading = false;

        alert(err.error?.message ?? "Withdrawal failed");

      }

    });

}
  // =========================================
  // PAGINATION
  // =========================================
get paginatedHistory(){

  const start=(this.currentPage-1)*this.pageSize;

  return this.filteredHistory.slice(start,start+this.pageSize);

}

get totalPages(){

  return Math.ceil(this.filteredHistory.length/this.pageSize) || 1;

}

next(){

  if(this.currentPage<this.totalPages){

    this.currentPage++;

  }

}

prev(){

  if(this.currentPage>1){

    this.currentPage--;

  }

}
  // =========================================
  // HELPERS
  // =========================================

  getStatusColor(status: string): string {

    switch (status) {

      case 'SUCCESS':
        return 'success';

      case 'PENDING':
        return 'pending';

      default:
        return 'reject';

    }

  }
}