/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { ACTIVITYUpdateComponent } from 'app/entities/activity/activity-update.component';
import { ACTIVITYService } from 'app/entities/activity/activity.service';
import { ACTIVITY } from 'app/shared/model/activity.model';

describe('Component Tests', () => {
    describe('ACTIVITY Management Update Component', () => {
        let comp: ACTIVITYUpdateComponent;
        let fixture: ComponentFixture<ACTIVITYUpdateComponent>;
        let service: ACTIVITYService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ACTIVITYUpdateComponent]
            })
                .overrideTemplate(ACTIVITYUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ACTIVITYUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ACTIVITYService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ACTIVITY(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.aCTIVITY = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new ACTIVITY();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.aCTIVITY = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
