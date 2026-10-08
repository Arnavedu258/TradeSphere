import { Component, ChangeDetectionStrategy } from '@angular/core';

import { RouterLink, RouterLinkActive } from '@angular/router';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { faImages } from '@fortawesome/free-solid-svg-icons';
import { faChartSimple } from '@fortawesome/free-solid-svg-icons';
import { faDatabase } from '@fortawesome/free-solid-svg-icons';
import { faComment } from '@fortawesome/free-solid-svg-icons';
import { faCalendar } from '@fortawesome/free-solid-svg-icons';
import { faFileInvoice } from '@fortawesome/free-solid-svg-icons';
import { faShop } from '@fortawesome/free-solid-svg-icons';
import { faCreditCard } from '@fortawesome/free-solid-svg-icons';
import { NgStyle } from '@angular/common';
import { faHouse } from '@fortawesome/free-solid-svg-icons';
import { faClipboardList } from '@fortawesome/free-solid-svg-icons';
import { faWallet } from '@fortawesome/free-solid-svg-icons';
import { faMoneyBillTransfer } from '@fortawesome/free-solid-svg-icons';
import { faArrowTrendUp } from '@fortawesome/free-solid-svg-icons';
@Component({
  selector: 'app-sidebar',
  imports: [RouterLink, FontAwesomeModule,RouterLinkActive],
  templateUrl: './sidebar.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './sidebar.scss',
})
export class Sidebar {
  faArrowTrendUp=faArrowTrendUp;
  faMoneyBillTransfer=faMoneyBillTransfer;
  faWallet=faWallet;
  faClipboardList=faClipboardList;
  faHouse=faHouse;
  faUser = faUser;
  faImages = faImages;
  faChartSimple = faChartSimple;
  faDatabase = faDatabase;
  faShop = faShop;
  faCalendar = faCalendar;
  faFileInvoice = faFileInvoice;
  faComment = faComment;
  faCreditCard = faCreditCard;
}
