import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IHR } from 'app/shared/model/hr.model';
import { Principal } from 'app/core';
import { HRService } from './hr.service';

@Component({
    selector: 'jhi-hr',
    templateUrl: './hr.component.html'
})
export class HRComponent implements OnInit, OnDestroy {
    hRS: IHR[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private hRService: HRService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.hRService.query().subscribe(
            (res: HttpResponse<IHR[]>) => {
                this.hRS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInHRS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IHR) {
        return item.id;
    }

    registerChangeInHRS() {
        this.eventSubscriber = this.eventManager.subscribe('hRListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
