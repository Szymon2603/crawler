import { Component, OnInit } from '@angular/core';
import { CrawlerService } from './crawler.service'
import { Image } from './../models/image';
import { ConfigPackageMaster } from '../models/config-package-master'
import { Observable } from 'rxjs/Observable';

@Component({
    selector: 'crawler-runner',
    templateUrl: 'crawler-runner.component.html',
    providers: [ CrawlerService ]
})
export class CrawlerRunnerComponent implements OnInit {

    configs: ConfigPackageMaster[];
    //TODO: Remove default values (move to placeholder)
    startUrl: string = 'https://9gag.com/gag/amYEoMj';
    maxVisits: number = 1;
    configId: number;
    images: Image[] = [];
    mode = 'Observable';
    activeLoader: boolean = false;

    constructor (private crawlerService: CrawlerService) {}

    ngOnInit() {
        this.configs = this.crawlerService.getConfigPackageMaster();
         this.configId = this.configs[0].id;
    }

    //TODO: Do poprawy obsługa błędów
    onSubmit() {
        this.activeLoader = true;
        this.crawlerService.runCrawler(this.startUrl, this.configId, this.maxVisits)
                                         .subscribe(
                                             images => {
                                                 this.images = this.images.concat(images);
                                                 this.activeLoader = false;
                                             },
                                             errors => console.log(JSON.stringify(errors)),
                                             () => this.activeLoader = false
                                         );
    }
}
