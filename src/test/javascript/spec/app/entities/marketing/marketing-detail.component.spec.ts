/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { MARKETINGDetailComponent } from 'app/entities/marketing/marketing-detail.component';
import { MARKETING } from 'app/shared/model/marketing.model';

describe('Component Tests', () => {
    describe('MARKETING Management Detail Component', () => {
        let comp: MARKETINGDetailComponent;
        let fixture: ComponentFixture<MARKETINGDetailComponent>;
        const route = ({ data: of({ mARKETING: new MARKETING(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [MARKETINGDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MARKETINGDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MARKETINGDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.mARKETING).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
