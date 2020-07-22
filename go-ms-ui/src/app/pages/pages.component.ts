import {Component, OnInit} from '@angular/core';
import {InitializationUtil} from '../core/util';
import {PrivilegeConstant} from '../common/constant/backendrelated/privilege-constant';

@Component({
  selector: 'app-pages',
  template: `
    <router-outlet></router-outlet>

    <app-locale-translate>
    </app-locale-translate>
    <app-error-locale-translate>
    </app-error-locale-translate>`,
})
export class PagesComponent implements OnInit {

  constructor(private initializationUtil: InitializationUtil) {
    this.initializationUtil.initialize();
  }

  ngOnInit(): void {
  }

}
