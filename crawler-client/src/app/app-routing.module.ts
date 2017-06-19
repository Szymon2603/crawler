import { NgModule } from '@angular/core';
import { CrawlerRunnerComponent } from './crawler/crawler-runner.component';
import { RouterModule, Routes } from '@angular/router';

const routes: Routes = [
    { path: 'crawler', component: CrawlerRunnerComponent },
    { path: '', pathMatch: 'full', redirectTo: '/images' }
]

@NgModule({
    imports: [
        RouterModule.forRoot(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class AppRoutingModule { }
