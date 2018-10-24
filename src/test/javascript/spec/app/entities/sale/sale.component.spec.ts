/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { SALEComponent } from 'app/entities/sale/sale.component';
import { SALEService } from 'app/entities/sale/sale.service';
import { SALE } from 'app/shared/model/sale.model';

describe('Component Tests', () => {
    describe('SALE Management Component', () => {
        let comp: SALEComponent;
        let fixture: ComponentFixture<SALEComponent>;
        let service: SALEService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [SALEComponent],
                providers: []
            })
                .overrideTemplate(SALEComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(SALEComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SALEService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new SALE(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.sALES[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
