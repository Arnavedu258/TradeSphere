import { Component, ChangeDetectionStrategy } from '@angular/core';

import { NgStyle } from '@angular/common';
import { RouterLink } from '@angular/router';
@Component({
  selector: 'app-navbar',
  imports: [RouterLink],

  templateUrl: './navbar.html',
  changeDetection: ChangeDetectionStrategy.Eager,
  styleUrl: './navbar.scss',
})
export class Navbar {}

