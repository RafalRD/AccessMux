/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { MARKETINGUpdateComponent } from 'app/entities/marketing/marketing-update.component';
import { MARKETINGService } from 'app/entities/marketing/marketing.service';
import { MARKETING } from 'app/shared/model/marketing.model';

describe('Component Tests', () => {
    describe('MARKETING Management Update Component', () => {
        let comp: MARKETINGUpdateComponent;
        let fixture: ComponentFixture<MARKETINGUpdateComponent>;
        let service: MARKETINGService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [MARKETINGUpdateComponent]
            })
                .overrideTemplate(MARKETINGUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MARKETINGUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MARKETINGService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MARKETING(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mARKETING = entity;
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
                    const entity = new MARKETING();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.mARKETING = entity;
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
