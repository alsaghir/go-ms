import {Injectable} from '@angular/core';
import {NbAuthResult} from '@nebular/auth';
import {Observable} from 'rxjs';
import {Profile, UserCredentials, UserDetails, UserInfo} from '../../common/interface';
import {UserManagementState} from '../state';
import {tap} from 'rxjs/operators';
import {UserManagementApi} from '../api';
import {NbUtil} from '../util';
import {HttpResponse} from '@angular/common/http';

@Injectable({providedIn: 'root'})
export class UserManagementFacade {
  constructor(private userManagementState: UserManagementState,
              private userManagementApi: UserManagementApi,
              private nbUtil: NbUtil) {
  }

  authenticate(strategyName: string, data: UserCredentials): Observable<NbAuthResult> {
    return this.nbUtil.authenticate(strategyName, data);
  }

  getUserDetails$(): Observable<UserDetails> {
    if (this.userManagementState.isUserDetailsLoaded()) {
      return this.userManagementState.getUserDetails$();
    } else {
      return this.userManagementApi.getUserDetails$()
        .pipe(tap(userDetails => this.userManagementState.setUserDetails(userDetails)));
    }
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
  }
}
