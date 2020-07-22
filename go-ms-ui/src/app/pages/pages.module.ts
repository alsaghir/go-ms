import {NgModule} from '@angular/core';

import {PagesRoutingModule} from './pages-routing.module';
import {PagesComponent} from './pages.component';
import {SharedModule} from '../shared/shared.module';
import {LocaleTranslateComponent} from './hidden/locale-translate.component';
import {ErrorTranslateComponent} from './hidden/backendrelated/error-locale-translate.component';
import {LoginComponent} from './login/login.component';

@NgModule({
  imports: [
    PagesRoutingModule,

    SharedModule
  ],
  declarations: [
    PagesComponent,
    ErrorTranslateComponent,
    LocaleTranslateComponent,

    LoginComponent
  ],
})
export class PagesModule {
}
