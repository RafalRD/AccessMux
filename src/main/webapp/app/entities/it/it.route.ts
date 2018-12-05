import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { of } from 'rxjs';
import { map } from 'rxjs/operators';
import { IT } from 'app/shared/model/it.model';
import { ITService } from './it.service';
import { ITComponent } from './it.component';
import { ITDetailComponent } from './it-detail.component';
import { ITUpdateComponent } from './it-update.component';
import { ITDeletePopupComponent } from './it-delete-dialog.component';
import { IIT } from 'app/shared/model/it.model';

@Injectable({ providedIn: 'root' })
export class ITResolve implements Resolve<IIT> {
    constructor(private service: ITService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(map((iT: HttpResponse<IT>) => iT.body));
        }
        return of(new IT());
    }
}

export const iTRoute: Routes = [
    {
        path: 'it',
        component: ITComponent,
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_IT'],
            pageTitle: 'accessMuxApp.iT.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'it/:id/view',
        component: ITDetailComponent,
        resolve: {
            iT: ITResolve
        },
        data: {
            authorities: ['ROLE_ADMIN', 'ROLE_IT'],
            pageTitle: 'accessMuxApp.iT.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'it/new',
        component: ITUpdateComponent,
        resolve: {
            iT: ITResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'accessMuxApp.iT.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'it/:id/edit',
        component: ITUpdateComponent,
        resolve: {
            iT: ITResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'accessMuxApp.iT.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const iTPopupRoute: Routes = [
    {
        path: 'it/:id/delete',
        component: ITDeletePopupComponent,
        resolve: {
            iT: ITResolve
        },
        data: {
            authorities: ['ROLE_ADMIN'],
            pageTitle: 'accessMuxApp.iT.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
