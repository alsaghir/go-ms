import {ModuleWithProviders, NgModule} from '@angular/core';
import {CommonModule} from '@angular/common';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {MatRippleModule} from '@angular/material/core';

import {
  NbActionsModule,
  NbAlertModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbContextMenuModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule, NbListModule,
  NbMenuModule,
  NbSearchModule,
  NbSelectModule,
  NbSidebarModule, NbSpinnerModule,
  NbThemeModule,
  NbUserModule
} from '@nebular/theme';

import {NbEvaIconsModule} from '@nebular/eva-icons';
import {NbAuthModule} from '@nebular/auth';

import {TableModule} from 'primeng/table';

import {HeaderComponent} from './components';
import {FooterComponent} from './components/footer/footer.component';
import {TableComponent} from './components/table/table.component';

const COMPONENTS = [
  HeaderComponent,
  FooterComponent,
  TableComponent
];

const PIPES = [];

const NB_MODULES = [
  NbActionsModule,
  NbAlertModule,
  NbAuthModule,
  NbButtonModule,
  NbCardModule,
  NbCheckboxModule,
  NbContextMenuModule,
  NbEvaIconsModule,
  NbIconModule,
  NbInputModule,
  NbLayoutModule,
  NbListModule,
  NbMenuModule,
  NbSearchModule,
  NbSelectModule,
  NbSidebarModule,
  NbThemeModule,
  NbUserModule,
  NbSpinnerModule
];

const MODULES = [
  CommonModule,
  FormsModule,
  ReactiveFormsModule,

  MatRippleModule,
  TableModule,

  NB_MODULES];

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
