import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageComponent } from './image.component'
import { ImagesComponent } from './images.component';
import { InitializedImageListComponent } from './initialized-image-list.component';
import { ImagesService } from './images.service';

@NgModule({
    imports: [
        CommonModule
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
