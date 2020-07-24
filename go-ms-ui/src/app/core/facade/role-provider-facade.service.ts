import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {NbRoleProvider} from '@nebular/security';

import {UserManagementFacade} from './user-management-facade.service';
import {map} from 'rxjs/operators';

@Injectable()
export class RoleProviderFacade implements NbRoleProvider {

  constructor(private userManagementFacade: UserManagementFacade) {
  }

  getRole(): Observable<string | string[]> {
    return this.userManagementFacade.getUserDetails$()
      .pipe(
        map(userDetails => userDetails.profiles.map(profile => profile.id.toString()))
      );
  }
}
