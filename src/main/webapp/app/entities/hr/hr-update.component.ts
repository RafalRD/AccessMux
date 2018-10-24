import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IHR } from 'app/shared/model/hr.model';
import { HRService } from './hr.service';

@Component({
    selector: 'jhi-hr-update',
    templateUrl: './hr-update.component.html'
})
export class HRUpdateComponent implements OnInit {
    private _hR: IHR;
    isSaving: boolean;

    constructor(private hRService: HRService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hR }) => {
            this.hR = hR;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hR.id !== undefined) {
            this.subscribeToSaveResponse(this.hRService.update(this.hR));
        } else {
            this.subscribeToSaveResponse(this.hRService.create(this.hR));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHR>>) {
        result.subscribe((res: HttpResponse<IHR>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get hR() {
        return this._hR;
    }

    set hR(hR: IHR) {
        this._hR = hR;
    }
}
