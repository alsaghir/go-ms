import {Injectable} from '@angular/core';
import {NbAuthResult} from '@nebular/auth';
import {Observable, of as observableOf} from 'rxjs';
import {UserManagementState} from '../state';
import {UserManagementApi} from '../api';
import {NbUtil} from '../util';
import {Collection, Link, Profile, User, UserCredentials} from "../../common/model";
import {tap} from "rxjs/operators";
import {Privilege} from "../../common/model/resource/privilege";
import {BackendUrls} from "../../common/config";

@Injectable({providedIn: 'root'})
export class UserManagementFacade {
  constructor(private userManagementState: UserManagementState,
              private userManagementApi: UserManagementApi,
              private nbUtil: NbUtil) {
  }

  authenticate(strategyName: string, data: UserCredentials): Observable<NbAuthResult> {
    return this.nbUtil.authenticate(strategyName, data).pipe(tap(nbAuthResult => {
      if (nbAuthResult.isSuccess())
        this.userManagementState.setLoggedInUserId(Number(nbAuthResult.getToken().getPayload().sub))
    }));
  }

  getLoggedInUserId$(): Observable<number> {
    if (this.userManagementState.isLoggedInUserIdLoaded())
      return this.userManagementState.getLoggedInUserId$();
    else
      return observableOf(this.nbUtil.getToken().getPayload().sub as number)
        .pipe(tap(id => this.userManagementState.setLoggedInUserId(id)));
  }

  getProfiles$(): Observable<Collection<Profile>> {
    return this.userManagementApi.getProfiles$();
  }

  getProfilesOf$(user: User): Observable<Collection<Profile>> {
    return this.userManagementApi.getProfilesOf$(user);
  }

  getPrivileges$(): Observable<Collection<Privilege>> {
    return this.userManagementApi.getPrivileges$();
  }

  getPrivilegesOf$(profile: Profile) {
    return this.userManagementApi.getPrivilegesOf(profile);
  }

  getUser$(id: number): Observable<User> {
    return this.userManagementApi.getUser$(id);
  }

  updateAssignedPrivilegesOf(profile: Profile, linksOfPrivileges: Link[]): Observable<null> {
    return this.userManagementApi.updateAssignedPrivilegesOf(profile, linksOfPrivileges);
  }

  /* getLoggedInUserInfo$(): Observable<UserInfo> {
     return this.userManagementApi.getUserInfo$()
         .pipe(tap(loggedInUserInfo => this.userManagementState.setLoggedInUserInfo(loggedInUserInfo)), shareReplay(1));
   }

   getUserProfiles$(): Observable<Profile[]> {
     if (this.userManagementState.isUserProfilesLoaded()) {
       return this.userManagementState.getUserProfiles$();
     } else {
       return this.userManagementApi.getUserProfiles$()
         .pipe(tap(userProfiles => this.userManagementState.setUserProfiles(userProfiles)));
     }
   }

   getProfilePrivileges$(profileId: number): Observable<string[]> {
     return this.userManagementApi.getProfilePrivileges$(profileId);
   }

   getAllProfiles$(): Observable<Profile[]> {
     if (this.userManagementState.isAllProfilesLoaded()) {
       return this.userManagementState.getAllProfiles$();
     } else {
       return this.userManagementApi.getAllProfiles$()
         .pipe(tap(profiles => this.userManagementState.setAllProfiles(profiles)));
     }
   }

   getAllUsers$(): Observable<UserInfo[]> {
     if (this.userManagementState.isAllUsersInfoLoaded()) {
       return this.userManagementState.getAllUsersInfo$();
     } else {
       return this.userManagementApi.getAllUsersInfo$()
         .pipe(tap(usersInfo => this.userManagementState.setAllUsersInfo(usersInfo)));
     }
   }

   addUser(userInfo: UserInfo): Observable<HttpResponse<any>> {
     this.userManagementState.setAllUsersInfoUpdating(true);
     return this.userManagementApi.addUser(userInfo).pipe(
       tap(response => {
         this.userManagementState.setAllUsersInfoUpdating(false);
         this.userManagementState.setAllUsersInfoLoaded(false);
       })
     );
   }*/



}
