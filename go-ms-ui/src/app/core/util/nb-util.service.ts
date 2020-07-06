import {Injectable} from '@angular/core';
import {
  NbComponentStatus,
  NbGlobalPosition,
  NbLayoutDirection,
  NbLayoutDirectionService,
  NbThemeService,
  NbToastRef,
  NbToastrService
} from '@nebular/theme';
import {LayoutDirection} from '../../common/constant';

@Injectable({providedIn: 'root'})
export class NbUtil {

  constructor(private nbToastrService: NbToastrService,
              private nbThemeService: NbThemeService,
              private nbLayoutDirectionService: NbLayoutDirectionService) {
  }

  private toastService(): NbToastrService {
    return this.nbToastrService;
  }

  private themeService(): NbThemeService {
    return this.nbThemeService;
  }

  showToast(message: string, title: string, position: string | NbGlobalPosition, status: NbComponentStatus): NbToastRef {
    return this.toastService().show(message, title, {position: position as NbGlobalPosition, status: status as NbComponentStatus});
  }

  dangerToast(message: string, title: string, position: string | NbGlobalPosition): NbToastRef {
    return this.toastService().danger(message, title, {position: position as NbGlobalPosition});
  }

  setDirection(direction: string | LayoutDirection): void {
    this.nbLayoutDirectionService.setDirection(direction as NbLayoutDirection);
  }

}
