import { CommonModule, DecimalPipe, UpperCasePipe } from '@angular/common';
import { ChangeDetectorRef, Component } from '@angular/core';
import { AssetService } from '../../../service/asset-service';
import { Asset } from '../../../service/model/asset';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-portfolio',
  imports: [CommonModule,FormsModule,DecimalPipe,UpperCasePipe],
  templateUrl: './portfolio.html',
  styleUrl: './portfolio.scss',
})
export class Portfolio {

  userId = 1;
  assets: Asset[] = [];
  totalPortfolioValue = 0;
totalProfit = 0;
bestCoin = '';
selectedAsset: Asset | null = null;

sellQty = 1;
showSellModal = false;

  constructor(private assetService: AssetService,private cdr:ChangeDetectorRef) {}

loadAssets(): void {
  this.assetService.getUserAssets(this.userId).subscribe({
    next: (res: Asset[]) => {

      console.log("Portfolio Response:", res);

      this.assets = [...res];   // create new reference

      this.totalPortfolioValue = 0;
      this.totalProfit = 0;
      this.bestCoin = '';

      let bestProfit = Number.NEGATIVE_INFINITY;

      this.assets.forEach(asset => {

        const currentValue = asset.Quantity * asset.coin.currentPrice;
        const invested = asset.Quantity * asset.BuyPrice;
        const profit = currentValue - invested;

        this.totalPortfolioValue += currentValue;
        this.totalProfit += profit;

        if (profit > bestProfit) {
          bestProfit = profit;
          this.bestCoin = asset.coin.symbol.toUpperCase();
        }
      });
this.cdr.markForCheck();
    },
    error: err => console.error(err)
  });

}


  ngOnInit(): void {
    this.loadAssets();
    this.cdr.detectChanges();
  }

  confirmSell(): void {

  if (!this.selectedAsset) return;

  if (this.sellQty <= 0 || this.sellQty > this.selectedAsset.Quantity) {
    alert('Invalid quantity');
    return;
  }

  this.assetService.seltAsset(
    this.userId,
    this.selectedAsset.coin.coinId,
    this.sellQty
  ).subscribe({

    next: () => {

      alert('Asset sold successfully!');

      this.showSellModal = false;
      this.selectedAsset = null;
      this.sellQty = 1;

      // Refresh portfolio after selling
      this.loadAssets();
    },

    error: (err) => {
      alert(err.error?.message || 'Sell failed');
    }

  });

}

getCurrentValue(asset:any){
  return asset.Quantity * asset.coin.currentPrice;
}

getProfit(asset:any){
  return (asset.coin.currentPrice - asset.BuyPrice) * asset.Quantity;
}
openSellModal(asset: Asset) {
  this.selectedAsset = asset;
  this.sellQty = 1;
  this.showSellModal = true;
}
sellAsset(){

  if(!this.selectedAsset) return;

  this.assetService.seltAsset(
      this.userId,
      this.selectedAsset.coin.coinId,
      this.sellQty
  ).subscribe({

    next:()=>{

      this.showSellModal = false;

      this.loadAssets();   // refresh holdings
     
    }

  });

}
}