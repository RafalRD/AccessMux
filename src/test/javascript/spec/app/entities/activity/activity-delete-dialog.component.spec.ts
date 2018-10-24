/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessMuxTestModule } from '../../../test.module';
import { ACTIVITYDeleteDialogComponent } from 'app/entities/activity/activity-delete-dialog.component';
import { ACTIVITYService } from 'app/entities/activity/activity.service';

describe('Component Tests', () => {
    describe('ACTIVITY Management Delete Component', () => {
        let comp: ACTIVITYDeleteDialogComponent;
        let fixture: ComponentFixture<ACTIVITYDeleteDialogComponent>;
        let service: ACTIVITYService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ACTIVITYDeleteDialogComponent]
            })
                .overrideTemplate(ACTIVITYDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ACTIVITYDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ACTIVITYService);
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
