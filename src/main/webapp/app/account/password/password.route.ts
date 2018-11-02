import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { PasswordComponent } from './password.component';

export const passwordRoute: Route = {
    path: 'password',
    component: PasswordComponent,
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_HR', 'ROLE_IT', 'ROLE_FINANSE', 'ROLE_SPRZEDAZ', 'ROLE_MARKETING', 'ROLE_INNE'],
        pageTitle: 'global.menu.account.password'
    },
    canActivate: [UserRouteAccessService]
};
