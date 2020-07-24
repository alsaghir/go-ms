import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, of as observableOf} from 'rxjs';

import {API_BACKEND_HOST_ACTIVE, BackendUrls} from '../../common/config';
import {LoggerUtil} from './logger-util.service';
import {NbUtil} from './nb-util.service';

import jsonData from '../../../assets/mock.json';

@Injectable()
export class BackendInterceptor implements HttpInterceptor {

  constructor(private loggerUtil: LoggerUtil,
              private nbUtil: NbUtil) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if (!API_BACKEND_HOST_ACTIVE.MOCK) {
      req = req.clone({setHeaders: {Authorization: `Bearer ${this.nbUtil.getToken()}`}});
      return next.handle(req);
    } else {
      this.loggerUtil.debug('***START Requesting Mock Data***');
      this.loggerUtil.debug(req);
      const data = mockData.filter(dataForUrl => dataForUrl.url === req.url)[0];
      this.loggerUtil.debug(data);
      this.loggerUtil.debug('***END Requesting Mock Data***');
      return observableOf(new HttpResponse({body: data.json, status: 200}));
    }
  }
}

const mockData = [
  {
    url: BackendUrls.API_ENDPOINT(BackendUrls.API_LOGIN),
    json: jsonData.API_LOGIN
  },
  {
    url: BackendUrls.API_ENDPOINT(BackendUrls.API_USER),
    json: jsonData.API_USER
  },
  {
    url: BackendUrls.API_ENDPOINT(BackendUrls.API_USERS_INFO),
    json: jsonData.API_USERS_INFO
  },
  {
    url: BackendUrls.API_ENDPOINT(BackendUrls.API_PROFILES),
    json: jsonData.API_PROFILES
  }
];
