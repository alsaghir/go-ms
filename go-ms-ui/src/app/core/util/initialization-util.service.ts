import {Injectable} from '@angular/core';
import {Location} from '@angular/common';
import {LayoutDirectionUtil} from './layout-direction-util.service';
import {LayoutDirection} from '../../common/constant';

@Injectable({providedIn: 'root'})
export class InitializationUtil {

  constructor(private location: Location,
              private layoutDirectionUtil: LayoutDirectionUtil) {
  }

  initialize(): void{
    // i.e. from /ar-EG/ to ar-EG
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

    this.layoutDirectionUtil.setDirection(direction);
  }

}
