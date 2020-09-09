import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Subject} from 'rxjs';
import {UserManagementFacade} from '../../../core/facade';
import {UserInfo} from '../../../common/interface';
import {FormBuilder, FormControl, FormGroup} from '@angular/forms';
import {takeUntil} from 'rxjs/operators';


@Component({
  selector: 'app-users',
  styleUrls: ['users.component.scss'],
  templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>();
  private readonly createUserFormControls = {email: 'email', firstName: 'firstName', lastName: 'lastName'};
  readonly usersDataToDisplay = [
    {header: 'email', field: 'email'},
    {header: 'firstName', field: 'firstName'},
    {header: 'lastName', field: 'lastName'}
  ];

  users: UserInfo[] = null;
  createUserForm: FormGroup;
  showCreateUserForm = false;
  createUserFormSubmitted = false;

  constructor(private formBuilder: FormBuilder,
              private userManagementFacade: UserManagementFacade) {
  }

  ngOnInit(): void {
    this.userManagementFacade.getAllUsers$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(usersInfo => {
        this.users = usersInfo;
      });

    this.createUserForm = this.formBuilder.group(
      {
        email: [''],
        firstName: [''],
        lastName: ['']
      });

  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  emailFormControl(): FormControl {
    return this.createUserForm.get(this.createUserFormControls.email) as FormControl;
  }

  firstNameFormControl(): FormControl {
    return this.createUserForm.get(this.createUserFormControls.firstName) as FormControl;
  }

  lastNameFormControl(): FormControl {
    return this.createUserForm.get(this.createUserFormControls.lastName) as FormControl;
  }

  createUser($event: any): void {
    this.createUserFormSubmitted = true;

    const userInfo: UserInfo = {
      id: null,
      email: this.emailFormControl().value,
      firstName: this.firstNameFormControl().value,
      lastName: this.lastNameFormControl().value
    };

    this.userManagementFacade.addUser(userInfo).subscribe(response => {
      this.setShowCreateUserForm(false);
      this.createUserFormSubmitted = false;
    });
  }

  setShowCreateUserForm(showCreateUserForm: boolean): void {
    this.showCreateUserForm = showCreateUserForm;
  }
}
