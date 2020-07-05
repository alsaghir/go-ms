import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import {
  NbActionsModule,
  NbAlertModule, NbButtonModule, NbCardModule, NbCheckboxModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbSearchModule,
  NbSelectModule,
  NbUserModule
} from '@nebular/theme';
import {NbEvaIconsModule} from '@nebular/eva-icons';
import {NbAuthModule} from '@nebular/auth';

const COMPONENTS = [];
const PIPES = [];

const NB_MODULES = [
  NbAuthModule,
  NbEvaIconsModule,
  NbLayoutModule,
  NbIconModule,
  NbSelectModule,
  NbActionsModule,
  NbSearchModule,
  NbUserModule,
  NbAlertModule,
  NbInputModule,
  NbCheckboxModule,
  NbButtonModule,
  NbCardModule,
];

const MODULES = [CommonModule, FormsModule, ReactiveFormsModule, NB_MODULES];

@NgModule({
  imports: [...MODULES],
  exports: [...MODULES, ...PIPES, ...COMPONENTS],
  declarations: [...COMPONENTS, ...PIPES],
})
export class SharedModule {
  static forRoot(): ModuleWithProviders<SharedModule> {
    return {
      ngModule: SharedModule,
      providers: [],
    };
  }
}
