import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMARKETING } from 'app/shared/model/marketing.model';
import { Principal } from 'app/core';
import { MARKETINGService } from './marketing.service';

@Component({
    selector: 'jhi-marketing',
    templateUrl: './marketing.component.html'
})
export class MARKETINGComponent implements OnInit, OnDestroy {
    mARKETINGS: IMARKETING[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private mARKETINGService: MARKETINGService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.mARKETINGService.query().subscribe(
            (res: HttpResponse<IMARKETING[]>) => {
                this.mARKETINGS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInMARKETINGS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMARKETING) {
        return item.id;
    }

    registerChangeInMARKETINGS() {
        this.eventSubscriber = this.eventManager.subscribe('mARKETINGListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
