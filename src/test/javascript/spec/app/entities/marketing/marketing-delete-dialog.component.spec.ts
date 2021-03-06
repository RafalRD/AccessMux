/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessMuxTestModule } from '../../../test.module';
import { MARKETINGDeleteDialogComponent } from 'app/entities/marketing/marketing-delete-dialog.component';
import { MARKETINGService } from 'app/entities/marketing/marketing.service';

describe('Component Tests', () => {
    describe('MARKETING Management Delete Component', () => {
        let comp: MARKETINGDeleteDialogComponent;
        let fixture: ComponentFixture<MARKETINGDeleteDialogComponent>;
        let service: MARKETINGService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [MARKETINGDeleteDialogComponent]
            })
                .overrideTemplate(MARKETINGDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MARKETINGDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MARKETINGService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it(
                'Should call delete service on confirmDelete',
                inject(
                    [],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });
});
