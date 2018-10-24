/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { ACTIVITYComponent } from 'app/entities/activity/activity.component';
import { ACTIVITYService } from 'app/entities/activity/activity.service';
import { ACTIVITY } from 'app/shared/model/activity.model';

describe('Component Tests', () => {
    describe('ACTIVITY Management Component', () => {
        let comp: ACTIVITYComponent;
        let fixture: ComponentFixture<ACTIVITYComponent>;
        let service: ACTIVITYService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ACTIVITYComponent],
                providers: []
            })
                .overrideTemplate(ACTIVITYComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ACTIVITYComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ACTIVITYService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new ACTIVITY(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.aCTIVITIES[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
