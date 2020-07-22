import {Injectable} from '@angular/core';
import {NbAuthResult, NbAuthService, NbTokenService} from '@nebular/auth';
import {Observable} from 'rxjs';
import {UserCredentials, UserDetails, UserInfo} from '../../common/interface';
import {UserManagementState} from '../state';
import {tap} from 'rxjs/operators';
import {UserManagementApi} from '../api';

@Injectable({providedIn: 'root'})
export class UserManagementFacade {
  constructor(private userManagementState: UserManagementState,
              private userManagementApi: UserManagementApi,
              private nbAuthService: NbAuthService,
              private nbTokenService: NbTokenService) {
  }

  authenticate(strategyName: string, data: UserCredentials): Observable<NbAuthResult> {
    this.userManagementState.setUpdating(true);
    return this.nbAuthService.authenticate(strategyName, data)
      .pipe(
        tap(nbAuthResult => {
          this.userManagementState.setNbAuthResult(nbAuthResult);
          this.userManagementState.setUpdating(false);
        })
      );
  }

  isUpdating$(): Observable<boolean> {
    return this.userManagementState.isUpdating$();
  }

  getUserDetails$(): Observable<UserDetails> {
    if (this.userManagementState.isUserDetailsLoaded()) {
      return this.userManagementState.getUserDetails$();
    } else {
      return this.userManagementApi.getUserDetails$().pipe(tap(userDetails => this.userManagementState.setUserDetails(userDetails)));
    }
  }

  getUsers$(): Observable<UserInfo[]> {
    if (this.userManagementState.isUsersInfoLoaded()) {
      return this.userManagementState.getUsersInfo$();
    } else {
      return this.userManagementApi.getUsersInfo$().pipe(tap(usersInfo => this.userManagementState.setUsersInfo(usersInfo)));
    }
  }
}
