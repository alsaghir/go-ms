import {Injectable} from '@angular/core';
import {ApiError} from '../../common/interface';
import {ErrorLocaleName} from '../../common/constant/backendrelated';
import {LoggerUtil} from './logger-util.service';
import {ErrorLocaleHandlingUtil} from './error-locale-handling-util.service';
import {LocaleHandlingUtil} from './locale-handling-util.service';
import {NbUtil} from './nb-util.service';
import {LocaleName} from "../../common/constant";

@Injectable({providedIn: 'root'})
export class CommonUtil {

  constructor(private loggerUtil: LoggerUtil,
              private errorLocaleHandlingUtil: ErrorLocaleHandlingUtil,
              private localeHandlingUtil: LocaleHandlingUtil,
              private nbUtil: NbUtil) {
  }

  handleRetrievingDataError(err: any): void {
    this.loggerUtil.error(err);
    const errorCodesOrNames: string[] = [];

    if (err != null
      && err.error != null
      && (err.error as ApiError).apiSubErrors != null) {
      (err.error as ApiError).apiSubErrors.forEach(apiSubError => errorCodesOrNames.push(apiSubError.code));
    } else {
      errorCodesOrNames.push(ErrorLocaleName.instance.UNEXPECTED_ERROR);
    }

    this.errorLocaleHandlingUtil.translateByCodesAndNames(errorCodesOrNames)
      .forEach(translatedError => this.nbUtil.dangerToast(translatedError,
        this.localeHandlingUtil.translationOf(LocaleName.instance.ERROR),
        'bottom-right'));
  }
}
