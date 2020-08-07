import {ModuleWithProviders, NgModule, Optional, SkipSelf} from '@angular/core';
import {NbMenuModule, NbSidebarModule, NbThemeModule, NbToastrModule, NbWindowModule} from '@nebular/theme';
import {HTTP_INTERCEPTORS, HttpClientModule} from '@angular/common/http';

import {NbAuthModule} from '@nebular/auth';
import {NbRoleProvider, NbSecurityModule} from '@nebular/security';

import {RoleProviderFacade, TokenStrategyFacade} from './facade';
import {NbJwtToken} from '../common/implementation';
import {BackendInterceptor} from './util';

export const CORE_PROVIDERS = [
  NbAuthModule.forRoot({
    forms: {}, strategies: [
      TokenStrategyFacade.setup({
        name: 'jwt',
        token: {
          class: NbJwtToken
        }
      })
    ]
  }).providers,
  NbSecurityModule.forRoot().providers,
  { provide: NbRoleProvider, useClass: RoleProviderFacade },

  NbThemeModule.forRoot({
    name: 'cosmic',
  }).providers,

  NbToastrModule.forRoot().providers,
  NbSidebarModule.forRoot().providers,
  NbMenuModule.forRoot().providers,
  NbWindowModule.forRoot().providers

];

export const HTTP_CLIENT_INTERCEPTORS = [
  {provide: HTTP_INTERCEPTORS, useClass: BackendInterceptor, multi: true},
];

@NgModule({
  imports: [HttpClientModule],
  exports: [HttpClientModule],
  declarations: [],
})
export class CoreModule {
  // https://angular.io/guide/singleton-services#prevent-reimport-of-the-greetingmodule
  constructor(@Optional() @SkipSelf() parentModule: CoreModule) {
    if (parentModule) {
      throw new Error(`CoreModule has already been loaded. Import Core modules in the AppModule only.`);
    }
  }

  static forRoot(): ModuleWithProviders<CoreModule> {
    return {
      ngModule: CoreModule,
      providers: [
        ...CORE_PROVIDERS,
        ...HTTP_CLIENT_INTERCEPTORS
      ],
    };
  }
}
