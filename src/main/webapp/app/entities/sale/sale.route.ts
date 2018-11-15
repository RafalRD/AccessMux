import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { SALE } from 'app/shared/model/sale.model';
import { SALEService } from './sale.service';
import { SALEComponent } from './sale.component';
import { SALEDetailComponent } from './sale-detail.component';
import { SALEUpdateComponent } from './sale-update.component';
import { SALEDeletePopupComponent } from './sale-delete-dialog.component';
import { ISALE } from 'app/shared/model/sale.model';

@Injectable({ providedIn: 'root' })
export class SALEResolve implements Resolve<ISALE> {
    constructor(private service: SALEService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((sALE: HttpResponse<SALE>) => sALE.body));
        }
        return of(new SALE());
    }
}

export const sALERoute: Routes = [
    {
        path: 'sale',
        component: SALEComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SPRZEDAZ'],
            pageTitle: 'accessMuxApp.sALE.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale/:id/view',
        component: SALEDetailComponent,
        resolve: {
            sALE: SALEResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SPRZEDAZ'],
            pageTitle: 'accessMuxApp.sALE.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale/new',
        component: SALEUpdateComponent,
        resolve: {
            sALE: SALEResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SPRZEDAZ'],
            pageTitle: 'accessMuxApp.sALE.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'sale/:id/edit',
        component: SALEUpdateComponent,
        resolve: {
            sALE: SALEResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SPRZEDAZ'],
            pageTitle: 'accessMuxApp.sALE.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const sALEPopupRoute: Routes = [
    {
        path: 'sale/:id/delete',
        component: SALEDeletePopupComponent,
        resolve: {
            sALE: SALEResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_SPRZEDAZ'],
            pageTitle: 'accessMuxApp.sALE.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
