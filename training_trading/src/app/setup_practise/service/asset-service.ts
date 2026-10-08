import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Asset } from './model/asset';
import { Order } from './model/order';

@Injectable({
  providedIn: 'root'
})
export class AssetService {

  private assetApi = 'http://localhost:8082/api/assets';
  private orderApi = 'http://localhost:8082/api/orders';

  constructor(private http: HttpClient) {}

  getUserAssets(userId: number): Observable<Asset[]> {
    return this.http.get<Asset[]>(
      `${this.assetApi}?userid=${userId}`
    );
  }

  // SELL ASSET
  seltAsset(userId: number, coin: any, qty: number): Observable<Order> {

    const body = {
      coin: {
        coinId: coin.coinId
      },
      quantity: qty,
      user: {
        id: userId
      }
    };

    return this.http.put<Order>(
      `${this.orderApi}/sell`,
      body
    );
  }
}