/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { OTHERComponent } from 'app/entities/other/other.component';
import { OTHERService } from 'app/entities/other/other.service';
import { OTHER } from 'app/shared/model/other.model';

describe('Component Tests', () => {
    describe('OTHER Management Component', () => {
        let comp: OTHERComponent;
        let fixture: ComponentFixture<OTHERComponent>;
        let service: OTHERService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [OTHERComponent],
                providers: []
            })
                .overrideTemplate(OTHERComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(OTHERComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(OTHERService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new OTHER(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.oTHERS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
