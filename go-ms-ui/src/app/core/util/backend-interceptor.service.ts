import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, of as observableOf} from 'rxjs';

import {API_BACKEND_HOST_ACTIVE, BackendUrls} from '../../common/config';
import {LoggerUtil} from './logger-util.service';
import jsonData from '../../../assets/mock.json';

@Injectable()
export class BackendInterceptor implements HttpInterceptor {

  constructor(private loggerUtil: LoggerUtil) {
  }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    if (!API_BACKEND_HOST_ACTIVE.MOCK) {
      return next.handle(req);
    } else {
      this.loggerUtil.debug('***START Requesting Mock Data***');
      this.loggerUtil.debug(req);
      const data = mockData.filter(dataForUrl => dataForUrl.url === req.url);
      this.loggerUtil.debug(data);
      this.loggerUtil.debug('***END Requesting Mock Data***');
      return observableOf(new HttpResponse({body: data, status: 200}));
    }
  }

}

const mockData = [
  {
    url: BackendUrls.API_ENDPOINT(BackendUrls.API_LOGIN),
    json: jsonData.API_LOGIN
  }
];
