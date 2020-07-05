import {Injectable} from '@angular/core';
import {NbComponentStatus, NbGlobalPosition, NbThemeService, NbToastRef, NbToastrService} from '@nebular/theme';

@Injectable({providedIn: 'root'})
export class NbUtil {

  constructor(private nbToastrService: NbToastrService,
              private nbThemeService: NbThemeService) {
  }

  private toastService(): NbToastrService {
    return this.nbToastrService;
  }

  private themeService(): NbThemeService {
    return this.nbThemeService;
  }

  showToast(message: string, title: string, position: NbGlobalPosition, status: NbComponentStatus): NbToastRef {
    return this.toastService().show(message, title, {position: position as NbGlobalPosition, status: status as NbComponentStatus});
  }

  dangerToast(message: string, title: string, position: string | NbGlobalPosition): NbToastRef {
    return this.toastService().danger(message, title, {position: position as NbGlobalPosition});
  }

}
