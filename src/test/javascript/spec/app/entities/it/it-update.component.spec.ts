/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { ITUpdateComponent } from 'app/entities/it/it-update.component';
import { ITService } from 'app/entities/it/it.service';
import { IT } from 'app/shared/model/it.model';

describe('Component Tests', () => {
    describe('IT Management Update Component', () => {
        let comp: ITUpdateComponent;
        let fixture: ComponentFixture<ITUpdateComponent>;
        let service: ITService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ITUpdateComponent]
            })
                .overrideTemplate(ITUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ITUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ITService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new IT(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iT = entity;
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
                    const entity = new IT();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.iT = entity;
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
