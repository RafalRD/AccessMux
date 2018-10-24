import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IOTHER } from 'app/shared/model/other.model';
import { Principal } from 'app/core';
import { OTHERService } from './other.service';

@Component({
    selector: 'jhi-other',
    templateUrl: './other.component.html'
})
export class OTHERComponent implements OnInit, OnDestroy {
    oTHERS: IOTHER[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private oTHERService: OTHERService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.oTHERService.query().subscribe(
            (res: HttpResponse<IOTHER[]>) => {
                this.oTHERS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInOTHERS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IOTHER) {
        return item.id;
    }

    registerChangeInOTHERS() {
        this.eventSubscriber = this.eventManager.subscribe('oTHERListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
