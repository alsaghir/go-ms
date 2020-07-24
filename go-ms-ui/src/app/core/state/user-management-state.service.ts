import {Injectable} from '@angular/core';
import {NbAuthResult} from '@nebular/auth';
import {BehaviorSubject, Observable} from 'rxjs';
import {Profile, UserDetails, UserInfo} from '../../common/interface';

@Injectable({providedIn: 'root'})
export class UserManagementState {

  private nbAuthResult$ = new BehaviorSubject<NbAuthResult>(new NbAuthResult(false));
  private updating$ = new BehaviorSubject<boolean>(false);
  private userDetails$ = new BehaviorSubject<UserDetails>(null);
  private userDetailsLoaded = false;
  private usersInfo$ = new BehaviorSubject<UserInfo[]>(null);
  private usersInfoLoaded = false;
  private profiles$ = new BehaviorSubject<Profile[]>(null);
  private profilesLoaded = false;


  constructor() {
  }

  setNbAuthResult(nbAuthResult: NbAuthResult): void {
    this.nbAuthResult$.next(nbAuthResult);
  }

  setUserDetails(userDetails: UserDetails): void {
    this.userDetailsLoaded = true;
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

  isUserDetailsLoaded(): boolean {
    return this.userDetailsLoaded;
  }

  setUsersInfo(usersInfo: UserInfo[]): void {
    this.usersInfo$.asObservable();
  }

  getUsersInfo$(): Observable<UserInfo[]> {
    return this.usersInfo$.asObservable();
  }

  isUsersInfoLoaded(): boolean {
    return this.usersInfoLoaded;
  }

  isProfilesLoaded(): boolean {
    return this.profilesLoaded;
  }

  getProfiles$(): Observable<Profile[]> {
    return this.profiles$.asObservable();
  }

  setProfiles(profiles: Profile[]): void {
    return this.profiles$.next(profiles);
  }

}
