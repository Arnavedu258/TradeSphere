import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, tap } from 'rxjs';
import { environment } from '../../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class WalletService {

  private API = `${environment.tradingApi}/api/wallet`

  constructor(private http: HttpClient) {}

  // Shared wallet state
  wallet$ = new BehaviorSubject<any>(null);

getWallet(userId: number): Observable<any> {
  return this.http.get(
    `http://localhost:8082/api/wallet?userID=${userId}`
  );
}

  refreshWallet(userId:number){
    this.getWallet(userId).subscribe();
  }

  // Deposit
  deposit(userId:number,amount:number){
    return this.http.put<any>(
      `${this.API}/deposit?userID=${userId}&amount=${amount}`,
      {}
    ).pipe(
      tap(res => this.wallet$.next(res))
    );
  }

  // Transfer
  transfer(sender:number,receiver:number,amount:number){

    const body={ amount };

    return this.http.put<any>(
      `${this.API}/transfer?SenderId=${sender}&RecieverID=${receiver}`,
      body
    ).pipe(
      tap(res => this.wallet$.next(res))
    );
  }

  // Buy / Sell payment
  payOrder(userId:number,orderId:number){

    return this.http.put<any>(
      `${this.API}/pay?userID=${userId}&orderID=${orderId}`,
      {}
    ).pipe(
      tap(res => this.wallet$.next(res))
    );
  }

  // Transaction history
  loadTransaction(userId:number){
    return this.http.get<any[]>(
      `${this.API}/transaction?userID=${userId}`
    );
  }
}