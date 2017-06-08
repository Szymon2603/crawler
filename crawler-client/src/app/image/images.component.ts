import { Component, Input } from '@angular/core';
import { Image } from './../models/image';

@Component({
    selector: 'images',
    templateUrl: 'images.component.html'
})
export class ImagesComponent {
    @Input() images : Image[];
}
