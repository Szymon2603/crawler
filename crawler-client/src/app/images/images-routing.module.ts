import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { InitializedImageListComponent } from './initialized-image-list.component';

const routes: Routes = [
    { path: 'images', component: InitializedImageListComponent }
]

@NgModule({
    imports: [
        RouterModule.forChild(routes)
    ],
    exports: [
        RouterModule
    ]
})
export class ImagesRoutingModule { }
