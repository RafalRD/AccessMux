/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { MARKETINGComponent } from 'app/entities/marketing/marketing.component';
import { MARKETINGService } from 'app/entities/marketing/marketing.service';
import { MARKETING } from 'app/shared/model/marketing.model';

describe('Component Tests', () => {
    describe('MARKETING Management Component', () => {
        let comp: MARKETINGComponent;
        let fixture: ComponentFixture<MARKETINGComponent>;
        let service: MARKETINGService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [MARKETINGComponent],
                providers: []
            })
                .overrideTemplate(MARKETINGComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MARKETINGComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MARKETINGService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MARKETING(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.mARKETINGS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
