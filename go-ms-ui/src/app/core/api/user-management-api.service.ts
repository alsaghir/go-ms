import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {BackendUrls} from '../../common/config';
import {Observable, of as observableOf} from 'rxjs';
import {ApiError, JwtToken, User} from '../../common/interface';
import {catchError, map} from 'rxjs/operators';
import {NbAuthResult} from '@nebular/auth';
import {NbJwtToken} from '../../common/implementation';
import {ErrorLocaleName} from '../../common/constant';
import {LoggerUtil} from '../util';

@Injectable({providedIn: 'root'})
export class UserManagementApi {

  readonly API = BackendUrls.API_ENDPOINT(BackendUrls.API_LOGIN);

  constructor(private http: HttpClient,
              private loggerUtil: LoggerUtil) {
  }

  getJwtToken$(userData: User, strategyName: string): Observable<NbAuthResult> {
    return this.http.post<JwtToken>(this.API, userData, {observe: 'response'}).pipe(
      map(
        (response: HttpResponse<JwtToken>) =>
          new NbAuthResult(true, response, null, [], [], new NbJwtToken(response.body.token, strategyName))
      ),
      catchError((error: any, caught: Observable<NbAuthResult>) => {
          this.loggerUtil.error(error);
          const errorCodesOrNames: string[] = [];

          if (error.error as ApiError && (error.error as ApiError).apiSubErrors) {
            errorCodesOrNames.push(...Object.keys((error.error as ApiError).apiSubErrors));
          } else if (error as HttpErrorResponse || (error as HttpErrorResponse).status === 0) {
            errorCodesOrNames.push(ErrorLocaleName.getInstance().UNEXPECTED_ERROR);
          } else {
            errorCodesOrNames.push(ErrorLocaleName.getInstance().UNEXPECTED_ERROR);
          }
          return observableOf(new NbAuthResult(false, caught, null, errorCodesOrNames));
        }
      ));
  }
}
