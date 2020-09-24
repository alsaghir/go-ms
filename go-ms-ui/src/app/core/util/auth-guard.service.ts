import {Injectable} from "@angular/core";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot} from "@angular/router";
import {NbUtil} from "./nb-util.service";
import {Observable} from "rxjs";
import {tap} from "rxjs/operators";
import {LoggerUtil} from "./logger-util.service";
import {LOGIN_PATH} from "../../common/config";

@Injectable({providedIn: 'root'})
export class AuthGuard implements CanActivate {

  constructor(private router: Router, private nbUtil: NbUtil, private logger: LoggerUtil) {
  }

  canActivate(
    next: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> {
    return this.nbUtil.isAuthenticated().pipe(tap(isAuthenticated => {
      if (!isAuthenticated)
        this.router.navigate([LOGIN_PATH])
          .then(isSuccessNavigation =>
            this.logger.debug(isSuccessNavigation ? 'Successful Auth Navigation' : 'Failed Auth Navigation'));
    }));
  }
}
