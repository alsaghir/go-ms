import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {BehaviorSubject, Subject} from 'rxjs';
import {map, takeUntil} from 'rxjs/operators';

import {LocaleHandlingUtil, NbUtil, RippleService} from '../../../core/util';
import {LocaleName, THEMES} from '../../../common/constant';
import {NbMenuItem} from '@nebular/theme';
import {EventFacade, UserManagementFacade} from '../../../core/facade';


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

  public constructor(private changeDetectorRef: ChangeDetectorRef,
                     private rippleService: RippleService,
                     private eventFacade: EventFacade,
                     private userManagementFacade: UserManagementFacade,
                     private localeHandlingUtil: LocaleHandlingUtil,
                     private nbUtil: NbUtil) {
  }

  ngOnInit(): void {

    this.userManagementFacade.getUserDetails$().subscribe(value => console.log(value));

    this.eventFacade.localeViewRendered$().pipe(takeUntil(this.destroy$))
      .subscribe((localeViewRendered: boolean) => {
        if (localeViewRendered) {
          this.userMenu = [{
            title: this.translationOf(LocaleName.instance.LOGOUT)
          }];
          this.changeDetectorRef.detectChanges();
        }
      });

    this.currentTheme = this.nbUtil.currentTheme();
    this.materialTheme$.next(this.currentTheme.startsWith('material'));

    const {xl} = this.nbUtil.getBreakpointsMap();
    this.nbUtil.onMediaQueryChange()
      .pipe(
        map(([, currentBreakpoint]) => currentBreakpoint.width < xl),
        takeUntil(this.destroy$),
      )
      .subscribe((isLessThanXl: boolean) => this.userPictureOnly = isLessThanXl);

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
}
