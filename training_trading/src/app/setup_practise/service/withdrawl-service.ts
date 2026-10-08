import { HttpClient } from '@angular/common/http';
import { Injectable, Service } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environment/environment';
 export interface  WithdrawRequest
 {id: number;
  amount: number;
  status: 'PENDING' | 'SUCCESS' | 'DECLINED';
  date: string;
}

@Injectable({
  providedIn: 'root'
})
export class WithdrawlService {
    private API =  `${environment.tradingApi}/api/withdrawl`;

  constructor(private http: HttpClient) {}

  // Create withdrawal request
  create(userId: number, amount: number): Observable<WithdrawRequest> {
    return this.http.post<WithdrawRequest>(
      `${this.API}/withdrawl/${userId}/${amount}`,
      {}
    );
  }

  // User withdrawal history
  history(userId: number): Observable<WithdrawRequest[]> {
    return this.http.get<WithdrawRequest[]>(
      `${this.API}/withdrawl/${userId}`
    );
  }

  // Admin: get all requests
  getAllRequests(): Observable<WithdrawRequest[]> {
    return this.http.get<WithdrawRequest[]>(
      `${this.API}/admin/withdrawl`
    );
  }

  // Admin: approve / reject request
  processRequest(id: number, accept: boolean): Observable<WithdrawRequest> {
    return this.http.patch<WithdrawRequest>(
      `${this.API}/admin/withdrawl/${id}/process/${accept}`,
      {}
    );
  }
}
