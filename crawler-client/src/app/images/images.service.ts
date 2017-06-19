import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Image } from '../models/image';
import { Observable } from 'rxjs/Observable';
import { ServiceBase } from '../commons/services/basic-service';

@Injectable()
export class ImagesService extends ServiceBase {

    constructor(private http: Http) {
        super();
        this.initImagesMockList();
    }

    imagesEndpoint = 'http://localhost:8080/crawler-app/images';

    images(): Observable<Image[]> {
        return this.http
            .get(this.imagesEndpoint)
            .map(this.extractData)
            .catch(this.handleError)
    }

    private imagesMockList: Image[] = [];

    private initImagesMockList(): void {
        if(this.imagesMockList.length == 0) {
            let imageURL = 'https://img-9gag-fun.9cache.com/photo/a6bWMX8_700b.jpg';
            for(let i = 0; i < 10; ++i) {
                this.imagesMockList.push(new Image(i + 1, imageURL, i + 10, i + 20, new Date(Date.now())));
            }
        }
    }

    /**
    * Atrapa na potrzeby testów
    */
    imagesMock(): Observable<Image[]> {
        return Observable
            .from([this.imagesMockList])
            .delay(this.imagesMockList.length * 10);
    }

    /**
    * Atrapa na potrzeby testów
    */
    addImageMock(image: Image): void {
        let ids: number[] = this.imagesMockList.map(v => {
            return v.id;
        })
        let lastId = Math.max.apply(ids);
        image.id = lastId;
        this.imagesMockList.push(image);
    }
}
