import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ImageComponent } from './image.component'
import { ImagesComponent } from './images.component';
import { InitializedImageListComponent } from './initialized-image-list.component';
import { ImageService } from './image.service';

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
        ImageService
    ]
})
export class ImageModule { }
