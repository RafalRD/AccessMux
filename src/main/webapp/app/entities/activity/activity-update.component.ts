import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IACTIVITY } from 'app/shared/model/activity.model';
import { ACTIVITYService } from './activity.service';

@Component({
    selector: 'jhi-activity-update',
    templateUrl: './activity-update.component.html'
})
export class ACTIVITYUpdateComponent implements OnInit {
    private _aCTIVITY: IACTIVITY;
    isSaving: boolean;

    constructor(private aCTIVITYService: ACTIVITYService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ aCTIVITY }) => {
            this.aCTIVITY = aCTIVITY;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.aCTIVITY.id !== undefined) {
            this.subscribeToSaveResponse(this.aCTIVITYService.update(this.aCTIVITY));
        } else {
            this.subscribeToSaveResponse(this.aCTIVITYService.create(this.aCTIVITY));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IACTIVITY>>) {
        result.subscribe((res: HttpResponse<IACTIVITY>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get aCTIVITY() {
        return this._aCTIVITY;
    }

    set aCTIVITY(aCTIVITY: IACTIVITY) {
        this._aCTIVITY = aCTIVITY;
    }
}
