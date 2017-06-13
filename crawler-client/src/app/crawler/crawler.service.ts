import { Injectable } from '@angular/core';
import { Http, URLSearchParams } from '@angular/http';
import { Image } from '../models/image';
import { ConfigPackageMaster } from '../models/config-package-master';
import { Observable } from 'rxjs/Observable';
import { ServiceBase } from '../service/basic-service';

@Injectable()
export class CrawlerService extends ServiceBase {

    runCrawlerUrl = 'http://localhost:8080/crawler-app/runCrawler'

    constructor(private http: Http) { super() }

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

    /**
    * Atrapa na moment braku komponentu do pobierania konfiguracji. Zostanie uzupełnione po dodanie router-a oraz
    * rozbudowie części serwerowej.
    */
    getConfigPackageMaster(): ConfigPackageMaster[] {
        return [new ConfigPackageMaster(1, '9GAG')];
    }
}
