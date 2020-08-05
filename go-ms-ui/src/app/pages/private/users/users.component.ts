import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {UserManagementFacade} from '../../../core/facade';
import {UserInfo} from '../../../common/interface';


@Component({
  selector: 'app-users',
  styleUrls: ['users.component.scss'],
  templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>();

  isShowAddUserForm = false;

  users: UserInfo[] = null;

  usersDataToDisplay = [
    {header: 'email', field: 'email'},
    {header: 'firstName', field: 'firstName'},
    {header: 'lastName', field: 'lastName'}
  ];

  constructor(private userManagementFacade: UserManagementFacade) {
  }

  ngOnInit(): void {
    this.userManagementFacade.getUsers$().subscribe(usersInfo => this.users = usersInfo);
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  showAddUserForm(): void {
    this.isShowAddUserForm = true;
  }
}
