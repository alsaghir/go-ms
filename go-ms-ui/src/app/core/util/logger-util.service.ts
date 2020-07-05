import {Injectable} from '@angular/core';

@Injectable({providedIn: 'root'})
export class LoggerUtil {

  error(message: any): void {
    if (window.console) {
      console.log(message);
    }
  }
}
