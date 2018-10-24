/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { HRComponent } from 'app/entities/hr/hr.component';
import { HRService } from 'app/entities/hr/hr.service';
import { HR } from 'app/shared/model/hr.model';

describe('Component Tests', () => {
    describe('HR Management Component', () => {
        let comp: HRComponent;
        let fixture: ComponentFixture<HRComponent>;
        let service: HRService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [HRComponent],
                providers: []
            })
                .overrideTemplate(HRComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(HRComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HRService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new HR(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.hRS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
