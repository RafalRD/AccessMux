import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { HR } from 'app/shared/model/hr.model';
import { HRService } from './hr.service';
import { HRComponent } from './hr.component';
import { HRDetailComponent } from './hr-detail.component';
import { HRUpdateComponent } from './hr-update.component';
import { HRDeletePopupComponent } from './hr-delete-dialog.component';
import { IHR } from 'app/shared/model/hr.model';

@Injectable({ providedIn: 'root' })
export class HRResolve implements Resolve<IHR> {
    constructor(private service: HRService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((hR: HttpResponse<HR>) => hR.body));
        }
        return of(new HR());
    }
}

export const hRRoute: Routes = [
    {
        path: 'hr',
        component: HRComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HR'],
            pageTitle: 'accessMuxApp.hR.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hr/:id/view',
        component: HRDetailComponent,
        resolve: {
            hR: HRResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HR'],
            pageTitle: 'accessMuxApp.hR.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hr/new',
        component: HRUpdateComponent,
        resolve: {
            hR: HRResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HR'],
            pageTitle: 'accessMuxApp.hR.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'hr/:id/edit',
        component: HRUpdateComponent,
        resolve: {
            hR: HRResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HR'],
            pageTitle: 'accessMuxApp.hR.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const hRPopupRoute: Routes = [
    {
        path: 'hr/:id/delete',
        component: HRDeletePopupComponent,
        resolve: {
            hR: HRResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_HR'],
            pageTitle: 'accessMuxApp.hR.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
