package com.tutorial.trading.service.OrderServices;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tutorial.trading.Repository.OrderItemrepo;
import com.tutorial.trading.Repository.OrderRepository;
import com.tutorial.trading.model.Order;
import com.tutorial.trading.model.OrderItem;
import com.tutorial.trading.model.OrderStatus;
import com.tutorial.trading.model.OrderType;
import com.tutorial.trading.model.User;
import com.tutorial.trading.model.coinEntity;
import com.tutorial.trading.model.Assets.Assets;
import com.tutorial.trading.service.concutrade;
import com.tutorial.trading.service.AssetsSerivces.AssetSerivce;
import com.tutorial.trading.service.wallet.WalletService;

import jakarta.transaction.Transactional;
@Service
public class OrderServiceIMP implements OrderService {


    @Autowired
    private OrderRepository orderrepo;

    
    @Autowired 
    private OrderItemrepo orderIterepo;
    @Autowired 
    private concutrade coincurrent;

    @Autowired 
    private WalletService walletservice;
    @Autowired
    private AssetSerivce Assetservice;
    @Override
    public Order CreateOrder(User user, OrderItem orderItem, OrderType orderType) {

        // isma hum orderitem->coinENtityka price * orderItem  -> uskaquantity
        // 256*quantity=>2   ==512
        // is tara ka karna haa
     Double price=orderItem.getCoinEntity().getCurrentPrice()*orderItem.getQuantity();

Order order=new Order();
order.setUser(user);
order.setOrderItem(orderItem);
order.setOrdertype(orderType);
order.setPrice(BigDecimal.valueOf(price));
order.setTimestamp(LocalDateTime.now());
order.setStatus(OrderStatus.PENDING);

// abb ye hum repo me save java order ka database haa

return orderrepo.save(order);
    }
  @Override
    public Order  GetorderBYID(Long OrderID) {
return orderrepo.findById(OrderID).orElseThrow(()->
new RuntimeException("order not found"));
    }
    // now hum order item create karega by method 

    private OrderItem CreateOrderItem(Double quantity,Double buyPrice,double sellPrice,coinEntity coinEntity) {
        OrderItem orderit=new OrderItem();
        orderit.setBuyPrice(buyPrice);

        orderit.setSellPrice(sellPrice);
        orderit.setCoinider(coinEntity.getCoinId());
        orderit.setImage(coinEntity.getImage());

            orderit.setName(coinEntity.getName());
        orderit.setName(coinEntity.getName());

        orderit.setCoinEntity(coinEntity);
        

        orderit.setQuantity(quantity);
        
        return orderIterepo.save(orderit);
    }
@Transactional
    public Order buyAsset(coinEntity coin,double quantity,User user)throws Exception{

        if(quantity<=0){
            throw new Exception("quantity must be greater then zero");
        }

        
        double buyprice=coin.getCurrentPrice();

        coinEntity coinser=coincurrent.Particularcoin(coin.getCoinId());


        OrderItem orderItem=CreateOrderItem(quantity,buyprice,0.0,coinser);

        Order order=CreateOrder(user,orderItem,OrderType.BUY);
    
        orderItem.setOrder(order);

        // aab hum assest used to buy 
walletservice.PayOrderPayment(order,user);
order.setStatus(OrderStatus.SUCCESS);
order.setOrdertype(OrderType.BUY);
    
Order ordersave=orderrepo.save(order);

// aab hum create asset

Assets oldassets=Assetservice.findAsssetByUserIdandCoinID(order.getUser().getId(),order.getOrderItem().getCoinEntity().getCoinId());


if(oldassets==null){
  Assetservice.createAsset(user, orderItem.getCoinEntity(),orderItem.getQuantity());
}
else{
    Assetservice.updateAsset(oldassets.getId(), quantity);
}

        return ordersave;

        
    }
@Transactional
    public Order sellAsset(coinEntity coin,double quantity,User user)throws Exception{

        if(quantity<=0){
            throw new Exception("quantity must be greater then zero");
        }
Assets assestToSEll=Assetservice.findAsssetByUserIdandCoinID(user.getId(), coin.getCoinId());

 if(assestToSEll == null){
    throw new Exception("Asset not found");
 }

 if(assestToSEll.getQuantity()<quantity){
       throw new Exception("Insufficient quantity of asset to sell");
 }

if(assestToSEll != null){

    double buyprice=assestToSEll.getBuyPrice();
    double SellPreice=coin.getCurrentPrice();


        OrderItem orderItem=CreateOrderItem(quantity, buyprice, SellPreice, coin);
        Order order=CreateOrder(user,orderItem,OrderType.SELL);
orderItem.setOrder(order);

   if(assestToSEll.getQuantity()>=quantity){
    order.setStatus(OrderStatus.SUCCESS);
    order.setOrdertype(OrderType.SELL);
   Order saveorder= orderrepo.save(order);
    // now hum order payment 
    walletservice.PayOrderPayment(order, user);
// aab if i sell all asset so vo remove hojaya user 

    Assets updateAsset=Assetservice.updateAsset(assestToSEll.getId(),-quantity);

    if(updateAsset.getQuantity()*coin.getCurrentPrice()<1){
        Assetservice.deleteAsset(updateAsset.getId());
    }

        return saveorder;

}



throw new Exception("Insufficent quantity of asset to sell");

}
throw new Exception("asset not fOund");

}

    @Override
    public List<Order> getallOrderOfUSer(Long userId, OrderType orderType,String AssetSymbol)  {
        // TODO Auto-generated method stub
        return orderrepo.findByUserId(userId);
    }

    @Override
    @Transactional
    public Order ProcessOrder(coinEntity coin, double quantity, OrderType orderType, User user)throws Exception {
    //    aab isme hum check karega ki orderasset buy or sell

    if(orderType.equals(OrderType.BUY)){
        return buyAsset(coin,quantity,user);
    } 

    else if(orderType.equals(OrderType.SELL)){
        return sellAsset(coin,quantity,user);
    }

        throw new RuntimeException("invalid order type");
    
    }
    }
    

