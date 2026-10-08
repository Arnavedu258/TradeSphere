import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';

export interface CreateOrderReq {
  coinId: string;
  quantity: number;
  orderType: 'BUY' | 'SELL';
}

export interface SellRequest {
  coin: string;
  quantity: number;
  user: number;
}

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  private API = `${environment.tradingApi}/api/orders`;

  constructor(private http: HttpClient) {}

  // Create BUY / SELL order
    createOrder(userId: number, body: CreateOrderReq): Observable<any> {
    return this.http.post<any>(
      `${this.API}/payment?UserID=${userId}`,
      body
    );
  }

  // Order History
  getUserOrders(
    userId: number,
    orderType: 'BUY' | 'SELL',
    symbol: string
  ): Observable<any[]> {

    return this.http.get<any[]>(
      `${this.API}?USerID=${userId}&OrderType=${orderType}&Asset_Symbol=${symbol}`
    );
  }

  // Single Order
  getOrderById(orderId: number): Observable<any> {
    return this.http.get<any>(
      `${this.API}/orders/${orderId}`
    );
  }

  // SELL Asset
  sellAsset(req: SellRequest): Observable<any> {
    return this.http.put<any>(
      `${this.API}/sell`,
      req
    );
  }
}