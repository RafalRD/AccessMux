/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { SALEUpdateComponent } from 'app/entities/sale/sale-update.component';
import { SALEService } from 'app/entities/sale/sale.service';
import { SALE } from 'app/shared/model/sale.model';

describe('Component Tests', () => {
    describe('SALE Management Update Component', () => {
        let comp: SALEUpdateComponent;
        let fixture: ComponentFixture<SALEUpdateComponent>;
        let service: SALEService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [SALEUpdateComponent]
            })
                .overrideTemplate(SALEUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SALEUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SALEService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new SALE(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sALE = entity;
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
                    const entity = new SALE();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.sALE = entity;
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
