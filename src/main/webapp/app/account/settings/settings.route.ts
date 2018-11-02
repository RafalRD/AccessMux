import { Route } from '@angular/router';

import { UserRouteAccessService } from 'app/core';
import { SettingsComponent } from './settings.component';

export const settingsRoute: Route = {
    path: 'settings',
    component: SettingsComponent,
    data: {
        authorities: ['ROLE_ADMIN', 'ROLE_HR', 'ROLE_IT', 'ROLE_FINANSE', 'ROLE_SPRZEDAZ', 'ROLE_MARKETING', 'ROLE_INNE'],
        pageTitle: 'global.menu.account.settings'
    },
    canActivate: [UserRouteAccessService]
};
