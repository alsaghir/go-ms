import {Injectable} from '@angular/core';
import {Location} from '@angular/common';
import {LayoutDirection} from '../../common/constant';
import {NbUtil} from './nb-util.service';

@Injectable({providedIn: 'root'})
export class InitializationUtil {

  constructor(private location: Location,
              private nbUtil: NbUtil) {
  }

  initialize(): void {

    /* Changing pattern of locale detected from URL
    i.e. from /ar-EG/ to ar-EG
    */
    let currentLocaleInUrlPath: string = this.location.prepareExternalUrl('');
    currentLocaleInUrlPath = currentLocaleInUrlPath.substring(currentLocaleInUrlPath.indexOf('/') + 1);
    currentLocaleInUrlPath = currentLocaleInUrlPath.substring(0, currentLocaleInUrlPath.indexOf('/'));
    const languageOnly = currentLocaleInUrlPath.substring(0, 2);

    let direction: LayoutDirection = LayoutDirection.LTR;

    switch (languageOnly.toLocaleLowerCase()) {
      case 'ar':
        direction = LayoutDirection.RTL;
        break;
    }

    this.nbUtil.setDirection(direction);
  }

}
