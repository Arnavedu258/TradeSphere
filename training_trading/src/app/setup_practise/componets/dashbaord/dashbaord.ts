import { ChangeDetectorRef, Component, OnInit, ChangeDetectionStrategy } from '@angular/core';
import { Genereal } from '../../service/genereal';

import { RouterOutlet } from '@angular/router';
import { Sidebar } from '../../header/sidebar/sidebar';
import { Navbar } from '../../header/navbar/navbar';

@Component({
  selector: 'app-dashbaord',
  imports: [RouterOutlet, Sidebar, Navbar],
  templateUrl: './dashbaord.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './dashbaord.scss',
})
export class Dashbaord {}
