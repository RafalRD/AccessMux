import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IIT } from 'app/shared/model/it.model';
import { ITService } from './it.service';

@Component({
    selector: 'jhi-it-update',
    templateUrl: './it-update.component.html'
})
export class ITUpdateComponent implements OnInit {
    private _iT: IIT;
    isSaving: boolean;

    constructor(private iTService: ITService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ iT }) => {
            this.iT = iT;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.iT.id !== undefined) {
            this.subscribeToSaveResponse(this.iTService.update(this.iT));
        } else {
            this.subscribeToSaveResponse(this.iTService.create(this.iT));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IIT>>) {
        result.subscribe((res: HttpResponse<IIT>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get iT() {
        return this._iT;
    }

    set iT(iT: IIT) {
        this._iT = iT;
    }
}
