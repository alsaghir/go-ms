import {Injectable, TemplateRef} from '@angular/core';
import {
  NbComponentStatus,
  NbGlobalPosition,
  NbLayoutDirection,
  NbLayoutDirectionService,
  NbMediaBreakpointsService,
  NbMenuService,
  NbSidebarService,
  NbThemeService,
  NbToastRef,
  NbToastrService,
  NbWindowService
} from '@nebular/theme';
import {LayoutDirection} from '../../common/constant';
import {Observable} from 'rxjs';
import {NbMediaBreakpoint} from '@nebular/theme/services/breakpoints.service';
import {NbAuthResult, NbAuthService, NbAuthToken, NbTokenService, NbTokenStorage} from '@nebular/auth';
import {UserCredentials} from '../../common/interface';
import {NbAccessControl, NbAclService} from '@nebular/security';
import {NbComponentType} from '@nebular/theme/components/cdk/overlay/mapping';
import {NbWindowConfig} from '@nebular/theme/components/window/window.options';
import {NbWindowRef} from '@nebular/theme/components/window/window-ref';

@Injectable({providedIn: 'root'})
export class NbUtil {

  constructor(private nbToastrService: NbToastrService,
              private nbThemeService: NbThemeService,
              private nbLayoutDirectionService: NbLayoutDirectionService,
              private nbSidebarService: NbSidebarService,
              private nbMenuService: NbMenuService,
              private nbMediaBreakpointsService: NbMediaBreakpointsService,
              private nbTokenService: NbTokenService,
              private nbTokenStorage: NbTokenStorage,
              private nbAuthService: NbAuthService,
              private nbAclService: NbAclService,
              private nbWindowService: NbWindowService) {
  }

  private toastService(): NbToastrService {
    return this.nbToastrService;
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

  private themeService(): NbThemeService {
    return this.nbThemeService;
  }

  onThemeChange(): Observable<any> {
    return this.themeService().onThemeChange();
  }

  currentTheme(): string {
    return this.themeService().currentTheme;
  }

  onMediaQueryChange(): Observable<NbMediaBreakpoint[]> {
    return this.themeService().onMediaQueryChange();
  }

  changeTheme(themeName: string): void {
    this.themeService().changeTheme(themeName);
  }

  private mediaBreakpointsService(): NbMediaBreakpointsService {
    return this.nbMediaBreakpointsService;
  }

  getBreakpointsMap(): {
    [breakpoint: string]: number;
  } {
    return this.mediaBreakpointsService().getBreakpointsMap();
  }

  private sidebarService(): NbSidebarService {
    return this.nbSidebarService;
  }

  toggle(compact: boolean, tag: string): void {
    this.sidebarService().toggle(compact, tag);
  }

  menuService(): NbMenuService {
    return this.nbMenuService;
  }

  navigateHome(): void {
    this.nbMenuService.navigateHome();
  }

  private tokenStorage(): NbTokenStorage {
    return this.nbTokenStorage;
  }

  getToken(): NbAuthToken {
    return this.tokenStorage().get();
  }

  private authService(): NbAuthService {
    return this.nbAuthService;
  }

  authenticate(strategyName: string, data: UserCredentials): Observable<NbAuthResult> {
    return this.authService().authenticate(strategyName, data);
  }

  onTokenChange(): Observable<NbAuthToken> {
    return this.authService().onTokenChange();
  }

  isAuthenticated(): Observable<boolean> {
    return this.authService().isAuthenticated();
  }

  private aclService(): NbAclService {
    return this.nbAclService;
  }

  setAccessControl(list: NbAccessControl): void {
    return this.aclService().setAccessControl(list);
  }

  private windowService(): NbWindowService {
    return this.nbWindowService;
  }

  openWindow(windowContent: TemplateRef<any> | NbComponentType,
             windowConfig?: Partial<NbWindowConfig>): NbWindowRef {
    return this.windowService().open(windowContent, windowConfig);
  }

}
