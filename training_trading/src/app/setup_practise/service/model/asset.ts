export interface Asset {
 id: number;
 
  BuyPrice: number;
  Quantity: number;

  coin: {
    id: number;
    coinId: string;
    symbol: string;
    name: string;
    image: string;
    currentPrice: number;
    marketCap: number;
    marketCapRank: number;
    priceChangePercentage24h: number;
  };

  user: {
    id: number;
    userName: string;
    userEmail: string;
  };
}
