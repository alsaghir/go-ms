import {Injectable} from '@angular/core';
import {LoggerUtil} from './logger-util.service';
import {ErrorLocaleHandlingUtil} from './error-locale-handling-util.service';
import {LocaleHandlingUtil} from './locale-handling-util.service';
import {NbUtil} from './nb-util.service';
import {LocaleName} from "../../common/constant";
import {Collection, DomainError, Resource} from "../../common/model";

@Injectable({providedIn: 'root'})
export class CommonUtil {

  constructor(private loggerUtil: LoggerUtil,
              private errorLocaleHandlingUtil: ErrorLocaleHandlingUtil,
              private localeHandlingUtil: LocaleHandlingUtil,
              private nbUtil: NbUtil) {
  }

  handleHttpError(domainError: DomainError): void {
    this.errorLocaleHandlingUtil.translateByCodes(domainError.getErrorsCodes())
      .forEach(translatedError => this.nbUtil.dangerToast(translatedError,
      this.localeHandlingUtil.translationOf(LocaleName.instance.ERROR),
      'bottom-right'));

    this.errorLocaleHandlingUtil.translateByName(domainError.getErrorsNames())
      .forEach(translatedError => this.nbUtil.dangerToast(translatedError,
        this.localeHandlingUtil.translationOf(LocaleName.instance.ERROR),
        'bottom-right'));
  }
}
