/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { FINANCESUpdateComponent } from 'app/entities/finances/finances-update.component';
import { FINANCESService } from 'app/entities/finances/finances.service';
import { FINANCES } from 'app/shared/model/finances.model';

describe('Component Tests', () => {
    describe('FINANCES Management Update Component', () => {
        let comp: FINANCESUpdateComponent;
        let fixture: ComponentFixture<FINANCESUpdateComponent>;
        let service: FINANCESService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [FINANCESUpdateComponent]
            })
                .overrideTemplate(FINANCESUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FINANCESUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FINANCESService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new FINANCES(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.fINANCES = entity;
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
                    const entity = new FINANCES();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.fINANCES = entity;
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
