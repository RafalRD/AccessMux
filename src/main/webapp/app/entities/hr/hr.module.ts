import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    HRComponent,
    HRDetailComponent,
    HRUpdateComponent,
    HRDeletePopupComponent,
    HRDeleteDialogComponent,
    hRRoute,
    hRPopupRoute
} from './';
import { HrPipe } from 'app/entities/hr/hrPipe';
import { BrowserModule } from '@angular/platform-browser';

const ENTITY_STATES = [...hRRoute, ...hRPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule],
    declarations: [HRComponent, HRDetailComponent, HRUpdateComponent, HRDeleteDialogComponent, HRDeletePopupComponent, HrPipe],
    entryComponents: [HRComponent, HRUpdateComponent, HRDeleteDialogComponent, HRDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxHRModule {}
