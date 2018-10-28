import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    ITComponent,
    ITDetailComponent,
    ITUpdateComponent,
    ITDeletePopupComponent,
    ITDeleteDialogComponent,
    iTRoute,
    iTPopupRoute
} from './';

import { ItPipe } from 'app/entities/it/itPipe';
import { BrowserModule } from '@angular/platform-browser';

const ENTITY_STATES = [...iTRoute, ...iTPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule],
    declarations: [ITComponent, ITDetailComponent, ITUpdateComponent, ITDeleteDialogComponent, ITDeletePopupComponent, ItPipe],
    entryComponents: [ITComponent, ITUpdateComponent, ITDeleteDialogComponent, ITDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxITModule {}
