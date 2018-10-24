/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { HRUpdateComponent } from 'app/entities/hr/hr-update.component';
import { HRService } from 'app/entities/hr/hr.service';
import { HR } from 'app/shared/model/hr.model';

describe('Component Tests', () => {
    describe('HR Management Update Component', () => {
        let comp: HRUpdateComponent;
        let fixture: ComponentFixture<HRUpdateComponent>;
        let service: HRService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [HRUpdateComponent]
            })
                .overrideTemplate(HRUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HRUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HRService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new HR(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hR = entity;
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
                    const entity = new HR();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.hR = entity;
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
