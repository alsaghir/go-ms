import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {map, takeUntil} from 'rxjs/operators';
import {NbMenuItem} from '@nebular/theme';

import {
  CommonUtil,
  ErrorLocaleHandlingUtil,
  LocaleHandlingUtil,
  LoggerUtil,
  NbUtil,
  RippleService
} from '../../../core/util';


import {EventFacade, UserManagementFacade} from '../../../core/facade';
import {LocaleName, THEMES} from "../../../common/constant";
import {User} from "../../../common/model";


@Component({
  selector: 'app-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();

  readonly themes: { name: string, value: string }[] = Object.keys(THEMES).map(themeKey => THEMES[themeKey]);

  materialTheme$ = new BehaviorSubject<boolean>(false);
  userPictureOnly = false;
  currentTheme: string = null;
  userMenu: NbMenuItem[] = null;
  loggedInUserId: number = null;
  loggedInUser: User = null;

  public constructor(private changeDetectorRef: ChangeDetectorRef,
                     private rippleService: RippleService,
                     private eventFacade: EventFacade,
                     private userManagementFacade: UserManagementFacade,
                     private localeHandlingUtil: LocaleHandlingUtil,
                     private errorLocaleHandlingUtil: ErrorLocaleHandlingUtil,
                     private loggerUtil: LoggerUtil,
                     private commonUtil: CommonUtil,
                     private nbUtil: NbUtil) {
  }

  ngOnInit(): void {
    this.loadInfoNeedsLocaleTranslation();

    this.currentTheme = this.nbUtil.currentTheme();
    this.materialTheme$.next(this.currentTheme.startsWith('material'));

    this.ListenBreakPointForMediaQueryChange();

    this.listenThemeChange();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  changeTheme(themeName: string): void {
    this.nbUtil.changeTheme(themeName);
  }

  toggleSidebar(): boolean {
    this.nbUtil.toggle(true, 'menu-sidebar');
    return false;
  }

  navigateHome(): boolean {
    this.nbUtil.navigateHome();
    return false;
  }

  private translationOf(text: string): string {
    return this.localeHandlingUtil.translationOf(text);
  }

  private loadUser(): void {
    this.userManagementFacade.getLoggedInUserId$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        userId => {
          this.loggedInUserId = userId;

          this.userManagementFacade.getUser$(userId).pipe(takeUntil(this.destroy$))
            .subscribe(user => this.loggedInUser = user);
        }
      );
  }

  private loadInfoNeedsLocaleTranslation(): void {
    this.eventFacade.localeViewRendered$()
      .pipe(takeUntil(this.destroy$))
      .subscribe((localeViewRendered: boolean) => {
        if (localeViewRendered) {

          this.userMenu = [{
            title: this.translationOf(LocaleName.instance.LOGOUT)
          }];

          this.loadUser();

          this.changeDetectorRef.detectChanges();
        }
      });
  }

  private ListenBreakPointForMediaQueryChange(): void {
    const {xl} = this.nbUtil.getBreakpointsMap();
    this.nbUtil.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);
  }

  private listenThemeChange(): void {
    this.nbUtil.onThemeChange()
      .pipe(
        map(({name}) => name),
        takeUntil(this.destroy$),
      ).subscribe(themeName => {
      this.currentTheme = themeName;
      this.materialTheme$.next(themeName.startsWith('material'));
      this.rippleService.toggle(themeName?.startsWith('material'));
    });
  }
}
