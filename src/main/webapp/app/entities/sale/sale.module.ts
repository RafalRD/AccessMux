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

const ENTITY_STATES = [...sALERoute, ...sALEPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [SALEComponent, SALEDetailComponent, SALEUpdateComponent, SALEDeleteDialogComponent, SALEDeletePopupComponent],
    entryComponents: [SALEComponent, SALEUpdateComponent, SALEDeleteDialogComponent, SALEDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxSALEModule {}
