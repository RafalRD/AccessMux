import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IFINANCES } from 'app/shared/model/finances.model';
import { Principal } from 'app/core';
import { FINANCESService } from './finances.service';

@Component({
    selector: 'jhi-finances',
    templateUrl: './finances.component.html'
})
export class FINANCESComponent implements OnInit, OnDestroy {
    fINANCES: IFINANCES[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private fINANCESService: FINANCESService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.fINANCESService.query().subscribe(
            (res: HttpResponse<IFINANCES[]>) => {
                this.fINANCES = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInFINANCES();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IFINANCES) {
        return item.id;
    }

    registerChangeInFINANCES() {
        this.eventSubscriber = this.eventManager.subscribe('fINANCESListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
