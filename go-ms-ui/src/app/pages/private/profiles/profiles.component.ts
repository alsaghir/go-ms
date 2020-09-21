import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subject} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {UserManagementFacade} from "../../../core/facade";
import {CommonUtil, NbUtil} from "../../../core/util";
import {LayoutDirection} from "../../../common/constant";
import {takeUntil} from "rxjs/operators";
import {BackendUrls} from "../../../common/config";
import {Collection, Profile} from "../../../common/model";
import {Privilege} from "../../../common/model/resource/privilege";

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
  privilegesModel: { privilegeObject: Privilege, assigned?: boolean }[] = [];
  selectedProfileForEdit: Profile;

  profilesResponse: Collection<Profile>;
  privilegesResponse: Collection<Privilege>;

  constructor(private formBuilder: FormBuilder,
              private userManagementFacade: UserManagementFacade,
              private commonUtil: CommonUtil,
              private nbUtil: NbUtil) {
  }

  ngOnInit(): void {
    this.layoutDirection = this.nbUtil.getDirection();

    this.userManagementFacade.getProfiles$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(profilesResponse => {
        this.profilesResponse = profilesResponse;
        this.profilesResponse._embedded[BackendUrls.API_RESOURCE_PROFILES]
          .forEach(profileResponse => this.profilesModel.push(Object.assign(new Profile(), profileResponse)))
      });

    this.userManagementFacade.getPrivileges$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(privilegesResponse => {
        console.log(privilegesResponse);
        this.privilegesResponse = privilegesResponse;
        this.privilegesResponse._embedded[BackendUrls.API_RESOURCE_PRIVILEGES]
          .forEach(privilegeResponse =>
            this.privilegesModel.push({privilegeObject: Object.assign(new Privilege(), privilegeResponse)}))
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  editProfile(profile: Profile) {
    this.userManagementFacade.getPrivilegesOf$(profile)
      .pipe(takeUntil(this.destroy$))
      .subscribe(
        assignedPrivileges => {
          const assignedPrivilegesModel =
            assignedPrivileges._embedded[BackendUrls.API_RESOURCE_PRIVILEGES];
          this.privilegesModel.filter(
            privilegeModel => assignedPrivilegesModel.some(
              assignedPrivilege =>
                assignedPrivilege.privilege === privilegeModel.privilegeObject.privilege
            )
          ).forEach(privilegeModel => privilegeModel.assigned = true);
          this.selectedProfileForEdit = profile;
        }
      );

  }
}
