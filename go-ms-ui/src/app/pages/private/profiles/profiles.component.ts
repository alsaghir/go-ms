import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subject} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {UserManagementFacade} from "../../../core/facade";
import {CommonUtil, NbUtil} from "../../../core/util";
import {LayoutDirection, LocaleName} from "../../../common/constant";
import {takeUntil} from "rxjs/operators";
import {BackendUrls} from "../../../common/config";
import {Collection, Link, Profile} from "../../../common/model";
import {Privilege} from "../../../common/model/resource/privilege";
import {NbAccessChecker, NbAclService} from "@nebular/security";

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
  privilegesUpdating: boolean;

  constructor(private formBuilder: FormBuilder,
              private userManagementFacade: UserManagementFacade,
              private commonUtil: CommonUtil,
              private nbAccessChecker: NbAccessChecker,
              private nbAclService: NbAclService,
              private nbUtil: NbUtil) {
  }

  ngOnInit(): void {

    this.nbAccessChecker.isGranted('OP_USERS_MANAGEMENT', 'any').subscribe(value => console.log(value));

    console.log(this.nbAclService);

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

  updateAssignedPrivileges(privilegeModel: { privilegeObject: Privilege; assigned?: boolean }, checked: boolean) {
    this.privilegesUpdating = true;

    this.privilegesModel
      .find(currentPrivilegeModel =>
        currentPrivilegeModel.privilegeObject.privilege === privilegeModel.privilegeObject.privilege)
      .assigned = checked;

    const linksOfAssignedPrivileges: Link[] = this.privilegesModel
      .filter(privilegeModel => privilegeModel.assigned != null && privilegeModel.assigned == true)
      .map(privilegeModel => privilegeModel.privilegeObject._links.self);

    this.userManagementFacade.updateAssignedPrivilegesOf(this.selectedProfileForEdit, linksOfAssignedPrivileges)
      .subscribe(() => {
        this.nbUtil.showToast('Saved Successfully', 'Success', 'bottom-right', 'success');
        setTimeout(()=>{ this.privilegesUpdating = false; }, 2000);
      });
  }
}
