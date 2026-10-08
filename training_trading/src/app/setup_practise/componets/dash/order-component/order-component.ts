import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { CommonModule, DecimalPipe, DatePipe, UpperCasePipe } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { OrderService } from '../../../service/order-service';
import { Order } from '../../../service/model/order';
import { forkJoin } from 'rxjs';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';

import {
  faChartLine,
  faCircle,
  faRotateRight,
  faWallet,
  faArrowTrendUp,
  faTrophy,
  faChartColumn,
  faMagnifyingGlass,
  faArrowDown,
  faArrowUp,
  faCircleCheck,
  faBoxOpen,
  faChevronLeft,
  faChevronRight
} from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-order-component',
  standalone: true,
  imports: [CommonModule, FormsModule, DecimalPipe, DatePipe, UpperCasePipe,FontAwesomeModule],
  templateUrl: './order-component.html',
  styleUrls: ['./order-component.scss']
})
export class OrderComponent implements OnInit {

  userId = 1;

  orders:Order[]=[];
  viewOrders:Order[]=[];
  buyOrders: Order[] = [];
sellOrders: Order[] = [];

  selectedType:'BUY'|'SELL'='BUY';
  search='';

  sortBy='date';

  loading=false;

  totalBuy=0;
  totalSell=0;
  totalProfit=0;

  bestTrade=0;
  worstTrade=0;
  winRate=0;

  currentPage=1;
  pageSize=8;
    faChartLine=faChartLine;
  faCircle=faCircle;
  faRotateRight=faRotateRight;
  faWallet=faWallet;
  faArrowTrendUp=faArrowTrendUp;
  faTrophy=faTrophy;
  faChartColumn=faChartColumn;
  faMagnifyingGlass=faMagnifyingGlass;
  faArrowDown=faArrowDown;
  faArrowUp=faArrowUp;
  faCircleCheck=faCircleCheck;
  faBoxOpen=faBoxOpen;
  faChevronLeft=faChevronLeft;
  faChevronRight=faChevronRight;

  constructor(private service:OrderService,private cdr:ChangeDetectorRef){}

  ngOnInit(){
    this.loadOrders();
    this.cdr.markForCheck();
  }

loadOrders() {

  this.loading = true;

  forkJoin({
    buy: this.service.getUserOrders(this.userId, 'BUY', ''),
    sell: this.service.getUserOrders(this.userId, 'SELL', '')
  }).subscribe({

    next: (res) => {

      this.buyOrders = res.buy;
      this.sellOrders = res.sell;

      this.orders = this.selectedType === 'BUY'
        ? this.buyOrders
        : this.sellOrders;

      this.calculateAnalytics();
      this.applyFilters();

      this.loading = false;   // IMPORTANT
      this.cdr.detectChanges();

    },

    error: (err) => {
      console.error(err);
      this.loading = false;   // IMPORTANT
    }

  });

}
  applyFilters(){

    let data=[...this.orders];

    if(this.search.trim()){

      const txt=this.search.toLowerCase();
      

      data=data.filter(x=>

        x.name.toLowerCase().includes(txt) ||

        x.coinider.toLowerCase().includes(txt)

      );

    }

    switch(this.sortBy){

      case 'profit':
        data.sort((a,b)=>this.getProfit(b)-this.getProfit(a));
        break;

      case 'value':
        data.sort((a,b)=>(b.price*b.orderItem.quantity)-(a.price*a.orderItem.quantity));
        break;

      default:
        data.sort((a,b)=>new Date(b.timestamp).getTime()-new Date(a.timestamp).getTime());

    }

    this.viewOrders=data;

    this.currentPage=1;

  }

 calculateAnalytics() {

  this.totalBuy = 0;
  this.totalSell = 0;
  this.totalProfit = 0;

  const allOrders = [...this.buyOrders, ...this.sellOrders];
  const profits: number[] = [];

  allOrders.forEach(o => {

    const value = o.price * o.orderItem.quantity;

    if (o.ordertype === 'BUY') this.totalBuy += value;
    if (o.ordertype === 'SELL') this.totalSell += value;

    const p = this.getProfit(o);
    profits.push(p);
    this.totalProfit += p;

  });

  this.bestTrade = Math.max(...profits, 0);
  this.worstTrade = Math.min(...profits, 0);

  const wins = profits.filter(x => x > 0).length;
  this.winRate = allOrders.length ? (wins / allOrders.length) * 100 : 0;
}

changeType(type: 'BUY' | 'SELL') {

  this.selectedType = type;

  this.orders =
    type === 'BUY'
      ? this.buyOrders
      : this.sellOrders;

  this.applyFilters();
}
getProfit(order: Order): number {
  const qty = order.orderItem.quantity;

  if (order.ordertype === 'BUY') {
    return (order.price - order?.orderItem.BuyPrice) * qty;
  }

  return (order.sellPrice - order?.orderItem.BuyPrice) * qty;
  
}

getOrderValue(order: Order): number {
  return order.price * order.orderItem.quantity;
}

  get paginatedOrders(){

    const start=(this.currentPage-1)*this.pageSize;

    return this.viewOrders.slice(start,start+this.pageSize);

  }

  get totalPages(){

    return Math.ceil(this.viewOrders.length/this.pageSize);

  }

  next(){

    if(this.currentPage<this.totalPages)
      this.currentPage++;

  }

  prev(){

    if(this.currentPage>1)
      this.currentPage--;

  }

}
