import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMARKETING } from 'app/shared/model/marketing.model';
import { MARKETINGService } from './marketing.service';

@Component({
    selector: 'jhi-marketing-delete-dialog',
    templateUrl: './marketing-delete-dialog.component.html'
})
export class MARKETINGDeleteDialogComponent {
    mARKETING: IMARKETING;

    constructor(private mARKETINGService: MARKETINGService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.mARKETINGService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'mARKETINGListModification',
                content: 'Deleted an mARKETING'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-marketing-delete-popup',
    template: ''
})
export class MARKETINGDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ mARKETING }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MARKETINGDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.mARKETING = mARKETING;
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
