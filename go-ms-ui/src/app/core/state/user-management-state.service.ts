import {Injectable} from '@angular/core';
import {NbAuthResult} from '@nebular/auth';
import {BehaviorSubject, Observable} from 'rxjs';
import {UserDetails} from '../../common/interface';

@Injectable({providedIn: 'root'})
export class UserManagementState {

  private nbAuthResult$ = new BehaviorSubject<NbAuthResult>(new NbAuthResult(false));
  private updating$ = new BehaviorSubject<boolean>(false);
  private userDetails$ = new BehaviorSubject<UserDetails>(null);

  constructor() {
  }


  setNbAuthResult(nbAuthResult: NbAuthResult): void {
    this.nbAuthResult$.next(nbAuthResult);
  }

  setUserDetails(userDetails: UserDetails): void {
    this.userDetails$.next(userDetails);
  }

  setUpdating(isUpdating: boolean): void {
    this.updating$.next(isUpdating);
  }

  isUpdating$(): Observable<boolean> {
    return this.updating$.asObservable();
  }

  getUserDetails$(): Observable<UserDetails> {
    return this.userDetails$.asObservable();
  }
}
