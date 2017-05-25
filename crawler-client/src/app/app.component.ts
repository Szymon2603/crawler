import { Component } from '@angular/core';

@Component({
  selector: 'crawler-app',
  template: `<h1>Crawler client app</h1>
             <h3>version: {{version}}`,
})
export class AppComponent  { version = '0.1'; }
