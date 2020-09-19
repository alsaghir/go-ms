import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {NbRoleProvider} from '@nebular/security';

import {UserManagementFacade} from './user-management-facade.service';
import {NbUtil} from "../util";

@Injectable()
export class RoleProviderFacade implements NbRoleProvider {

  constructor(private userManagementFacade: UserManagementFacade,
              private nbUtil: NbUtil) {
  }

  getRole(): Observable<string | string[]> {
    return of('anonymous');

    /*this.nbUtil.isAuthenticated().pipe(
      mergeMap(isAuthenticated => {
        this.nbUtil.getToken().getPayload()
        if (isAuthenticated) {
          return this.userManagementFacade.getUserProfiles$().pipe(
            map(profiles => profiles.map(profile => profile.id.toString())));
        } else {
          return 'anonymous';
        }
      })
    );*/
  }
}
