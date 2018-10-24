import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { MARKETING } from 'app/shared/model/marketing.model';
import { MARKETINGService } from './marketing.service';
import { MARKETINGComponent } from './marketing.component';
import { MARKETINGDetailComponent } from './marketing-detail.component';
import { MARKETINGUpdateComponent } from './marketing-update.component';
import { MARKETINGDeletePopupComponent } from './marketing-delete-dialog.component';
import { IMARKETING } from 'app/shared/model/marketing.model';

@Injectable({ providedIn: 'root' })
export class MARKETINGResolve implements Resolve<IMARKETING> {
    constructor(private service: MARKETINGService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((mARKETING: HttpResponse<MARKETING>) => mARKETING.body));
        }
        return of(new MARKETING());
    }
}

export const mARKETINGRoute: Routes = [
    {
        path: 'marketing',
        component: MARKETINGComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.mARKETING.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marketing/:id/view',
        component: MARKETINGDetailComponent,
        resolve: {
            mARKETING: MARKETINGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.mARKETING.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marketing/new',
        component: MARKETINGUpdateComponent,
        resolve: {
            mARKETING: MARKETINGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.mARKETING.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'marketing/:id/edit',
        component: MARKETINGUpdateComponent,
        resolve: {
            mARKETING: MARKETINGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.mARKETING.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const mARKETINGPopupRoute: Routes = [
    {
        path: 'marketing/:id/delete',
        component: MARKETINGDeletePopupComponent,
        resolve: {
            mARKETING: MARKETINGResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.mARKETING.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
