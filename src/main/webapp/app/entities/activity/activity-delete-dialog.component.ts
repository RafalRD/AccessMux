import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IACTIVITY } from 'app/shared/model/activity.model';
import { ACTIVITYService } from './activity.service';

@Component({
    selector: 'jhi-activity-delete-dialog',
    templateUrl: './activity-delete-dialog.component.html'
})
export class ACTIVITYDeleteDialogComponent {
    aCTIVITY: IACTIVITY;

    constructor(private aCTIVITYService: ACTIVITYService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.aCTIVITYService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'aCTIVITYListModification',
                content: 'Deleted an aCTIVITY'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-activity-delete-popup',
    template: ''
})
export class ACTIVITYDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ aCTIVITY }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ACTIVITYDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.aCTIVITY = aCTIVITY;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate([{ outlets: { popup: null } }], { replaceUrl: true, queryParamsHandling: 'merge' });
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
