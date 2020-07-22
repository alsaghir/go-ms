import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {BackendUrls} from '../../common/config';
import {Observable, of as observableOf} from 'rxjs';
import {ApiError, JwtToken, UserCredentials, UserDetails, UserInfo} from '../../common/interface';
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
        catchError((err: any, caught: Observable<NbAuthResult>) => {
            this.loggerUtil.error(err);
            const errorCodesOrNames: string[] = [];

            if ((err.error as ApiError).apiSubErrors != null) {

              errorCodesOrNames.push(...Object.keys((err.error as ApiError).apiSubErrors));
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
    return this.http.get<UserDetails>(apiUrl);
  }

  getUsersInfo$(): Observable<UserInfo[]> {
    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_USERS_INFO);
    return this.http.get<UserInfo[]>(apiUrl);
  }
}
