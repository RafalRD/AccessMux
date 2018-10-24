import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { FINANCES } from 'app/shared/model/finances.model';
import { FINANCESService } from './finances.service';
import { FINANCESComponent } from './finances.component';
import { FINANCESDetailComponent } from './finances-detail.component';
import { FINANCESUpdateComponent } from './finances-update.component';
import { FINANCESDeletePopupComponent } from './finances-delete-dialog.component';
import { IFINANCES } from 'app/shared/model/finances.model';

@Injectable({ providedIn: 'root' })
export class FINANCESResolve implements Resolve<IFINANCES> {
    constructor(private service: FINANCESService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((fINANCES: HttpResponse<FINANCES>) => fINANCES.body));
        }
        return of(new FINANCES());
    }
}

export const fINANCESRoute: Routes = [
    {
        path: 'finances',
        component: FINANCESComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.fINANCES.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'finances/:id/view',
        component: FINANCESDetailComponent,
        resolve: {
            fINANCES: FINANCESResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.fINANCES.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'finances/new',
        component: FINANCESUpdateComponent,
        resolve: {
            fINANCES: FINANCESResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.fINANCES.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'finances/:id/edit',
        component: FINANCESUpdateComponent,
        resolve: {
            fINANCES: FINANCESResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.fINANCES.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fINANCESPopupRoute: Routes = [
    {
        path: 'finances/:id/delete',
        component: FINANCESDeletePopupComponent,
        resolve: {
            fINANCES: FINANCESResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'accessMuxApp.fINANCES.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
