/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { OTHERUpdateComponent } from 'app/entities/other/other-update.component';
import { OTHERService } from 'app/entities/other/other.service';
import { OTHER } from 'app/shared/model/other.model';

describe('Component Tests', () => {
    describe('OTHER Management Update Component', () => {
        let comp: OTHERUpdateComponent;
        let fixture: ComponentFixture<OTHERUpdateComponent>;
        let service: OTHERService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [OTHERUpdateComponent]
            })
                .overrideTemplate(OTHERUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OTHERUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OTHERService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new OTHER(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.oTHER = entity;
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
                    const entity = new OTHER();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.oTHER = entity;
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
