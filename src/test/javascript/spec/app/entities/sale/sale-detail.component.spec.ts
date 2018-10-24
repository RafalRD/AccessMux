/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { SALEDetailComponent } from 'app/entities/sale/sale-detail.component';
import { SALE } from 'app/shared/model/sale.model';

describe('Component Tests', () => {
    describe('SALE Management Detail Component', () => {
        let comp: SALEDetailComponent;
        let fixture: ComponentFixture<SALEDetailComponent>;
        const route = ({ data: of({ sALE: new SALE(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [SALEDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(SALEDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(SALEDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.sALE).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
