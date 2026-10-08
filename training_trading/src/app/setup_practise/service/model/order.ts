export interface Order {
     id:number;
  price:number;
  status:string;
  ordertype:'BUY'|'SELL';
  timestamp:string;

  coinider:string;
  name:string;

  sellPrice:number;

  orderItem:{
    id:number;
    quantity:number;
     BuyPrice:number;
    image:String
  };

  user:{
    id:number;
    userName:string;
    userEmail:string;
  };
}
