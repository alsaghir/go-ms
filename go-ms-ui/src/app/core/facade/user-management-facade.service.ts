import {Injectable} from '@angular/core';
import {NbAuthResult, NbAuthService} from '@nebular/auth';
import {Observable} from 'rxjs';
import {User} from '../../common/interface';
import {UserManagementState} from '../state';
import {tap} from 'rxjs/operators';

@Injectable({providedIn: 'root'})
export class UserManagementFacade {
  constructor(private userManagementState: UserManagementState,
              private nbAuthService: NbAuthService) {
  }

  authenticate(strategyName: string, data: User): Observable<NbAuthResult> {
    return this.nbAuthService.authenticate(strategyName, data)
      .pipe(tap(nbAuthResult => this.userManagementState.setNbAuthResult(nbAuthResult)));
  }
}
