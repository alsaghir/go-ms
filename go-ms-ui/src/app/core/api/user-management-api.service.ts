import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {BackendUrls} from '../../common/config';
import {Observable, of as observableOf} from 'rxjs';
import {ApiError, JwtToken, UserCredentials, UserDetails} from '../../common/interface';
import {catchError, map, tap} from 'rxjs/operators';
import {NbAuthResult} from '@nebular/auth';
import {NbJwtToken} from '../../common/implementation';
import {ErrorLocaleName} from '../../common/constant';
import {LoggerUtil} from '../util';

@Injectable({providedIn: 'root'})
export class UserManagementApi {

  constructor(private http: HttpClient,
              private loggerUtil: LoggerUtil) {
  }

  getJwtToken$(userData: UserCredentials, strategyName: string): Observable<NbAuthResult> {

    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_LOGIN);

    return this.http.post<JwtToken>(apiUrl, userData, {observe: 'response'})
      .pipe(
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
              errorCodesOrNames.push(ErrorLocaleName.instance.UNEXPECTED_ERROR);
            } else {
              errorCodesOrNames.push(ErrorLocaleName.instance.UNEXPECTED_ERROR);
            }
            return observableOf(new NbAuthResult(false, caught, null, errorCodesOrNames));
          }
        )
      );
  }

  getUserDetails$(): Observable<UserDetails> {
    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_USER);
    return this.http.get<UserDetails>(apiUrl).pipe(
      tap(x => console.log(x)),
      catchError((err, caught) => {
        console.log(caught);
        console.log(err);
        throw new Error('Error calling API');
      })
    );
  }
}
