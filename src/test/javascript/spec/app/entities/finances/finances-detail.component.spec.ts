/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { FINANCESDetailComponent } from 'app/entities/finances/finances-detail.component';
import { FINANCES } from 'app/shared/model/finances.model';

describe('Component Tests', () => {
    describe('FINANCES Management Detail Component', () => {
        let comp: FINANCESDetailComponent;
        let fixture: ComponentFixture<FINANCESDetailComponent>;
        const route = ({ data: of({ fINANCES: new FINANCES(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [FINANCESDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(FINANCESDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(FINANCESDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.fINANCES).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
