import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IFINANCES } from 'app/shared/model/finances.model';
import { FINANCESService } from './finances.service';

@Component({
    selector: 'jhi-finances-update',
    templateUrl: './finances-update.component.html'
})
export class FINANCESUpdateComponent implements OnInit {
    private _fINANCES: IFINANCES;
    isSaving: boolean;

    constructor(private fINANCESService: FINANCESService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ fINANCES }) => {
            this.fINANCES = fINANCES;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.fINANCES.id !== undefined) {
            this.subscribeToSaveResponse(this.fINANCESService.update(this.fINANCES));
        } else {
            this.subscribeToSaveResponse(this.fINANCESService.create(this.fINANCES));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IFINANCES>>) {
        result.subscribe((res: HttpResponse<IFINANCES>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get fINANCES() {
        return this._fINANCES;
    }

    set fINANCES(fINANCES: IFINANCES) {
        this._fINANCES = fINANCES;
    }
}
