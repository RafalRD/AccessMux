import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    SALEComponent,
    SALEDetailComponent,
    SALEUpdateComponent,
    SALEDeletePopupComponent,
    SALEDeleteDialogComponent,
    sALERoute,
    sALEPopupRoute
} from './';
import { BrowserModule } from '@angular/platform-browser';
import { SalePipe } from 'app/entities/sale/salePipe';

const ENTITY_STATES = [...sALERoute, ...sALEPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule],
    declarations: [SALEComponent, SALEDetailComponent, SALEUpdateComponent, SALEDeleteDialogComponent, SALEDeletePopupComponent, SalePipe],
    entryComponents: [SALEComponent, SALEUpdateComponent, SALEDeleteDialogComponent, SALEDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxSALEModule {}
