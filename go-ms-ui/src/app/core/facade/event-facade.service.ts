import {Injectable} from '@angular/core';
import {EventState} from '../state/event-state.service';
import {Observable} from 'rxjs';

@Injectable({providedIn: 'root'})
export class EventFacade {
  constructor(private eventState: EventState) {
  }


  localeViewRendered(isRendered: true): void {
    this.eventState.localeViewRendered(isRendered);
  }

  localeViewRendered$(): Observable<boolean> {
    return this.eventState.isLocaleViewRendered();
  }

}
