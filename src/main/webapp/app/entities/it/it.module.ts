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

const ENTITY_STATES = [...iTRoute, ...iTPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [ITComponent, ITDetailComponent, ITUpdateComponent, ITDeleteDialogComponent, ITDeletePopupComponent],
    entryComponents: [ITComponent, ITUpdateComponent, ITDeleteDialogComponent, ITDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxITModule {}
