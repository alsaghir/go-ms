import {RouterModule, Routes} from '@angular/router';
import {NgModule} from '@angular/core';
import {UsersComponent} from './users/users.component';
import {PrivateComponent} from './private.component';
import {ProfilesComponent} from "./profiles/profiles.component";

const routes: Routes = [
  {
    path: '',
    component: PrivateComponent,
    children: [
      {
        path: 'users',
        component: UsersComponent
      },
      {
        path: 'profiles',
        component: ProfilesComponent
      },
      {
        path: '',
        redirectTo: 'users',
        pathMatch: 'full'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule],
})
export class PrivateRoutingModule {
}
