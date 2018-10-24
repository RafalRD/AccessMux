/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { AccessMuxTestModule } from '../../../test.module';
import { ITComponent } from 'app/entities/it/it.component';
import { ITService } from 'app/entities/it/it.service';
import { IT } from 'app/shared/model/it.model';

describe('Component Tests', () => {
    describe('IT Management Component', () => {
        let comp: ITComponent;
        let fixture: ComponentFixture<ITComponent>;
        let service: ITService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ITComponent],
                providers: []
            })
                .overrideTemplate(ITComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ITComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ITService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new IT(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.iTS[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
