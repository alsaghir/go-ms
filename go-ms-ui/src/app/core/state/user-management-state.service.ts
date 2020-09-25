import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';
import {takeLast} from "rxjs/operators";

@Injectable({providedIn: 'root'})
export class UserManagementState {

  constructor() {
  }

  private loggedInUserId$ = new BehaviorSubject<number>(null);
  private loggedInUserIdLoaded = false;
  private loggedInUserIdUpdating = false;

  setLoggedInUserId (userId: number): void {
    this.loggedInUserIdLoaded = true;
    this.loggedInUserId$.next(userId);
  }

  getLoggedInUserId$(): Observable<number> {
    return this.loggedInUserId$.asObservable().pipe(takeLast(1))  }

  isLoggedInUserIdLoaded(): boolean {
    return this.loggedInUserIdLoaded;
  }

  setLoggedInUserIdLoaded(loggedInUserIdLoaded: boolean): void {
    this.loggedInUserIdLoaded = loggedInUserIdLoaded;
  }

  isLoggedInUserIdUpdating(): boolean {
    return this.loggedInUserIdUpdating;
  }

  setLoggedInUserIdUpdating(loggedInUserIdUpdating: boolean): void {
    this.loggedInUserIdUpdating = loggedInUserIdUpdating;
  }
/*
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
  }*/
}
