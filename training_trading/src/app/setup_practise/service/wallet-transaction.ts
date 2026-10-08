export interface WalletTransaction {
    id:number;

  walletTransactionType:'BUY'|'SELL'|'DEPOSIT'|'TRANSFER';

  coinName:string;
  coinSymbol:string;
  coinImage:string;

  quantity:number;
  pricePerUnit:number;
  amount:number;
  referenceId: string;
  transferId: string;
  createdAt:string;
    sender: User;
    wallet:{
      balance:number;
    }
  receiver: User;
}
export interface User {
  id: number;
  userName: string;
  userEmail: string;
  
}
