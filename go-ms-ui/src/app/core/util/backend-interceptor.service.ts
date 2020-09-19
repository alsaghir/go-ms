import {Injectable} from '@angular/core';
import {HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable, throwError} from 'rxjs';
import {LoggerUtil} from './logger-util.service';
import {NbUtil} from './nb-util.service';
import {catchError} from "rxjs/operators";
import {ApiError, DomainError} from "../../common/model";
import {ErrorLocaleName} from "../../common/constant/backendrelated";
import {CommonUtil} from "./common-util.service";

@Injectable()
export class BackendInterceptor implements HttpInterceptor {

  constructor(private commonUtil: CommonUtil,
              private loggerUtil: LoggerUtil,
              private nbUtil: NbUtil) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // Adding auth header
    req = req.clone({setHeaders: {Authorization: `Bearer ${this.nbUtil.getToken()}`}});

    // Continue
    return next.handle(req).pipe(
      // error conversion to codes or generic
      catchError((err: HttpErrorResponse) => {
        this.loggerUtil.error(err);

        const domainError = new DomainError();

        if (err.error instanceof ErrorEvent) { // client-side error

          domainError.addErrorName(ErrorLocaleName.instance.UNEXPECTED_ERROR);
        } else { // server-side error

          if (err.status === 405) {
            domainError.addErrorName(ErrorLocaleName.instance.NOT_ALLOWED);
          } else if ((err.error as ApiError).apiSubErrors != null) {
            domainError.addErrorCode(...(err.error as ApiError).apiSubErrors.map(apiError => apiError.code));
          } else {
            domainError.addErrorName(ErrorLocaleName.instance.UNEXPECTED_ERROR);
          }
        }

        this.commonUtil.handleHttpError(domainError)
        return throwError(domainError);
      })
    );
  }
}

const mockData = [];
