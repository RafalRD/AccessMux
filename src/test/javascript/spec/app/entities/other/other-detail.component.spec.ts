/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { OTHERDetailComponent } from 'app/entities/other/other-detail.component';
import { OTHER } from 'app/shared/model/other.model';

describe('Component Tests', () => {
    describe('OTHER Management Detail Component', () => {
        let comp: OTHERDetailComponent;
        let fixture: ComponentFixture<OTHERDetailComponent>;
        const route = ({ data: of({ oTHER: new OTHER(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [OTHERDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(OTHERDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(OTHERDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.oTHER).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
