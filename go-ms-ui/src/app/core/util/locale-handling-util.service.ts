import {ElementRef, Injectable, QueryList} from '@angular/core';
import {LocaleName} from '../../common/constant';


@Injectable({providedIn: 'root'})
export class LocaleHandlingUtil {
  private inputElements: QueryList<ElementRef<HTMLInputElement>>;
  private storedLocale: { [name: string]: string } = {};

  constructor() {
  }

  translationOf(localeName: string): string {
    if (!localeName) {
      throw new TypeError('Translation name is not found !');
    }
    return this.getTranslationFromElement(localeName);
  }

  private getTranslationFromArray(localeName: string): string {
    return this.storedLocale[localeName];
  }

  private getTranslationFromElement(localeName: string): string {
    return this.inputElements.find((element) => element.nativeElement.id === localeName).nativeElement.value;
  }

  setParentElementBundle(inputElements: QueryList<ElementRef<HTMLInputElement>>): void {
    this.inputElements = inputElements;
    inputElements.forEach((element: ElementRef<HTMLInputElement>) => {
      this.storedLocale[element.nativeElement.id] = element.nativeElement.value;

      if (!LocaleName.getInstance().hasOwnProperty(element.nativeElement.id)) {
        throw new TypeError('Locale name is not found !');
      }
    });
  }
}
