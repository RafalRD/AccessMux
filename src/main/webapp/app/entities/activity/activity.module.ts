import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    ACTIVITYComponent,
    ACTIVITYDetailComponent,
    ACTIVITYUpdateComponent,
    ACTIVITYDeletePopupComponent,
    ACTIVITYDeleteDialogComponent,
    aCTIVITYRoute,
    aCTIVITYPopupRoute
} from './';

const ENTITY_STATES = [...aCTIVITYRoute, ...aCTIVITYPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        ACTIVITYComponent,
        ACTIVITYDetailComponent,
        ACTIVITYUpdateComponent,
        ACTIVITYDeleteDialogComponent,
        ACTIVITYDeletePopupComponent
    ],
    entryComponents: [ACTIVITYComponent, ACTIVITYUpdateComponent, ACTIVITYDeleteDialogComponent, ACTIVITYDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxACTIVITYModule {}
