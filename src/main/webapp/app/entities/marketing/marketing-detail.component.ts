import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IMARKETING } from 'app/shared/model/marketing.model';

@Component({
    selector: 'jhi-marketing-detail',
    templateUrl: './marketing-detail.component.html'
})
export class MARKETINGDetailComponent implements OnInit {
    mARKETING: IMARKETING;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mARKETING }) => {
            this.mARKETING = mARKETING;
        });
    }

    previousState() {
        window.history.back();
    }
}
