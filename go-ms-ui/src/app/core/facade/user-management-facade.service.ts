import {Injectable} from '@angular/core';
import {NbAuthResult, NbAuthService, NbTokenService} from '@nebular/auth';
import {Observable} from 'rxjs';
import {UserCredentials, UserDetails} from '../../common/interface';
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
    return this.userManagementApi.getUserDetails$();
  }
}
