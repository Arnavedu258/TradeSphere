import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { Coin } from './model/coin';
import { environment } from '../../../environment/environment';

export interface CandleDTO {
  time: number;
  open: number;
  high: number;
  low: number;
  close: number;
}

@Injectable({
  providedIn: 'root'
})
export class MarketService {
    constructor(private http:HttpClient){ }
  getdata():Observable<any>{
    return this.http.get(`${environment.tradingApi}/marketdata`);
  }

  
  SingleTro(coinId:any):Observable<Coin>{
    return this.http.get<Coin>(`${environment.tradingApi}/marketdata/${coinId}`);

  }

  getchart(coinId:any,date:number):Observable<CandleDTO[]>{
    return this.http.get<CandleDTO[]>(`${environment.tradingApi}/charts/${coinId}?day=${date}`);
    


  }
}
