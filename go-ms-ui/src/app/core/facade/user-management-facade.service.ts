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
    return this.nbUtil.authenticate(strategyName, data)
      .pipe(
        tap(nbAuthResult => {
          this.userManagementState.setNbAuthResult(nbAuthResult);
        })
      );
  }

  getUserDetails$(): Observable<UserDetails> {
    if (this.userManagementState.isUserDetailsLoaded()) {
      return this.userManagementState.getUserDetails$();
    } else {
      return this.userManagementApi.getUserDetails$()
        .pipe(tap(userDetails => this.userManagementState.setUserDetails(userDetails)));
    }
  }

  getProfiles$(): Observable<Profile[]> {
    if (this.userManagementState.isProfilesLoaded()) {
      return this.userManagementState.getProfiles$();
    } else {
      return this.userManagementApi.getProfiles$()
        .pipe(tap(profiles => this.userManagementState.setProfiles(profiles)));
    }
  }

  getUsers$(): Observable<UserInfo[]> {
    if (this.userManagementState.isUsersInfoLoaded()) {
      return this.userManagementState.getUsersInfo$();
    } else {
      return this.userManagementApi.getUsersInfo$()
        .pipe(tap(usersInfo => this.userManagementState.setUsersInfo(usersInfo)));
    }
  }

  addUser(userInfo: UserInfo): Observable<HttpResponse<any>> {
    this.userManagementState.setUsersInfoUpdating(true);
    return this.userManagementApi.addUser(userInfo).pipe(
      tap(response => {
        this.userManagementState.setUsersInfoUpdating(false);
        this.userManagementState.setUsersInfoLoaded(false);
      })
    );
  }
}
