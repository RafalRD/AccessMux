import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IHR } from 'app/shared/model/hr.model';

@Component({
    selector: 'jhi-hr-detail',
    templateUrl: './hr-detail.component.html'
})
export class HRDetailComponent implements OnInit {
    hR: IHR;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ hR }) => {
            this.hR = hR;
        });
    }

    previousState() {
        window.history.back();
    }
}
