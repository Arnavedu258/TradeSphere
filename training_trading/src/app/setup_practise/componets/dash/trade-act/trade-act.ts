import { DatePipe, DecimalPipe, NgClass, NgStyle } from '@angular/common';
import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { WalletTransaction } from '../../../service/wallet-transaction';
import { WalletService } from '../../../service/wallet-service';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-trade-act',
  imports: [DatePipe,DecimalPipe,NgClass,FormsModule],
  templateUrl: './trade-act.html',
  styleUrl: './trade-act.scss',
})
export class TradeAct implements OnInit {
  transactions: WalletTransaction[] = [];
searchText: string = '';
  filteredTransactions: WalletTransaction[] = [];

  
  totalTrades = 0;
  portfolioValue = 0;
  totalProfit = 0;
  buyingPower = 0;
  constructor(private walletService:WalletService,private cdr:ChangeDetectorRef ){}

  ngOnInit():void{
    this.walletService.loadTransaction(1).subscribe((data:WalletTransaction[])=>{
      this.transactions=data;
      this.filteredTransactions=data;
     // Total Trades
      this.totalTrades = data.length;

      // Total Deposit
      this.portfolioValue = data
        .filter(x => x.walletTransactionType === 'DEPOSIT')
        .reduce((sum, x) => sum + Math.abs(x.amount), 0);

      // Total Transfer
      this.totalProfit = data
        .filter(x => x.walletTransactionType === 'TRANSFER')
        .reduce((sum, x) => sum + Math.abs(x.amount), 0);

      // Current Wallet Balance
      this.buyingPower = data.length ? data[0].wallet.balance : 0;
      this.cdr.markForCheck();
    })

  }

    onSearch() {
    const key = this.searchText.toLowerCase().trim();

    this.filteredTransactions = this.transactions.filter(tx =>
      tx.sender.userName.toLowerCase().includes(key) ||
      tx.receiver.userEmail.toLowerCase().includes(key) ||
      tx.walletTransactionType.toLowerCase().includes(key) ||
      tx.referenceId.toLowerCase().includes(key)
    );
  }
  
  
}


