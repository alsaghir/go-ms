import {Component, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs';


@Component({
  selector: 'app-users',
  styleUrls: ['users.component.scss'],
  templateUrl: 'users.component.html'
})
export class UsersComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>();

  isShowAddUserForm = false;

  users: { email: string, name: string }[] = [
    {email: 'Carla.Espinosa@exmaple.com', name: 'Carla Espinosa'},
    {email: 'Bob.Kelso@exmaple.com', name: 'Bob Kelso'},
    {email: 'Janitor@exmaple.com', name: 'Janitor Janitor'},
    {email: 'Perry.Cox@exmaple.com', name: 'Perry Cox'},
    {email: 'Ben.Sullivan@exmaple.com', name: 'Carpenter and photographer'},
    {email: 'Carla.Espinosa@exmaple.com', name: 'Nurse'},
    {email: 'Bob.Kelso@exmaple.com', name: 'Doctor of Medicine'},
    {email: 'Perry.Cox@exmaple.com', name: 'Doctor of Medicine'},
    {email: 'Carla.Espinosa@exmaple.com', name: 'Nurse'},
    {email: 'Bob.Kelso@exmaple.com', name: 'Doctor of Medicine'},
    {email: 'Janitor@exmaple.com', name: 'Janitor'}
  ];

  usersData = [{header: 'email', field: 'email'}, {header: 'name', field: 'name'}];

  constructor() {

  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  showAddUserForm(): void {
    this.isShowAddUserForm = true;
  }
}
