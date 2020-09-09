import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {Profile, UserDetails, UserInfo} from '../../common/interface';

@Injectable({providedIn: 'root'})
export class UserManagementState {

  constructor() {
  }

  private userDetails$ = new BehaviorSubject<UserDetails>(null);
  private userDetailsLoaded = false;

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

  private allUsersInfo$ = new BehaviorSubject<UserInfo[]>(null);
  private allUsersInfoLoaded = false;
  private allUsersInfoUpdating = false;

  setAllUsersInfo(usersInfo: UserInfo[]): void {
    this.allUsersInfo$.next(usersInfo);
  }

  getAllUsersInfo$(): Observable<UserInfo[]> {
    return this.allUsersInfo$.asObservable();
  }

  isAllUsersInfoLoaded(): boolean {
    return this.allUsersInfoLoaded;
  }

  setAllUsersInfoLoaded(usersInfoLoaded: boolean): void {
    this.allUsersInfoLoaded = usersInfoLoaded;
  }

  isAllUsersInfoUpdating$(): boolean {
    return this.allUsersInfoUpdating;
  }

  setAllUsersInfoUpdating(usersInfoUpdating: boolean): void {
    this.allUsersInfoUpdating = usersInfoUpdating;
  }

  private allProfiles$ = new BehaviorSubject<Profile[]>(null);
  private allProfilesLoaded = false;
  private allProfilesUpdating = false;

  isAllProfilesLoaded(): boolean {
    return this.allProfilesLoaded;
  }

  getAllProfiles$(): Observable<Profile[]> {
    return this.allProfiles$.asObservable();
  }

  setAllProfiles(allProfiles: Profile[]): void {
    return this.allProfiles$.next(allProfiles);
  }
}
