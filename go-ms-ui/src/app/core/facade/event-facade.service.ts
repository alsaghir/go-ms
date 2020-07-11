import {Injectable} from '@angular/core';
import {EventState} from '../state';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class EventFacade {
  constructor(private eventState: EventState) {
  }


  setLocaleViewRendered(isRendered: true): void {
    this.eventState.setLocaleViewRendered(isRendered);
  }

  localeViewRendered$(): Observable<boolean> {
    return this.eventState.isLocaleViewRendered$();
  }

}
