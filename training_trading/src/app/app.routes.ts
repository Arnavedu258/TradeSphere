import { Routes } from '@angular/router';
import { Dashbaord } from './setup_practise/componets/dashbaord/dashbaord';

export const routes: Routes = [
    {
        path:'',
        component:Dashbaord,
        children:[
            {
                path:'',
                redirectTo:'dashboard',
                pathMatch:'full'
            },{
                path:'dashboard',
   loadChildren: ()=>import('./setup_practise/componets/dash/dash-routing-module').then(n=>n.DashRoutingModule)
            }
        ]

     
         
    }
    
];
