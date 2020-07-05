import {Injectable} from '@angular/core';
import {NbLayoutDirection, NbLayoutDirectionService} from '@nebular/theme';
import {LayoutDirection} from '../../common/constant';

@Injectable({providedIn: 'root'})
export class LayoutDirectionUtil {

  constructor(private nbLayoutDirectionService: NbLayoutDirectionService) {
  }

  setDirection(direction: string | LayoutDirection): void {
    this.nbLayoutDirectionService.setDirection(direction as NbLayoutDirection);
  }
}
