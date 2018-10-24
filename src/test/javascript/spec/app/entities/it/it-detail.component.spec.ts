/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { ITDetailComponent } from 'app/entities/it/it-detail.component';
import { IT } from 'app/shared/model/it.model';

describe('Component Tests', () => {
    describe('IT Management Detail Component', () => {
        let comp: ITDetailComponent;
        let fixture: ComponentFixture<ITDetailComponent>;
        const route = ({ data: of({ iT: new IT(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ITDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ITDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ITDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.iT).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
