import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IIT } from 'app/shared/model/it.model';

@Component({
    selector: 'jhi-it-detail',
    templateUrl: './it-detail.component.html'
})
export class ITDetailComponent implements OnInit {
    iT: IIT;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ iT }) => {
            this.iT = iT;
        });
    }

    previousState() {
        window.history.back();
    }
}
