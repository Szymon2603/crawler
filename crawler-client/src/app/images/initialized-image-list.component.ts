import { Component, OnInit } from '@angular/core';
import { ImagesService } from './images.service';
import { Image } from './../models/image';

@Component({
    selector: 'initialized-image-list',
    template: '<images [images]="images"></images>'
})
export class InitializedImageListComponent implements OnInit {

    images: Image[] = [];

    constructor(private imageService: ImagesService) { }

    ngOnInit() {
        this.imageService.imagesMock()
            .subscribe(
                images => this.images = images,
                errors => console.log(JSON.stringify(errors))
            );
    }
}
