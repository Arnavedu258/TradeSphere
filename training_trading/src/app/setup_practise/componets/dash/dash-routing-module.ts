import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { Dash1 } from './dash1/dash1';
import { Paramdash } from './paramdash/paramdash';
import { WalletCompoent } from './wallet-compoent/wallet-compoent';
import { TradeAct } from './trade-act/trade-act';
import { WithdrawComponent } from './withdraw-component/withdraw-component';
import { OrderComponent } from './order-component/order-component';
import { Asset } from './asset/asset';
import { Portfolio } from './portfolio/portfolio';

const routes: Routes = [
 {
    path: '',
    component: Dash1
  },

  // Market List
  {
    path: 'market',
    component: Dash1
  },

  // Trading Terminal
  {
    path: 'data/:id',
    component: Paramdash
  },

  // Wallet
  {
    path: 'wallet',
    component: WalletCompoent
  },

  // Trade Activity
  {
    path: 'activity',
    component: TradeAct
  },

  
  // Assets Portfolio
  {
    path: 'portfolio',
    component:Portfolio
  },

  // Orders
  {
    path: 'orders',
    component: OrderComponent
  },

  // Withdraw
  {
    path: 'withdraw',
    component: WithdrawComponent
  }



];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class DashRoutingModule {}
