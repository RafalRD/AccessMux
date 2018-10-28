import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    MARKETINGComponent,
    MARKETINGDetailComponent,
    MARKETINGUpdateComponent,
    MARKETINGDeletePopupComponent,
    MARKETINGDeleteDialogComponent,
    mARKETINGRoute,
    mARKETINGPopupRoute
} from './';
import { BrowserModule } from '@angular/platform-browser';
import { MarketingPipe } from 'app/entities/marketing/marketingPipe';

const ENTITY_STATES = [...mARKETINGRoute, ...mARKETINGPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule],
    declarations: [
        MARKETINGComponent,
        MARKETINGDetailComponent,
        MARKETINGUpdateComponent,
        MARKETINGDeleteDialogComponent,
        MARKETINGDeletePopupComponent,
        MarketingPipe
    ],
    entryComponents: [MARKETINGComponent, MARKETINGUpdateComponent, MARKETINGDeleteDialogComponent, MARKETINGDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxMARKETINGModule {}
