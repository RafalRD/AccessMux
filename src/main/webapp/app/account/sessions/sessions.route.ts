import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { SessionsComponent } from './sessions.component';

export const sessionsRoute: Route = {
    path: 'sessions',
    component: SessionsComponent,
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_HR', 'ROLE_IT', 'ROLE_FINANSE', 'ROLE_SPRZEDAZ', 'ROLE_MARKETING', 'ROLE_INNE'],
        pageTitle: 'global.menu.account.sessions'
    },
    canActivate: [UserRouteAccessService]
};
