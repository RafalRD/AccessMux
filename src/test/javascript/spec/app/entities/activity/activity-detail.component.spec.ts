/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { AccessMuxTestModule } from '../../../test.module';
import { ACTIVITYDetailComponent } from 'app/entities/activity/activity-detail.component';
import { ACTIVITY } from 'app/shared/model/activity.model';

describe('Component Tests', () => {
    describe('ACTIVITY Management Detail Component', () => {
        let comp: ACTIVITYDetailComponent;
        let fixture: ComponentFixture<ACTIVITYDetailComponent>;
        const route = ({ data: of({ aCTIVITY: new ACTIVITY(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [AccessMuxTestModule],
                declarations: [ACTIVITYDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ACTIVITYDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ACTIVITYDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.aCTIVITY).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
