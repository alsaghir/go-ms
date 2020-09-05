import {ChangeDetectorRef, Component, OnDestroy, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {FormBuilder, FormControl, FormGroup, Validators} from '@angular/forms';
import {Subject} from 'rxjs';
import {takeUntil} from 'rxjs/operators';

import {NbAuthResult} from '@nebular/auth';
import {EventFacade, UserManagementFacade} from '../../core/facade';
import {ErrorLocaleHandlingUtil, LocaleHandlingUtil, LoggerUtil, NbUtil} from '../../core/util';
import {LocaleName} from '../../common/constant';
import {UserCredentials} from '../../common/interface';
import {NbAclRole, NbAclService} from "@nebular/security";
import {REDIRECT_AFTER_LOGIN_PATH} from "../../common/config";

@Component({
  selector: 'app-login',
  styleUrls: ['login.component.scss'],
  templateUrl: 'login.component.html',
})
export class LoginComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>();
  private readonly loginFormControls = {email: 'email', password: 'password'};

  submitted: boolean;
  rememberMe: boolean; // TODO
  loginForm: FormGroup;
  errors: string[];

  constructor(private formBuilder: FormBuilder,
              private router: Router,
              private changeDetectorRef: ChangeDetectorRef,
              // Facades to retrieve data state
              private userManagementFacade: UserManagementFacade,
              private eventFacade: EventFacade,
              // Utils
              private errorHandlingUtil: ErrorLocaleHandlingUtil,
              private localeHandlingUtil: LocaleHandlingUtil,
              private logger: LoggerUtil,
              private nbUtil: NbUtil) {

  }

  ngOnInit(): void {
    this.submitted = false;
    this.rememberMe = false;
    this.errors = [];
    this.loginForm = this.formBuilder.group(
      {
        email: ['', Validators.email]
        , password: ['', [Validators.minLength(3), Validators.maxLength(12)]]
      }
    );

    this.eventFacade.localeViewRendered$().pipe(takeUntil(this.destroy$))
      .subscribe((localeViewRendered: boolean) => {
        if (localeViewRendered) {
          this.changeDetectorRef.detectChanges();
        }
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  emailFormControl(): FormControl {
    return this.loginForm.get(this.loginFormControls.email) as FormControl;
  }

  passwordFormControl(): FormControl {
    return this.loginForm.get(this.loginFormControls.password) as FormControl;
  }

  login(): void {
    const userCredentials: UserCredentials = {email: this.emailFormControl().value, password: this.passwordFormControl().value};
    this.userManagementFacade.authenticate('jwt', userCredentials)
      .subscribe(
        (authResult: NbAuthResult) => {
          this.errors = [];
          if (authResult.isSuccess()) {
            this.router.navigate([REDIRECT_AFTER_LOGIN_PATH])
              .then(isSuccessNavigation =>
                this.logger.debug(isSuccessNavigation ? 'Successful Navigation' : 'Failed Navigation'));
            this.nbUtil.showToast(LocaleName.instance.LOGIN_SUCCESS, 'Success', 'bottom-right', 'success');
          } else {
            this.errors.push(...this.errorHandlingUtil.translateByCodesAndNames(authResult.getErrors()));
          }
          this.errors.forEach(error =>
            this.nbUtil.dangerToast(error, this.localeHandlingUtil.translationOf(LocaleName.instance.ERROR), 'bottom-right'));
        });
  }

}
