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
import { BrowserModule } from '@angular/platform-browser';
import { OtherPipe } from 'app/entities/other/otherPipe';

const ENTITY_STATES = [...oTHERRoute, ...oTHERPopupRoute];

@NgModule({
    imports: [AccessMuxSharedModule, RouterModule.forChild(ENTITY_STATES), BrowserModule],
    declarations: [
        OTHERComponent,
        OTHERDetailComponent,
        OTHERUpdateComponent,
        OTHERDeleteDialogComponent,
        OTHERDeletePopupComponent,
        OtherPipe
    ],
    entryComponents: [OTHERComponent, OTHERUpdateComponent, OTHERDeleteDialogComponent, OTHERDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxOTHERModule {}
