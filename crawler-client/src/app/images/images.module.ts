import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { ImageComponent } from './image.component'
import { ImagesComponent } from './images.component';
import { InitializedImageListComponent } from './initialized-image-list.component';
import { ImagesService } from './images.service';
import { ImagesRoutingModule } from './images-routing.module';

@NgModule({
    imports: [
        CommonModule,
        ImagesRoutingModule
    ],
    declarations: [
        ImageComponent,
        ImagesComponent,
        InitializedImageListComponent
    ],
    exports: [
        ImagesComponent,
        InitializedImageListComponent
    ],
    providers: [
        ImagesService
    ]
})
export class ImagesModule { }
