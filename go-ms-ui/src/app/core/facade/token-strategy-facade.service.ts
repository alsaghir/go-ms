import {NbAuthResult, NbAuthStrategy, NbAuthStrategyClass, NbPasswordAuthStrategyOptions} from '@nebular/auth';
import {Injectable} from '@angular/core';
import {Observable, of as observableOf} from 'rxjs';
import {NbAuthStrategyOptions} from '@nebular/auth/strategies/auth-strategy-options';
import {ApiError, JwtToken, User} from '../../common/interface';
import {CryptoUtil, ErrorLocaleHandlingUtil, LoggerUtil} from '../util';
import {BackendUrls} from '../../common/config';
import {ErrorLocaleName} from '../../common/constant';
import {HttpClient, HttpErrorResponse, HttpResponse} from '@angular/common/http';
import {catchError, map} from 'rxjs/operators';
import {NbJwtToken} from '../../common/implementation';


@Injectable({providedIn: 'root'})
export class TokenStrategyFacadeService extends NbAuthStrategy {

  static setup(options: NbAuthStrategyOptions): [NbAuthStrategyClass, NbPasswordAuthStrategyOptions] {
    return [TokenStrategyFacadeService, options];
  }

  constructor(private httpClient: HttpClient,
              private cryptoUtil: CryptoUtil,
              private loggerUtil: LoggerUtil,
              private errorLocaleHandlingUtil: ErrorLocaleHandlingUtil) {
    super();
  }

  authenticate(data?: any): Observable<NbAuthResult> {
    const userData = data as User;

    userData.password = this.encrypt(userData.password);

    const api = BackendUrls.API_ENDPOINT(BackendUrls.API_LOGIN);

    return this.httpClient.post<JwtToken>(api, userData, {observe: 'response'})
      .pipe(
        map(
          (response: HttpResponse<JwtToken>) =>
            new NbAuthResult(true, response, null, [], [], new NbJwtToken(response.body.token, this.getName()))
        ),
        catchError((error: any, caught: Observable<NbAuthResult>) => {
            this.loggerUtil.error(error);
            const errorCodesOrNames: string[] = [];

            if (error.error as ApiError && (error.error as ApiError).errorCodes) {
              errorCodesOrNames.push(...Object.keys((error.error as ApiError).errorCodes));
            } else if (error as HttpErrorResponse || (error as HttpErrorResponse).status === 0) {
              errorCodesOrNames.push(ErrorLocaleName.getInstance().UNEXPECTED_ERROR);
            } else {
              errorCodesOrNames.push(ErrorLocaleName.getInstance().UNEXPECTED_ERROR);
            }
            return observableOf(new NbAuthResult(false, caught, null, errorCodesOrNames));
          }
        ));
  }


  logout(): Observable<NbAuthResult> {
    console.log('logout method call');
    return undefined;
  }

  refreshToken(data?: any): Observable<NbAuthResult> {
    console.log('refreshToken method call');
    return undefined;
  }

  register(data?: any): Observable<NbAuthResult> {
    console.log('register method call');
    return undefined;
  }

  requestPassword(data?: any): Observable<NbAuthResult> {
    console.log('requestPassword method call');
    return undefined;
  }

  resetPassword(data?: any): Observable<NbAuthResult> {
    console.log('resetPassword method call');
    return undefined;
  }

  private encrypt(password: string): string {
    const aesKey = this.cryptoUtil.generateAesKey();
    const encryptedKey = this.cryptoUtil.encryptRsa(aesKey);
    const encryptedPassword = this.cryptoUtil.encryptAes(password, aesKey);
    return this.cryptoUtil.encode64(encryptedKey) + ';' + this.cryptoUtil.encode64(encryptedPassword);
  }
}
