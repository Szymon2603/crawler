import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Image } from '../models/image';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ImageService {

    constructor(private http: Http) { }

    imagesEndpoint = 'http://localhost:8080/crawler-app/images';

    images() : Observable<Image[]> {
        return this.http
            .get(this.imagesEndpoint)
            .map(this.extractData)
            .catch(this.handleError)
    }

    private extractData(res: Response) {
        let body = res.json();
        return body.data || {};
    }

    private handleError(error: Response | any) {
        let errMsg: string;
        if (error instanceof Response) {
            const body = error.json() || '';
            const err = body.error || JSON.stringify(body);
            errMsg = `${error.status} - ${error.statusText || ''} ${err}`;
        } else {
            errMsg = error.message ? error.message : error.toString();
        }
        console.error(errMsg);
        return Observable.throw(errMsg);
    }
}
