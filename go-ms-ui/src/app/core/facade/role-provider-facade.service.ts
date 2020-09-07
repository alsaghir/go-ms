import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {NbRoleProvider} from '@nebular/security';

import {UserManagementFacade} from './user-management-facade.service';
import {mergeMap, map} from 'rxjs/operators';
import {NbUtil} from "../util";

@Injectable()
export class RoleProviderFacade implements NbRoleProvider {

  constructor(private userManagementFacade: UserManagementFacade,
              private nbUtil: NbUtil) {
  }

  getRole(): Observable<string | string[]> {
    return this.nbUtil.isAuthenticated().pipe(
      mergeMap(isAuthenticated => {
        if (isAuthenticated) {
          return this.userManagementFacade.getUserDetails$()
            .pipe(
              map(userDetails => userDetails.profiles.map(profile => profile.id.toString()))
            );
        } else {
          return 'anonymous';
        }
      })
    );
  }
}
