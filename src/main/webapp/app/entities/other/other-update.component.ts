import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IOTHER } from 'app/shared/model/other.model';
import { OTHERService } from './other.service';

@Component({
    selector: 'jhi-other-update',
    templateUrl: './other-update.component.html'
})
export class OTHERUpdateComponent implements OnInit {
    private _oTHER: IOTHER;
    isSaving: boolean;

    constructor(private oTHERService: OTHERService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ oTHER }) => {
            this.oTHER = oTHER;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.oTHER.id !== undefined) {
            this.subscribeToSaveResponse(this.oTHERService.update(this.oTHER));
        } else {
            this.subscribeToSaveResponse(this.oTHERService.create(this.oTHER));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IOTHER>>) {
        result.subscribe((res: HttpResponse<IOTHER>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get oTHER() {
        return this._oTHER;
    }

    set oTHER(oTHER: IOTHER) {
        this._oTHER = oTHER;
    }
}
