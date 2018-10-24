import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISALE } from 'app/shared/model/sale.model';
import { Principal } from 'app/core';
import { SALEService } from './sale.service';

@Component({
    selector: 'jhi-sale',
    templateUrl: './sale.component.html'
})
export class SALEComponent implements OnInit, OnDestroy {
    sALES: ISALE[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private sALEService: SALEService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.sALEService.query().subscribe(
            (res: HttpResponse<ISALE[]>) => {
                this.sALES = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInSALES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISALE) {
        return item.id;
    }

    registerChangeInSALES() {
        this.eventSubscriber = this.eventManager.subscribe('sALEListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
