import {ErrorHandler, Injectable} from '@angular/core';
import {LoggerUtil} from "./logger-util.service";
import {HttpErrorResponse} from "@angular/common/http";

@Injectable({providedIn: 'root'})
export class GlobalErrorHandler implements ErrorHandler {

  constructor(private logger: LoggerUtil) {
  }

  handleError(error) {

    if (error instanceof HttpErrorResponse) {
      this.logger.trace(error as HttpErrorResponse);
    } else if (error instanceof Error) {
      this.logger.error(error as Error);
    } else {
      this.logger.fatal(error);
    }
  }
}
