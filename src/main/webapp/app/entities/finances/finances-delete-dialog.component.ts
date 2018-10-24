import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFINANCES } from 'app/shared/model/finances.model';
import { FINANCESService } from './finances.service';

@Component({
    selector: 'jhi-finances-delete-dialog',
    templateUrl: './finances-delete-dialog.component.html'
})
export class FINANCESDeleteDialogComponent {
    fINANCES: IFINANCES;

    constructor(private fINANCESService: FINANCESService, public activeModal: NgbActiveModal, private eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fINANCESService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'fINANCESListModification',
                content: 'Deleted an fINANCES'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-finances-delete-popup',
    template: ''
})
export class FINANCESDeletePopupComponent implements OnInit, OnDestroy {
    private ngbModalRef: NgbModalRef;

    constructor(private activatedRoute: ActivatedRoute, private router: Router, private modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ fINANCES }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(FINANCESDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.fINANCES = fINANCES;
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
