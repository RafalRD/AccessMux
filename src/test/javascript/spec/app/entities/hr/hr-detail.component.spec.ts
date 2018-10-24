/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { HRDetailComponent } from 'app/entities/hr/hr-detail.component';
import { HR } from 'app/shared/model/hr.model';

describe('Component Tests', () => {
    describe('HR Management Detail Component', () => {
        let comp: HRDetailComponent;
        let fixture: ComponentFixture<HRDetailComponent>;
        const route = ({ data: of({ hR: new HR(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [HRDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(HRDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(HRDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.hR).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
