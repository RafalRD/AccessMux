import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IIT } from 'app/shared/model/it.model';
import { Principal } from 'app/core';
import { ITService } from './it.service';

@Component({
    selector: 'jhi-it',
    templateUrl: './it.component.html'
})
export class ITComponent implements OnInit, OnDestroy {
    iTS: IIT[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private iTService: ITService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.iTService.query().subscribe(
            (res: HttpResponse<IIT[]>) => {
                this.iTS = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInITS();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IIT) {
        return item.id;
    }

    registerChangeInITS() {
        this.eventSubscriber = this.eventManager.subscribe('iTListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
