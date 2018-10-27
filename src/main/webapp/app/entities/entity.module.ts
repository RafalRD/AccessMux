import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AccessMuxITModule } from './it/it.module';
import { AccessMuxHRModule } from './hr/hr.module';
import { AccessMuxFINANCESModule } from './finances/finances.module';
import { AccessMuxMARKETINGModule } from './marketing/marketing.module';
import { AccessMuxSALEModule } from './sale/sale.module';
import { AccessMuxOTHERModule } from './other/other.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AccessMuxITModule,
        AccessMuxHRModule,
        AccessMuxFINANCESModule,
        AccessMuxMARKETINGModule,
        AccessMuxSALEModule,
        AccessMuxOTHERModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AccessMuxEntityModule {}
