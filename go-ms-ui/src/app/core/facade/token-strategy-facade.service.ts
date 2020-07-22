import {NbAuthResult, NbAuthStrategy, NbAuthStrategyClass, NbPasswordAuthStrategyOptions} from '@nebular/auth';
import {Injectable} from '@angular/core';
import {Observable} from 'rxjs';
import {NbAuthStrategyOptions} from '@nebular/auth/strategies/auth-strategy-options';
import {UserCredentials} from '../../common/interface';
import {CryptoUtil, LoggerUtil} from '../util';
import {PASSWORD_ENCRYPTION_ENABLED} from '../../common/config';
import {HttpClient} from '@angular/common/http';
import {UserManagementApi} from '../api';


@Injectable({providedIn: 'root'})
export class TokenStrategyFacade extends NbAuthStrategy {

  static setup(options: NbAuthStrategyOptions): [NbAuthStrategyClass, NbPasswordAuthStrategyOptions] {
    return [TokenStrategyFacade, options];
  }

  constructor(private httpClient: HttpClient,
              private cryptoUtil: CryptoUtil,
              private loggerUtil: LoggerUtil,
              private userManagementApi: UserManagementApi) {
    super();
  }

  authenticate(data?: any): Observable<NbAuthResult> {
    const userData = data as UserCredentials;

    if (PASSWORD_ENCRYPTION_ENABLED) {
      userData.password = this.encrypt(userData.password);
    }

    return this.userManagementApi.getJwtToken$(userData, this.getName());
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
