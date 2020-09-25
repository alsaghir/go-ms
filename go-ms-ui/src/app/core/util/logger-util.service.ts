import {Injectable} from '@angular/core';
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class LoggerUtil {

  error(error: Error): void {
    if (window.console) {
      console.error(error.stack);
    }
  }

  trace(error: HttpErrorResponse): void {
    if (window.console) {
      console.groupCollapsed('HttpErrorResponse');
      console.trace(error);
      console.groupEnd();
    }
  }

  fatal(error: any): void {
    if (window.console) {
      console.log(error);
    }
  }

  debug(message: any): void {
    if (window.console) {
      console.log(message);
    }
  }
}
