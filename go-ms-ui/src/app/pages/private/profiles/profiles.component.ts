import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subject} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {UserManagementFacade} from "../../../core/facade";
import {NbUtil} from "../../../core/util";
import {LayoutDirection} from "../../../common/constant";
import {takeUntil} from "rxjs/operators";
import {BackendUrls} from "../../../common/config";
import {Collection, Profile} from "../../../common/model";

@Component({
  selector: 'app-profiles',
  styleUrls: ['profiles.component.scss'],
  templateUrl: 'profiles.component.html'
})
export class ProfilesComponent implements OnInit, OnDestroy {

  private destroy$ = new Subject<void>();


  readonly profilesDataView = [
    {header: 'name', field: 'name'}
  ];

  layoutDirection: LayoutDirection;
  profilesModel: Profile[] = [];

  profilesResponse: Collection<Profile>;

  constructor(private formBuilder: FormBuilder,
              private userManagementFacade: UserManagementFacade,
              private nbUtil: NbUtil) {
  }

  ngOnInit(): void {
    this.layoutDirection = this.nbUtil.getDirection();

    this.userManagementFacade.getProfiles$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(profilesResponse => {
        this.profilesResponse = profilesResponse;
        this.profilesResponse._embedded[BackendUrls.API_RESOURCE_PROFILES]
          .forEach(profileResponse => this.profilesModel.push(profileResponse as Profile))
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
