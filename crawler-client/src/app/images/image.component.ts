import { Component, Input, AfterContentInit } from '@angular/core';
import { Image } from './../models/image';
import { ImagesService } from './images.service';

@Component({
    selector: 'image',
    templateUrl: 'image.component.html',
    styleUrls: ['image.component.scss']
})
export class ImageComponent implements AfterContentInit {
    @Input() image: Image;
    isNew: boolean;

    constructor(private imagesService: ImagesService) { }

    ngAfterContentInit() {
        this.isNew = this.image.id == undefined || this.image.id == null;
    }

    add() {
        console.log('Dodaję ' + this.image.toString())
        this.imagesService.addImageMock(this.image);
        this.isNew = false;
    }
}
