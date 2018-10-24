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

const ENTITY_STATES = [...mARKETINGRoute, ...mARKETINGPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        MARKETINGComponent,
        MARKETINGDetailComponent,
        MARKETINGUpdateComponent,
        MARKETINGDeleteDialogComponent,
        MARKETINGDeletePopupComponent
    ],
    entryComponents: [MARKETINGComponent, MARKETINGUpdateComponent, MARKETINGDeleteDialogComponent, MARKETINGDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxMARKETINGModule {}
