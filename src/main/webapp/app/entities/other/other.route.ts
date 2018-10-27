import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { OTHER } from 'app/shared/model/other.model';
import { OTHERService } from './other.service';
import { OTHERComponent } from './other.component';
import { OTHERDetailComponent } from './other-detail.component';
import { OTHERUpdateComponent } from './other-update.component';
import { OTHERDeletePopupComponent } from './other-delete-dialog.component';
import { IOTHER } from 'app/shared/model/other.model';

@Injectable({ providedIn: 'root' })
export class OTHERResolve implements Resolve<IOTHER> {
    constructor(private service: OTHERService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((oTHER: HttpResponse<OTHER>) => oTHER.body));
        }
        return of(new OTHER());
    }
}

export const oTHERRoute: Routes = [
    {
        path: 'other',
        component: OTHERComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INNE'],
            pageTitle: 'accessMuxApp.oTHER.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'other/:id/view',
        component: OTHERDetailComponent,
        resolve: {
            oTHER: OTHERResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INNE'],
            pageTitle: 'accessMuxApp.oTHER.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'other/new',
        component: OTHERUpdateComponent,
        resolve: {
            oTHER: OTHERResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INNE'],
            pageTitle: 'accessMuxApp.oTHER.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'other/:id/edit',
        component: OTHERUpdateComponent,
        resolve: {
            oTHER: OTHERResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INNE'],
            pageTitle: 'accessMuxApp.oTHER.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const oTHERPopupRoute: Routes = [
    {
        path: 'other/:id/delete',
        component: OTHERDeletePopupComponent,
        resolve: {
            oTHER: OTHERResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_INNE'],
            pageTitle: 'accessMuxApp.oTHER.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
