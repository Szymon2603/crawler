import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms';
import { HttpModule } from '@angular/http';

import { AppComponent }  from './app.component';
import { CrawlerRunnerComponent } from './crawler/crawler-runner.component';
import { ImagesModule } from './images/images.module';
import { LoaderComponent } from './commons/components/loader.component';

import { AppRoutingModule } from './app-routing.module';

@NgModule({
    imports: [
        BrowserModule,
        FormsModule,
        HttpModule,
        ImagesModule,
        AppRoutingModule
    ],
    declarations: [
        AppComponent,
        CrawlerRunnerComponent,
        LoaderComponent],
    bootstrap: [
        AppComponent
    ]
})
export class AppModule { }
