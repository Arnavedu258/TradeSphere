import {
  ChangeDetectionStrategy,
  ChangeDetectorRef,
  Component,
  OnInit,
  ViewChild
} from '@angular/core';

import { CurrencyPipe, NgStyle, UpperCasePipe } from '@angular/common';
import { RouterLink } from '@angular/router';

import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  faDollarSign,
  faMoneyCheckDollar,
  faArrowTrendUp,
  faWallet,
  faCoins,
  faChartLine
} from '@fortawesome/free-solid-svg-icons';

import { MatPaginator, MatPaginatorModule } from '@angular/material/paginator';
import { MatSort, MatSortModule } from '@angular/material/sort';
import { MatTableDataSource, MatTableModule } from '@angular/material/table';

import { ScrollingModule } from '@angular/cdk/scrolling';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatSelectModule } from '@angular/material/select';

import { Genereal } from '../../../service/genereal';
import { Chartset } from '../chartset/chartset';
import { MarketService } from '../../../service/market-service';
import { FormsModule } from '@angular/forms';

export interface Coin {
  coinId: string;
  name: string;
  symbol: string;
  image: string;
  currentPrice: number;
  marketCapRank: number;
  priceChangePercentage24h: number;
}


@Component({
  standalone: true,
  selector: 'app-dash1',
  templateUrl: './dash1.html',
  styleUrl: './dash1.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
  imports: [
 
  FormsModule,
    NgStyle,
    FontAwesomeModule,
    CanvasJSAngularChartsModule,
    MatTableModule,
    ScrollingModule,
    CurrencyPipe,
    UpperCasePipe,
    MatPaginatorModule,
    MatSortModule,
    Chartset,
    MatFormFieldModule,
    MatSelectModule,
    RouterLink,

  ]
})
export class Dash1 implements OnInit {

  @ViewChild(MatPaginator) paginator!: MatPaginator;
  @ViewChild(MatSort) sort!: MatSort;

  constructor(
    private market:MarketService,
    private cdr: ChangeDetectorRef
  ) {}

  // ---------------- ICONS ----------------

  faDollarSign = faDollarSign;
  faMoneyCheckDollar = faMoneyCheckDollar;
  faArrowTrendUp = faArrowTrendUp;
  faWallet = faWallet;
  faCoins = faCoins;
  faChartLine = faChartLine;

  // ---------------- TABLE ----------------
  // Calculator
selectedCoin!: Coin;

balance = 7000;
riskPercent = 2;

openingPrice = 0;
stopLossPrice = 0;

positionSize = 0;
riskAmount = 0;

  dataSource = new MatTableDataSource<Coin>();

  displayedColumns: string[] = [
    'coin',
    'price',
    'change',
    'action'
  ];

  datas: Coin[] = [];

  // ---------------- DASHBOARD STATS ----------------

  totalBalance = 7000;

  todayProfit = 0;

  totalAssets = 0;

  openPosition = 0;

  avgGrowth = 0;

  // ---------------- EXTRA DATA ----------------

  topGainers: Coin[] = [];

  topLosers: Coin[] = [];

  portfolioAllocation = [
    { name: 'BTC', value: 38, color: '#F59E0B' },
    { name: 'ETH', value: 28, color: '#3B82F6' },
    { name: 'SOL', value: 18, color: '#8B5CF6' },
    { name: 'Others', value: 16, color: '#10B981' }
  ];

  recentActivity = [
    {
      type: 'BUY',
      coin: 'BTC',
      amount: '$1,250',
      time: '2 min'
    },
    {
      type: 'SELL',
      coin: 'ETH',
      amount: '$480',
      time: '12 min'
    },
    {
      type: 'BUY',
      coin: 'SOL',
      amount: '$900',
      time: '1 hour'
    }
  ];

  // ---------------- INIT ----------------

  ngOnInit(): void {

    this.loadMarket();

  }

  // ---------------- LOAD ----------------

  loadMarket(): void {

    this.market.getdata().subscribe({

      next: (coins: Coin[]) => {

        this.datas = coins;

        this.dataSource.data = coins;

        this.dataSource.paginator = this.paginator;

        this.dataSource.sort = this.sort;

        this.calculateDashboard(coins);

        this.cdr.markForCheck();

      }

    });

  }

  // ---------------- CALCULATE ----------------

  calculateDashboard(coins: Coin[]): void {

    this.totalAssets = coins.length;

    this.openPosition = Math.floor(coins.length * 0.42);

    const positive = coins.filter(c => c.priceChangePercentage24h > 0);

    const totalProfit = positive.reduce(
      (sum, c) => sum + c.priceChangePercentage24h,
      0
    );

    this.todayProfit = Number((totalProfit * 8.7).toFixed(2));

    this.avgGrowth = Number(
      (
        coins.reduce(
          (a, b) => a + b.priceChangePercentage24h,
          0
        ) / coins.length
      ).toFixed(2)
    );

    this.topGainers = [...coins]
      .sort(
        (a, b) =>
          b.priceChangePercentage24h -
          a.priceChangePercentage24h
      )
      .slice(0, 5);

    this.topLosers = [...coins]
      .sort(
        (a, b) =>
          a.priceChangePercentage24h -
          b.priceChangePercentage24h
      )
      .slice(0, 5);

  }

  selectCoin(coin: Coin) {
  this.selectedCoin = coin;
  this.openingPrice = coin.currentPrice;
  this.stopLossPrice = Number((coin.currentPrice * 0.98).toFixed(2));
  this.calculatePosition();
}

increasePrice() {
  this.openingPrice++;
  this.calculatePosition();
}

decreasePrice() {
  if (this.openingPrice > 1) {
    this.openingPrice--;
    this.calculatePosition();
  }
}

increaseStop() {
  this.stopLossPrice++;
  this.calculatePosition();
}

decreaseStop() {
  if (this.stopLossPrice > 1) {
    this.stopLossPrice--;
    this.calculatePosition();
  }
}

calculatePosition() {

  const risk = this.openingPrice - this.stopLossPrice;

  if (risk <= 0) {
    this.positionSize = 0;
    return;
  }

  this.riskAmount = this.balance * this.riskPercent / 100;

  this.positionSize = Number((this.riskAmount / risk).toFixed(2));
}
  // ---------------- HELPERS ----------------

  get positiveCoins(): number {

    return this.datas.filter(
      c => c.priceChangePercentage24h > 0
    ).length;

  }

  get negativeCoins(): number {

    return this.datas.filter(
      c => c.priceChangePercentage24h < 0
    ).length;

  }

}