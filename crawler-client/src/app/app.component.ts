import { Component, ViewEncapsulation } from '@angular/core';

@Component({
  selector: 'crawler-app',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss'],
  encapsulation: ViewEncapsulation.None
})
export class AppComponent  { version = '0.1'; }
