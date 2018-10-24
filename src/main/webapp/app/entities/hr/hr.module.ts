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

const ENTITY_STATES = [...hRRoute, ...hRPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [HRComponent, HRDetailComponent, HRUpdateComponent, HRDeleteDialogComponent, HRDeletePopupComponent],
    entryComponents: [HRComponent, HRUpdateComponent, HRDeleteDialogComponent, HRDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxHRModule {}
