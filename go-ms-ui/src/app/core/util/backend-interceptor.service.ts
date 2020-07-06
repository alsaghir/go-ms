import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse} from '@angular/common/http';
import {Observable, of as observableOf} from 'rxjs';
import {API_BACKEND_HOST_ACTIVE} from '../../common/config';
import mockData from '../../../assets/mock.json';
import {LoggerUtil} from './logger-util.service';


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
      const data = mockData;
      const pathVars = req.url.split('/');
      pathVars.splice(0, 1); // delete first empty element
      const responseBody = this.getResponseFrom(data, pathVars);
      this.loggerUtil.debug(responseBody);
      this.loggerUtil.debug('***END Requesting Mock Data***');
      return observableOf(new HttpResponse({body: responseBody, status: 200}));
    }
  }

  private getResponseFrom(data: {}, pathVars: string[]): {} {
    if (pathVars[0]) {
      const deletedPathVar = pathVars.splice(0, 1);
      data = data[deletedPathVar[0]];
      return this.getResponseFrom(data, pathVars);
    } else {
      return data;
    }
  }
}
