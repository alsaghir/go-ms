import {Injectable} from '@angular/core';
import {NbAuthResult, NbAuthService} from '@nebular/auth';
import {Observable} from 'rxjs';
import {User} from '../../common/interface';
import {UserManagementApi} from '../api';
import {UserManagementState} from '../state';

@Injectable({providedIn: 'root'})
export class UserManagementFacade {
  constructor(private userManagementApi: UserManagementApi,
              private userManagementState: UserManagementState,
              private nbAuthService: NbAuthService) {
  }

  authenticate(strategyName: string, data: User): Observable<NbAuthResult> {
    return this.nbAuthService.authenticate(strategyName, data);
  }
}
