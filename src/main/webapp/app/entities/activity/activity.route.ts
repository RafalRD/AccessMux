import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { ACTIVITY } from 'app/shared/model/activity.model';
import { ACTIVITYService } from './activity.service';
import { ACTIVITYComponent } from './activity.component';
import { ACTIVITYDetailComponent } from './activity-detail.component';
import { ACTIVITYUpdateComponent } from './activity-update.component';
import { ACTIVITYDeletePopupComponent } from './activity-delete-dialog.component';
import { IACTIVITY } from 'app/shared/model/activity.model';

@Injectable({ providedIn: 'root' })
export class ACTIVITYResolve implements Resolve<IACTIVITY> {
    constructor(private service: ACTIVITYService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((aCTIVITY: HttpResponse<ACTIVITY>) => aCTIVITY.body));
        }
        return of(new ACTIVITY());
    }
}

export const aCTIVITYRoute: Routes = [
    {
        path: 'activity',
        component: ACTIVITYComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.aCTIVITY.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity/:id/view',
        component: ACTIVITYDetailComponent,
        resolve: {
            aCTIVITY: ACTIVITYResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.aCTIVITY.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity/new',
        component: ACTIVITYUpdateComponent,
        resolve: {
            aCTIVITY: ACTIVITYResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.aCTIVITY.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'activity/:id/edit',
        component: ACTIVITYUpdateComponent,
        resolve: {
            aCTIVITY: ACTIVITYResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.aCTIVITY.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const aCTIVITYPopupRoute: Routes = [
    {
        path: 'activity/:id/delete',
        component: ACTIVITYDeletePopupComponent,
        resolve: {
            aCTIVITY: ACTIVITYResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.aCTIVITY.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
