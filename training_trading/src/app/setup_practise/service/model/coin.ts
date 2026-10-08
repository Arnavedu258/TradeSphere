export interface Coin {
  coinId: string;
  name: string;
  symbol: string;
  image: string;
  currentPrice: number;
  marketCapRank: number;
  marketCap:number;
  
  priceChangePercentage24h: number;
}

