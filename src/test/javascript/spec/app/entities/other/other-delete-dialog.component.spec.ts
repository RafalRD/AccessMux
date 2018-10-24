/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { AccessMuxTestModule } from '../../../test.module';
import { OTHERDeleteDialogComponent } from 'app/entities/other/other-delete-dialog.component';
import { OTHERService } from 'app/entities/other/other.service';

describe('Component Tests', () => {
    describe('OTHER Management Delete Component', () => {
        let comp: OTHERDeleteDialogComponent;
        let fixture: ComponentFixture<OTHERDeleteDialogComponent>;
        let service: OTHERService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [OTHERDeleteDialogComponent]
            })
                .overrideTemplate(OTHERDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OTHERDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OTHERService);
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
