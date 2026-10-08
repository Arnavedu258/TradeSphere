import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  OnInit,
  ViewChild
} from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import {
  createChart,
  CandlestickSeries,
  ColorType
} from 'lightweight-charts';

import { MarketService, CandleDTO } from '../../../service/market-service';
import { WalletService } from '../../../service/wallet-service';
import { OrderService } from '../../../service/order-service';
import { Coin } from '../../../service/model/coin';


export type OrderMode = 'Market' | 'Limit' | 'Stop Limit';
export interface TradeOrder{
  id:number;
  type:'BUY'|'SELL';
  price:number;
  quantity:number;
  total:number;
  time:string;
}

@Component({
  selector:'app-paramdash',
  standalone:true,
  imports:[CommonModule,FormsModule],
  templateUrl:'./paramdash.html',
  styleUrls:['./paramdash.scss']
})
export class Paramdash implements OnInit,AfterViewInit{

  @ViewChild('chartContainer')
  chartContainer!:ElementRef;

  constructor(
    private market:MarketService,
    private walletService:WalletService,
    private orderService:OrderService,
    private route:ActivatedRoute,
    private cdr:ChangeDetectorRef
  ){}

  /* ---------------------------------------
      DATA
  ----------------------------------------*/

  UserId=1;
  myid='';
  dater=30;

  singeldata:Coin|null=null;
  oneday:CandleDTO|null=null;

  dataDate:CandleDTO[]=[];
  manu:Coin[]=[];
availableBalance = 0;
 wallet: any = null;

  HighPrice:Coin|null=null;
  LowPrice:Coin|null=null;

  dailyProfitValue=0;

/* ---------------------------------------
    TRADE PANEL
----------------------------------------*/

isBuy = true;

selectedOrderType: OrderMode = 'Market';

quantity = 1;

stopLoss = 0;
takeProfit = 0;

orderMessage = '';
orderSuccess = false;
isLoading = false;

orderBook: TradeOrder[] = [];
recentTrades: TradeOrder[] = [];


  /* ---------------------------------------
      CHART
  ----------------------------------------*/

  private chart:any;
  private candleSeries:any;

  /* =======================================
      INIT
  ========================================*/

  ngOnInit():void{

    this.myid=this.route.snapshot.params['id'];

    this.loadWallet();
    this.loadCoin();
    this.loadMarket();
    this.loadOneDay();
    this.loadOrders();

  }

  ngAfterViewInit():void{

    const el=this.chartContainer.nativeElement;

    this.chart=createChart(el,{
      width:el.clientWidth,
      height:520,

      layout:{
        background:{type:ColorType.Solid,color:'#08111F'},
        textColor:'#94A3B8'
      },

      grid:{
        vertLines:{color:'rgba(255,255,255,.05)'},
        horzLines:{color:'rgba(255,255,255,.05)'}
      },

      rightPriceScale:{
        borderColor:'rgba(255,255,255,.08)'
      },

      timeScale:{
        borderColor:'rgba(255,255,255,.08)',
        timeVisible:true
      }
    });

    this.candleSeries=this.chart.addSeries(CandlestickSeries,{
      upColor:'#16C784',
      downColor:'#EA3943',
      wickUpColor:'#16C784',
      wickDownColor:'#EA3943',
      borderVisible:false
    });

    this.Changeperiod(this.dater);

    window.addEventListener('resize',()=>{
      this.chart.applyOptions({
        width:el.clientWidth
      });
    });

  }

  /* =======================================
      API
  ========================================*/



loadWallet(): void {
  this.walletService.getWallet(this.UserId).subscribe({
    next: (res) => {
      this.wallet = res;
      this.availableBalance = Number(res.balance);
      this.cdr.markForCheck();
    }
  });
}

  loadCoin(){

    this.market.SingleTro(this.myid).subscribe({
      next:(coin)=>{

        this.singeldata=coin;


        this.dailyProfitValue=Number(
          (coin.currentPrice*coin.priceChangePercentage24h/100).toFixed(2)
        );

      this.stopLoss = Number((coin.currentPrice * 0.95).toFixed(4));
      this.takeProfit = Number((coin.currentPrice * 1.15).toFixed(4));
        this.cdr.markForCheck();

      }
    });

  }

