import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ISALE } from 'app/shared/model/sale.model';
import { SALEService } from './sale.service';

@Component({
    selector: 'jhi-sale-update',
    templateUrl: './sale-update.component.html'
})
export class SALEUpdateComponent implements OnInit {
    private _sALE: ISALE;
    isSaving: boolean;

    constructor(private sALEService: SALEService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ sALE }) => {
            this.sALE = sALE;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.sALE.id !== undefined) {
            this.subscribeToSaveResponse(this.sALEService.update(this.sALE));
        } else {
            this.subscribeToSaveResponse(this.sALEService.create(this.sALE));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ISALE>>) {
        result.subscribe((res: HttpResponse<ISALE>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get sALE() {
        return this._sALE;
    }

    set sALE(sALE: ISALE) {
        this._sALE = sALE;
    }
}
