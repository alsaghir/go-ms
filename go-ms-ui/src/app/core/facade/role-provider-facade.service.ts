import {Injectable} from '@angular/core';
import {Observable, of} from 'rxjs';
import {NbAccessChecker, NbRoleProvider} from '@nebular/security';

import {UserManagementFacade} from './user-management-facade.service';
import {NbUtil} from "../util";
import {map, mergeMap} from "rxjs/operators";
import {BackendUrls} from "../../common/config";
import {NbAuthService} from "@nebular/auth";

@Injectable()
export class RoleProviderFacade implements NbRoleProvider {

  constructor(private userManagementFacade: UserManagementFacade, private nbUtil: NbUtil) {
  }

  getRole(): Observable<string | string[]> {
    //return of('anonymous');
    return this.nbUtil.isAuthenticated().pipe(
      mergeMap(isAuthenticated => {
        // const userId = this.nbUtil.getToken().getPayload().sub as number;
        if (isAuthenticated) {
          return this.userManagementFacade.getLoggedInUserId$()
            .pipe(
              mergeMap(userId =>
                this.userManagementFacade.getUser$(userId)
                  .pipe(
                    mergeMap(user =>
                      this.userManagementFacade.getProfilesOf$(user)
                        .pipe(
                          map(profiles => profiles._embedded[BackendUrls.API_RESOURCE_PROFILES]
                            .map(profile => {
                              console.log(profile);
                              return profile._links.self.href;
                            })
                          )
                        )
                    )
                  )
              )
            );
        } else {
          return 'anonymous';
        }
      })
    );
  }
}
