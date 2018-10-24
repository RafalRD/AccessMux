import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IMARKETING } from 'app/shared/model/marketing.model';
import { MARKETINGService } from './marketing.service';

@Component({
    selector: 'jhi-marketing-update',
    templateUrl: './marketing-update.component.html'
})
export class MARKETINGUpdateComponent implements OnInit {
    private _mARKETING: IMARKETING;
    isSaving: boolean;

    constructor(private mARKETINGService: MARKETINGService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ mARKETING }) => {
            this.mARKETING = mARKETING;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.mARKETING.id !== undefined) {
            this.subscribeToSaveResponse(this.mARKETINGService.update(this.mARKETING));
        } else {
            this.subscribeToSaveResponse(this.mARKETINGService.create(this.mARKETING));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IMARKETING>>) {
        result.subscribe((res: HttpResponse<IMARKETING>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get mARKETING() {
        return this._mARKETING;
    }

    set mARKETING(mARKETING: IMARKETING) {
        this._mARKETING = mARKETING;
    }
}
