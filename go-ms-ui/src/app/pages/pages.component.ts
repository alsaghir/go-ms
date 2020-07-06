import {Component, OnInit} from '@angular/core';
import {InitializationUtil} from '../core/util';

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
