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
}