  loadMarket(){

    this.market.getdata().subscribe((data:Coin[])=>{

      this.manu=data;

      this.HighPrice=data.reduce((a,b)=>
        a.currentPrice>b.currentPrice?a:b
      );

      this.LowPrice=data.reduce((a,b)=>
        a.currentPrice<b.currentPrice?a:b
      );

    });

  }

  loadOneDay(){

    this.market.getchart(this.myid,1).subscribe((d)=>{

      this.oneday=d[0];
      this.cdr.markForCheck();

    });

  }
loadOrders(): void {

  this.orderService
    .getUserOrders(this.UserId, 'BUY', this.myid)
    .subscribe({

      next: (orders: any[]) => {

        this.orderBook = orders.map(o => ({
          id: o.id,
          type: o.Ordertype as 'BUY' | 'SELL',
          price: Number(o.orderItem.BuyPrice || o.orderItem.sellPrice),
          quantity: o.orderItem.quantity,
          total: Number(o.price),
          time: o.timestamp
        }));

        this.recentTrades = [...this.orderBook].reverse();

        this.cdr.markForCheck();
      },

      error: () => {
        this.orderBook = [];
        this.recentTrades = [];
      }

    });

}

  /* =======================================
      CHART PERIOD
  ========================================*/

  Changeperiod(days:number){

    this.dater=days;

    this.market.getchart(this.myid,days).subscribe((data)=>{

      if(!this.candleSeries) return;

      this.dataDate=data;

      this.candleSeries.setData(
        data.map(c=>({

          time:Math.floor(c.time/1000),
          open:c.open,
          high:c.high,
          low:c.low,
          close:c.close

        }))
      );

      this.chart.timeScale().fitContent();

    });

  }

  /* =======================================
      GETTERS
  ========================================*/

  get totalCost():number{

    if(!this.singeldata) return 0;

    return Number(
      (this.quantity*this.singeldata.currentPrice).toFixed(2)
    );

  }

  get tradingFee():number{
    return Number((this.totalCost*0.002).toFixed(2));
  }

  get grandTotal():number{
    return Number((this.totalCost+this.tradingFee).toFixed(2));
  }

  get maxQty():number{

    if(!this.singeldata) return 1;

    return Math.max(
      1,
      Math.floor(this.availableBalance/this.singeldata.currentPrice)
    );

  }

  /* =======================================
      PANEL ACTIONS
  ========================================*/

  selectBuy(){ this.isBuy=true; }

  selectSell(){ this.isBuy=false; }

  increaseQty(){

    if(this.quantity<this.maxQty){
      this.quantity++;
    }

  }

  decreaseQty(){

    if(this.quantity>1){
      this.quantity--;
    }

  }

  setMax(){

    this.quantity=this.maxQty;

  }

  /* quantity slider */

  onSliderChange(value:number){

    this.quantity=value;

  }

  /* =======================================
      PLACE ORDER
  ========================================*/

placeOrder(){

  if(!this.singeldata) return;

  // Validation
  if(this.isBuy && this.grandTotal > this.availableBalance){
    this.orderSuccess = false;
    this.orderMessage = 'Insufficient wallet balance';
    return;
  }

  const body = {
    coinId: this.singeldata.coinId,
    quantity: this.quantity,
    orderType: (this.isBuy ? 'BUY' : 'SELL') as 'BUY' | 'SELL'
  };

  this.isLoading = true;

  this.orderService.createOrder(this.UserId, body).subscribe({

    next: () => {

      this.isLoading = false;
      this.quantity = 1;

      this.orderSuccess = true;
      this.orderMessage = `${body.orderType} Order Placed Successfully`;

      this.loadWallet();
      this.loadOrders();

      setTimeout(() => {
        this.orderMessage = '';
      }, 3000);

      this.cdr.markForCheck();

    },

    error: (err) => {

      this.isLoading = false;
      this.orderSuccess = false;
      this.orderMessage = err.error?.message || 'Order Failed';

      this.cdr.markForCheck();

    }

  });

}

}

