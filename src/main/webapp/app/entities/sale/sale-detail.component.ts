import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISALE } from 'app/shared/model/sale.model';

@Component({
    selector: 'jhi-sale-detail',
    templateUrl: './sale-detail.component.html'
})
export class SALEDetailComponent implements OnInit {
    sALE: ISALE;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ sALE }) => {
            this.sALE = sALE;
        });
    }

    previousState() {
        window.history.back();
    }
}
