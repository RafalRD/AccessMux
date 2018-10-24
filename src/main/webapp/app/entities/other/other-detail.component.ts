import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IOTHER } from 'app/shared/model/other.model';

@Component({
    selector: 'jhi-other-detail',
    templateUrl: './other-detail.component.html'
})
export class OTHERDetailComponent implements OnInit {
    oTHER: IOTHER;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ oTHER }) => {
            this.oTHER = oTHER;
        });
    }

    previousState() {
        window.history.back();
    }
}
