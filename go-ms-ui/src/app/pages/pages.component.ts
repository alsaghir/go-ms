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
  styleUrls: ['pages.component.scss'],
  template: `
    <router-outlet></router-outlet>

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

  /*private listenProfilesAndPrivileges(): void {
    this.userManagementFacade.getAllProfiles$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        profiles => {
          const nbAcl: NbAccessControl = {}; // keys are profiles ids
          profiles.forEach(profile => {
            const nbAclRole: NbAclRole = {}; // keys are privileges
            this.userManagementFacade.getProfilePrivileges$(profile.id)
              .pipe(takeUntil(this.destroy$))
              .subscribe(privileges => privileges.forEach(privilege => nbAclRole[privilege] = '*'));
            nbAcl[profile.id.toString()] = nbAclRole;
          });
          console.log(nbAcl);
          this.nbUtil.setAccessControl(nbAcl);
        },
        err => {
          this.commonUtil.handleRetrievingDataError(err);
        }
      );
  }*/

}
