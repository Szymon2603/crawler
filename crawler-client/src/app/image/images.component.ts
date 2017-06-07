import { Component, Input } from '@angular/core';
import { Image } from './../models/image';

@Component({
    selector: 'images',
    templateUrl: 'images.component.html',
    styleUrls: ['images.component.scss']
})
export class ImagesComponent {
    @Input() images : Image[];
}
