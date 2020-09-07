import {Component, OnDestroy, OnInit} from '@angular/core';
import {Location} from '@angular/common';
import {takeUntil} from 'rxjs/operators';
import {Subject} from 'rxjs';

import {NbAccessControl, NbAclRole} from '@nebular/security';

import {LayoutDirection} from '../common/constant';
import {UserManagementFacade} from '../core/facade';
import {CommonUtil, LoggerUtil, NbUtil} from '../core/util';


@Component({
  selector: 'app-pages',
  template: `<router-outlet></router-outlet>

    <app-locale-translate>
    </app-locale-translate>
    <app-error-locale-translate>
    </app-error-locale-translate>`,
})
export class PagesComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();

  constructor(private location: Location,
              private userManagementFacade: UserManagementFacade,
              private loggerUtil: LoggerUtil,
              private commonUtil: CommonUtil,
              private nbUtil: NbUtil) {

  }

  ngOnInit(): void {
    this.initializeDirectionByLocale();
    // this.listenProfilesAndPrivileges();
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  private initializeDirectionByLocale(): void {

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

  private listenProfilesAndPrivileges(): void {
    this.userManagementFacade.getProfiles$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        profiles => {
          const nbAcl: NbAccessControl = {};
          profiles.forEach(profile => {
            const nbAclRole: NbAclRole = {};
            profile.privileges.forEach(privilege => nbAclRole[privilege] = '*');
            console.log(nbAclRole);
            nbAcl[profile.id.toString()] = nbAclRole;
            console.log(nbAcl);
          });
          this.nbUtil.setAccessControl(nbAcl);
        },
        err => {
          this.commonUtil.handleRetrievingDataError(err);
        }
      );
  }

}
