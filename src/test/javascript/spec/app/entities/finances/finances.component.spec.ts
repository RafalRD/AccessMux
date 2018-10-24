/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { FINANCESComponent } from 'app/entities/finances/finances.component';
import { FINANCESService } from 'app/entities/finances/finances.service';
import { FINANCES } from 'app/shared/model/finances.model';

describe('Component Tests', () => {
    describe('FINANCES Management Component', () => {
        let comp: FINANCESComponent;
        let fixture: ComponentFixture<FINANCESComponent>;
        let service: FINANCESService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [FINANCESComponent],
                providers: []
            })
                .overrideTemplate(FINANCESComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(FINANCESComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FINANCESService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new FINANCES(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.fINANCES[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
