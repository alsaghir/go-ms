import {Injectable} from '@angular/core';
import {NbAuthResult} from '@nebular/auth';
import {BehaviorSubject} from 'rxjs';

@Injectable({providedIn: 'root'})
export class UserManagementState {

  private nbAuthResult$ = new BehaviorSubject<NbAuthResult>(new NbAuthResult(false));
  private updating$ = new BehaviorSubject<boolean>(false);

  constructor() {
  }


  setNbAuthResult(nbAuthResult: NbAuthResult): void {
    this.nbAuthResult$.next(nbAuthResult);
  }
}
