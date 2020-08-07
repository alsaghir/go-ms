import {Component, OnDestroy, OnInit, TemplateRef, ViewChild} from '@angular/core';
import {Subject} from 'rxjs';
import {UserManagementFacade} from '../../../core/facade';
import {UserInfo} from '../../../common/interface';
import {NbUtil} from '../../../core/util';
import {FormBuilder, FormGroup} from '@angular/forms';


@Component({
  selector: 'app-users',
  styleUrls: ['users.component.scss'],
  templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>();

  isShowAddUserForm = false;

  users: UserInfo[] = null;

  createUserForm: FormGroup;
  showCreateUserForm = false;

  usersDataToDisplay = [
    {header: 'email', field: 'email'},
    {header: 'firstName', field: 'firstName'},
    {header: 'lastName', field: 'lastName'}
  ];

  constructor(private formBuilder: FormBuilder,
              private userManagementFacade: UserManagementFacade,
              private nbUtil: NbUtil) {
  }

  ngOnInit(): void {
    this.userManagementFacade.getUsers$().subscribe(usersInfo => this.users = usersInfo);

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

  showAddUserForm(): void {
    this.isShowAddUserForm = true;
  }

  createUser($event: any): void {
    console.log($event);
  }
}
