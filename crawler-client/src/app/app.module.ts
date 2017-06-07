import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { CrawlerRunnerComponent } from './crawler/crawler-runner.component';
import { ImagesComponent } from './image/images.component';

@NgModule({
  imports:      [ BrowserModule, FormsModule, HttpModule ],
  declarations: [ AppComponent, CrawlerRunnerComponent, ImagesComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
