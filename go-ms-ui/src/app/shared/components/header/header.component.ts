import {Component, OnDestroy, OnInit} from '@angular/core';
import {Observable, Subject} from 'rxjs';
import {map, takeUntil} from 'rxjs/operators';

import {NbUtil, RippleService} from '../../../core/util';
import {THEMES} from '../../../common/constant';


@Component({
  selector: 'app-header',
  styleUrls: ['./header.component.scss'],
  templateUrl: './header.component.html',
})
export class HeaderComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();

  readonly materialTheme$: Observable<boolean>;
  readonly themes: {name: string, value: string}[];

  userPictureOnly = false;
  currentTheme = THEMES.DEFAULT.value;

  userMenu = [{title: 'Profile'}, {title: 'Log out'}];

  public constructor(private rippleService: RippleService, private nbUtil: NbUtil) {
    this.themes = Object.keys(THEMES).map(themeKey => THEMES[themeKey]);
    this.materialTheme$ = this.nbUtil.onThemeChange()
      .pipe(map(theme => {
        const themeName: string = theme?.name || '';
        return themeName.startsWith('material');
      }));
  }

  ngOnInit(): void {
    this.currentTheme = this.nbUtil.currentTheme();

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
}
