import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Image } from '../models/image';
import { Observable } from 'rxjs/Observable';
import { ServiceBase } from '../service/basic-service';

@Injectable()
export class ImageService extends ServiceBase {

    constructor(private http: Http) { super() }

    imagesEndpoint = 'http://localhost:8080/crawler-app/images';

    images() : Observable<Image[]> {
        return this.http
            .get(this.imagesEndpoint)
            .map(this.extractData)
            .catch(this.handleError)
    }

    /**
    * Atrapa na potrzeby testów
    */
    imagesMock() : Observable<Image[]> {
        let images: Image[] = [];
        let imageURL = 'https://img-9gag-fun.9cache.com/photo/a6bWMX8_700b.jpg';
        for(let i = 0; i < 10; ++i) {
            images.push(new Image(i + 1, imageURL, i + 10, i + 20, new Date(Date.now())));
        }
        return Observable
            .from([images])
            .delay(images.length * 100);
    }
}
