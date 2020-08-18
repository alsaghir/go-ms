import {Injectable} from '@angular/core';
import {NbAuthResult} from '@nebular/auth';
import {BehaviorSubject, Observable} from 'rxjs';
import {Profile, UserDetails, UserInfo} from '../../common/interface';

@Injectable({providedIn: 'root'})
export class UserManagementState {

  private nbAuthResult$ = new BehaviorSubject<NbAuthResult>(new NbAuthResult(false));
  private userDetails$ = new BehaviorSubject<UserDetails>(null);
  private userDetailsLoaded = false;
  private usersInfo$ = new BehaviorSubject<UserInfo[]>(null);
  private usersInfoLoaded = false;
  private usersInfoUpdating = false;
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

  setUsersInfoLoaded(usersInfoLoaded: boolean): void {
    this.usersInfoLoaded = usersInfoLoaded;
  }

  isUsersInfoUpdating$(): boolean {
    return this.usersInfoUpdating;
  }

  setUsersInfoUpdating(usersInfoUpdating: boolean): void {
    this.usersInfoUpdating = usersInfoUpdating;
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
