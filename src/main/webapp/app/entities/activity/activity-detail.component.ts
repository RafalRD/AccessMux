import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IACTIVITY } from 'app/shared/model/activity.model';

@Component({
    selector: 'jhi-activity-detail',
    templateUrl: './activity-detail.component.html'
})
export class ACTIVITYDetailComponent implements OnInit {
    aCTIVITY: IACTIVITY;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aCTIVITY }) => {
            this.aCTIVITY = aCTIVITY;
        });
    }

    previousState() {
        window.history.back();
    }
}
