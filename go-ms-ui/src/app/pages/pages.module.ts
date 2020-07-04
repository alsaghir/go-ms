import {NgModule} from '@angular/core';

import {PagesRoutingModule} from './pages-routing.module';
import {PagesComponent} from "./pages.component";
import {SharedModule} from "../shared/shared.module";
import {ErrorTranslateComponent} from './hidden/error-translate.component';
import {OtherTranslateComponent} from './hidden/other-translate.component';

@NgModule({
  imports: [
    PagesRoutingModule,

    SharedModule
  ],
  declarations: [PagesComponent, ErrorTranslateComponent, OtherTranslateComponent],
})
export class PagesModule {
}
