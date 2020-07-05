import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {BackendUrls} from '../../common/config/backend-urls';

@Injectable({providedIn: 'root'})
export class UserManagementApi {

  readonly API = BackendUrls.API_ENDPOINT(BackendUrls.API_LOGIN);

  constructor(private http: HttpClient) {
  }

}
