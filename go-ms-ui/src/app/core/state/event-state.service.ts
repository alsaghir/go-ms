import {Injectable} from '@angular/core';
import {BehaviorSubject, Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class EventState {

  private localeViewRendered$ = new BehaviorSubject<boolean>(false);

  localeViewRendered(isRendered: true): void {
    this.localeViewRendered$.next(isRendered);
  }

  isLocaleViewRendered(): Observable<boolean> {
    return this.localeViewRendered$.asObservable();
  }
}
