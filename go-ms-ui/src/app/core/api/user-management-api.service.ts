import {Injectable} from '@angular/core';
import {HttpClient, HttpResponse} from '@angular/common/http';
import {Observable, of as observableOf} from 'rxjs';
import {catchError, map} from 'rxjs/operators';
import {NbAuthResult} from '@nebular/auth';

import {BackendUrls} from '../../common/config';
import {NbJwtToken} from '../../common/implementation';
import {LoggerUtil} from '../util';
import {Collection, JwtToken, Profile, User, UserCredentials} from "../../common/model";
import {Privilege} from "../../common/model/resource/privilege";

@Injectable({providedIn: 'root'})
export class UserManagementApi {

  constructor(private http: HttpClient,
              private loggerUtil: LoggerUtil) {
  }

  getJwtToken$(userData: UserCredentials, strategyName: string): Observable<NbAuthResult> {

    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_JWT);

    return this.http.post<JwtToken>(apiUrl, userData, {observe: 'response'})
      .pipe(
        map(
          (response: HttpResponse<JwtToken>) =>
            new NbAuthResult(true, response, null, [], [], new NbJwtToken(response.body.token, strategyName))
        ),
        catchError((errorCodesOrNames: string[]) => {

            return observableOf(new NbAuthResult(false, null, null, errorCodesOrNames));
          }
        )
      );
  }

  getProfiles$(): Observable<Collection<Profile>> {

    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_RESOURCE_PROFILES);

    return this.http.get<Collection<Profile>>(apiUrl);
  }

  getPrivileges$(): Observable<Collection<Privilege>> {
    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_RESOURCE_PRIVILEGES) + '/search/findAllBy';

    return this.http.get<Collection<Privilege>>(apiUrl);
  }

  getPrivilegesOf(profile: Profile): Observable<Collection<Privilege>> {
    const apiUrl = profile._links[BackendUrls.API_RESOURCE_PRIVILEGES].href;

    return this.http.get<Collection<Privilege>>(apiUrl);
  }

  getUser$(id: number): Observable<User> {

    const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_RESOURCE_USERS) + `/${id}`;

    return this.http.get<User>(apiUrl);
  }

  /*
    getUserInfo$(id: number): Observable<UserInfo> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_USERS + `/${id}`);
      return this.http.get<UserInfo>(apiUrl);
    }

    getUserProfiles$(id: number): Observable<Profile[]> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_USERS + `/${id}/` + BackendUrls.API_PROFILES);
      return this.http.get<Profile[]>(apiUrl);
    }

    getProfilePrivileges$(id: number): Observable<string[]> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_PROFILES + `/${id}/` + BackendUrls.API_PRIVILEGES);
      return this.http.get<string[]>(apiUrl);
    }

    getAllUsersInfo$(): Observable<UserInfo[]> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_USERS);
      return this.http.get<UserInfo[]>(apiUrl);
    }

    addUser(userInfo: UserInfo): Observable<HttpResponse<any>> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_USERS);
      return this.http.post(apiUrl, userInfo, {observe: 'response'});
    }

    getAllProfiles$(): Observable<Profile[]> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_PROFILES);
      return this.http.get<Profile[]>(apiUrl);
    }

    getAllPrivileges$(): Observable<string[]> {
      const apiUrl = BackendUrls.API_ENDPOINT(BackendUrls.API_PRIVILEGES);
      return this.http.get<string[]>(apiUrl);
    }*/


}
