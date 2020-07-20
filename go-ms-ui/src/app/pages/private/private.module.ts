import {NgModule} from '@angular/core';

import {UsersComponent} from './users/users.component';
import {SharedModule} from '../../shared/shared.module';
import {PrivateComponent} from './private.component';
import {PrivateRoutingModule} from './private-routing.module';

@NgModule({
  imports: [
    PrivateRoutingModule,

    SharedModule
  ],
  declarations: [
    PrivateComponent,

    UsersComponent
  ],
})
export class PrivateModule {
}
