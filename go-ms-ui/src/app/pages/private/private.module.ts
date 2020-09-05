import {NgModule} from '@angular/core';

import {PrivateComponent} from './private.component';
import {PrivateRoutingModule} from './private-routing.module';

import {SharedModule} from '../../shared/shared.module';

import {ProfilesComponent} from "./profiles/profiles.component";
import {UsersComponent} from './users/users.component';

@NgModule({
  imports: [
    PrivateRoutingModule,

    SharedModule
  ],
  declarations: [
    PrivateComponent,

    ProfilesComponent,
    UsersComponent
  ],
})
export class PrivateModule {
}
