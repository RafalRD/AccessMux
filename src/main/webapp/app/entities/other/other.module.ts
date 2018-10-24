import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AccessMuxSharedModule } from 'app/shared';
import {
    OTHERComponent,
    OTHERDetailComponent,
    OTHERUpdateComponent,
    OTHERDeletePopupComponent,
    OTHERDeleteDialogComponent,
    oTHERRoute,
    oTHERPopupRoute
} from './';

const ENTITY_STATES = [...oTHERRoute, ...oTHERPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [OTHERComponent, OTHERDetailComponent, OTHERUpdateComponent, OTHERDeleteDialogComponent, OTHERDeletePopupComponent],
    entryComponents: [OTHERComponent, OTHERUpdateComponent, OTHERDeleteDialogComponent, OTHERDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxOTHERModule {}
