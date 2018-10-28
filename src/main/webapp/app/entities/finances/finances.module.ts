import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    FINANCESComponent,
    FINANCESDetailComponent,
    FINANCESUpdateComponent,
    FINANCESDeletePopupComponent,
    FINANCESDeleteDialogComponent,
    fINANCESRoute,
    fINANCESPopupRoute
} from './';
import { BrowserModule } from '@angular/platform-browser';
import { FinansePipe } from 'app/entities/finances/finansePipe';

const ENTITY_STATES = [...fINANCESRoute, ...fINANCESPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule],
    declarations: [
        FINANCESComponent,
        FINANCESDetailComponent,
        FINANCESUpdateComponent,
        FINANCESDeleteDialogComponent,
        FINANCESDeletePopupComponent,
        FinansePipe
    ],
    entryComponents: [FINANCESComponent, FINANCESUpdateComponent, FINANCESDeleteDialogComponent, FINANCESDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxFINANCESModule {}
