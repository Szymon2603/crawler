import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { CrawlerRunnerComponent } from './crawler/crawler-runner.component';
import { CrawlerService } from './crawler/crawler.service';
import { ImageModule } from './image/image.module';
import { LoaderComponent } from './commons/loader.component';

import { AppRoutingModule } from './app-routing.module';
import { ImageRoutingModule } from './image/image-routing.module';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        ImageModule,
        ImageRoutingModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        CrawlerRunnerComponent,
        LoaderComponent],
    bootstrap: [AppComponent]
})
export class AppModule { }
