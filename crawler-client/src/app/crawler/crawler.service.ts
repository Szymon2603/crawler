import { Injectable } from '@angular/core';
import { Http, Response, URLSearchParams } from '@angular/http';
import { Image } from '../models/image';
import { ConfigPackageMaster } from '../models/config-package-master';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class CrawlerService {

    runCrawlerUrl = 'http://localhost:8080/crawler-app/runCrawler'

    constructor(private http: Http) { }

    runCrawler(startUrl: string, configId: number, maxVisits?: number): Observable<Image[]> {
        let params = this.prepareParams(startUrl, configId, maxVisits);
        return this.http
            .get(this.runCrawlerUrl, { search: params })
            .map(this.extractData)
            .catch(this.handleError);
    }

    private prepareParams(startUrl: string, configId: number, maxVisits?: number): URLSearchParams {
        let params = new URLSearchParams();
        params.set('startUrl', startUrl);
        params.set('configId', configId.toString());
        if (maxVisits !== undefined) {
            params.set('maxVisits', maxVisits.toString());
        }
        return params;
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

    /**
    * Atrapa na moment braku komponentu do pobierania konfiguracji. Zostanie uzupełnione po dodanie router-a oraz
    * rozbudowie części serwerowej.
    */
    getConfigPackageMaster(): ConfigPackageMaster[] {
        return [new ConfigPackageMaster(1, '9GAG')];
    }
}
