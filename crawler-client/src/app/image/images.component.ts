import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { ImageService } from './image.service';
import { Image } from './../models/image';

@Component({
    selector: 'images',
    templateUrl: 'images.component.html',
    providers: [ ImageService ]
})
export class ImagesComponent implements OnInit {
    @Input() images : Image[];
    fetchData : boolean = false;

    constructor(private imageService: ImageService, private route: ActivatedRoute) { }

    ngOnInit() {
        this.route.data
            .subscribe((data: {fetchData: boolean}) => this.fetchData = data.fetchData)
        if(this.fetchData) {
            this.imageService.images()
                .subscribe(
                    images => this.images = images,
                    errors => console.log(JSON.stringify(errors))
                );
        }
    }
}
