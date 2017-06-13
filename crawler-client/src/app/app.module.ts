import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';
import { RouterModule, Routes } from '@angular/router';

import { AppComponent }  from './app.component';
import { CrawlerRunnerComponent } from './crawler/crawler-runner.component';
import { CrawlerService } from './crawler/crawler.service';
import { ImagesComponent } from './image/images.component';
import { InitializedImageListComponent } from './image/initialized-image-list.component';
import { ImageComponent } from './image/image.component';
import { LoaderComponent } from './commons/loader.component';

const routes = [
    { path: 'crawler', component: CrawlerRunnerComponent },
    { path: 'images', component: InitializedImageListComponent }
]

@NgModule({
  imports:      [ BrowserModule, FormsModule, HttpModule, RouterModule.forRoot(routes) ],
  declarations: [ AppComponent, CrawlerRunnerComponent, ImagesComponent, InitializedImageListComponent, LoaderComponent, ImageComponent ],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
