import { Injectable } from '@angular/core';
import {Observable} from 'rxjs';
import { NbAuthService } from '@nebular/auth';
import { NbRoleProvider } from '@nebular/security';

@Injectable({providedIn: 'root'})
export class RoleProviderFacade implements NbRoleProvider {

  constructor(private authService: NbAuthService) {
  }

  getRole(): Observable<string> {
    return null;
  }
}
