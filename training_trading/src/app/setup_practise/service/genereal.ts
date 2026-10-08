import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Coin } from '../componets/dash/chartset/chartset';
import { CandleDTO } from './market-service';


@Injectable({
  providedIn: 'root',
})
export class Genereal {

  constructor(private http:HttpClient){ }
  getdata():Observable<any>{
    return this.http.get("http://localhost:8082/marketdata");
  }

  SingleTro(coinId:any):Observable<Coin>{
    return this.http.get<Coin>(`http://localhost:8082/marketdata/${coinId}`);

  }

  getchart(coinId:any,date:number):Observable<CandleDTO[]>{
    return this.http.get<CandleDTO[]>(`http://localhost:8082/charts/${coinId}?day=${date}`);
    


  }
}
