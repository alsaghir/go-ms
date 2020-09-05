import {Component, OnInit} from '@angular/core';
import {NbMenuItem} from '@nebular/theme';

@Component({
  selector: 'app-pages',
  template: `
    <nb-layout windowMode>
      <nb-layout-header fixed>
        <app-header></app-header>
      </nb-layout-header>

      <nb-sidebar class="menu-sidebar" tag="menu-sidebar" responsive>
        <nb-menu [items]="MENU_ITEMS"></nb-menu>
      </nb-sidebar>

      <nb-layout-column>
        <router-outlet></router-outlet>
      </nb-layout-column>

      <nb-layout-footer fixed>
        <app-footer></app-footer>
      </nb-layout-footer>
    </nb-layout>
  `,
})
export class PrivateComponent implements OnInit {

  MENU_ITEMS: NbMenuItem[] = [
    {
      title: 'Users',
      icon: 'people-outline',
      link: '/pages/pr/users',
      home: true,
    },
    {
      title: 'Profiles',
      icon: 'people-outline',
      link: '/pages/pr/profiles',
      home: true,
    }
    ];

  constructor() {}

  ngOnInit(): void {
  }

}
