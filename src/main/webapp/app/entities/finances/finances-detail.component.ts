import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFINANCES } from 'app/shared/model/finances.model';

@Component({
    selector: 'jhi-finances-detail',
    templateUrl: './finances-detail.component.html'
})
export class FINANCESDetailComponent implements OnInit {
    fINANCES: IFINANCES;

    constructor(private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fINANCES }) => {
            this.fINANCES = fINANCES;
        });
    }

    previousState() {
        window.history.back();
    }
}
