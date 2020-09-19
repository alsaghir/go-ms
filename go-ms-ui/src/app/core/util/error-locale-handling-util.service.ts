import {ElementRef, Injectable, QueryList} from '@angular/core';
import {ErrorLocaleName} from '../../common/constant/backendrelated';
import {ApiError} from '../../common/model';

@Injectable({providedIn: 'root'})
export class ErrorLocaleHandlingUtil {

  private inputElements: QueryList<ElementRef<HTMLInputElement>>;
  private storedLocale: { [idOrName: string]: string } = {};

  constructor() {
  }

  translateByCodesAndNames(errorsCodesAndNames: string[]): string[] {
    if (!errorsCodesAndNames || !Array.isArray(errorsCodesAndNames)) {
      throw new TypeError('Error code or name is not found !');
    }

    return errorsCodesAndNames.map(errorCodeOrName => {

      // detecting error code by its pattern which is like e00002
      if (Number(errorCodeOrName.substr(1)) && !isNaN(Number(errorCodeOrName.substr(1)))) {
        return this.getTranslationFromElementsByCode(errorCodeOrName);
      } else { // It's an error name
        return this.getTranslationFromElementsByName(errorCodeOrName);
      }
    });
  }

  translateByCodes(errorsCodes: string[]): string[] {
    if (!errorsCodes || !Array.isArray(errorsCodes)) {
      throw new TypeError('Error code is not found !');
    }
    return errorsCodes.map(errorCode => this.getTranslationFromElementsByCode(errorCode));
  }

  translateByName(errorsNames: string[]): string[] {
    if (!errorsNames || !Array.isArray(errorsNames)) {
      throw new TypeError('Error name is not found !');
    }

    return errorsNames.map(errorName => this.getTranslationFromElementsByName(errorName));
  }

  private fillMessagesFromArray(messages: string[], codesOrNames: string[]): void {
    const firstCodeOrName = codesOrNames[0];
    if (Number(firstCodeOrName.substr(1)) && !isNaN(Number(firstCodeOrName.substr(1)))) {
      codesOrNames.forEach(code => {
        messages.push(this.storedLocale[code]);
      });
    } else {
      codesOrNames.forEach(name => {
        messages.push(this.storedLocale[name]);
      });
    }
  }

  private getTranslationFromElementsByCode(errorCode: string): string {
    let inputElement = this.inputElements.find((element) => element.nativeElement.id === errorCode);
    if (inputElement == null)
      inputElement = this.inputElements.find((element) => element.nativeElement.name === ErrorLocaleName.instance.UNEXPECTED_ERROR);

    return inputElement.nativeElement.value;
  }

  private getTranslationFromElementsByName(errorName: string): string {
    return this.inputElements.find((element) => element.nativeElement.name === errorName).nativeElement.value;
  }

  /** Hold references to elements and in memory to use either for translation
   * lookup
   * @param inputElements the input elements holding translated locales
   */
  setParentElementBundle(inputElements: QueryList<ElementRef<HTMLInputElement>>): void {
    this.inputElements = inputElements;
    inputElements.forEach((element: ElementRef<HTMLInputElement>) => {
      this.storedLocale[element.nativeElement.id] = element.nativeElement.value;
      this.storedLocale[element.nativeElement.name] = element.nativeElement.value;
      if (!ErrorLocaleName.instance.hasOwnProperty(element.nativeElement.name)) {
        throw new TypeError('Error code or name is not found !');
      }
    });
  }

  codesOf(apiError: ApiError): string[] {
    return Object.keys(apiError.apiSubErrors);
  }
}
