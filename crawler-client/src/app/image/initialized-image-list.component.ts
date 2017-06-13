import { Component, OnInit } from '@angular/core';
import { ImageService } from './image.service';
import { Image } from './../models/image';

@Component({
    selector: 'initialized-image-list',
    template: '<images [images]="images"></images>',
    providers: [ ImageService ]
})
export class InitializedImageListComponent implements OnInit {

    images: Image[] = [];

    constructor(private imageService: ImageService) { }

    ngOnInit() {
        this.imageService.imagesMock()
            .subscribe(
                images => this.images = images,
                errors => console.log(JSON.stringify(errors))
            );
    }
}
