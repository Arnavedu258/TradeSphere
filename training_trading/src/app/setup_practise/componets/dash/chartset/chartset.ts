import { ChangeDetectorRef, Component, OnInit } from '@angular/core';

import { CommonModule, NgStyle } from '@angular/common';
import { CanvasJSAngularChartsModule } from '@canvasjs/angular-charts';
import { Genereal } from '../../../service/genereal';
export interface Coin {
 
  coinId:string;

  name:string;

  symbol:string;

  image:string;

  currentPrice:number;
marketCap:number;
  marketCapRank:number;

  priceChangePercentage24h:number;
}                                                                           

@Component({
  selector: 'app-chartset',
  imports: [ CommonModule, CanvasJSAngularChartsModule,NgStyle],
  templateUrl: './chartset.html',
  styleUrl: './chartset.scss',
})
export class Chartset implements OnInit{
    constructor(private general:Genereal,private cdr:ChangeDetectorRef){}

    datas:any[]=[];

  chart: any;

	chartOptions = {

    backgroundColor: "transparent",

    animationEnabled:true,

    theme:"dark2",

    axisX:{
        lineThickness:0,
        tickLength:0,
        labelFontColor:"#94A3B8",

        gridThickness:1,
        gridDashType:"dash"
    },

    axisY:{
        minimum:10000,
        maximum:40000,

        lineThickness:0,

        tickLength:0,

        labelFontColor:"#94A3B8",

        gridDashType:"dash",

        suffix:"K"
        
    },
      height: 470,

    data:[{

        type:"area",

        color:"#3B82F6",

        fillOpacity:.25,

        markerSize:0,

        lineThickness:3,

        dataPoints:[
            {x:new Date(2018,0),y:18000},
            {x:new Date(2018,6),y:22000},
            {x:new Date(2019,0),y:26000},
            {x:new Date(2020,0),y:35000},
            {x:new Date(2021,0),y:31000},
            {x:new Date(2022,0),y:39000},
        ]

    }]
}

ngOnInit(): void {
    this.general.getdata().subscribe((data:Coin[])=>{
this.datas=data;
this.cdr.markForCheck();
})
}
}                              

