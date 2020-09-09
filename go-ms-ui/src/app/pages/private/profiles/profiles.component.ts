import {Component, OnDestroy, OnInit} from "@angular/core";
import {Subject} from "rxjs";
import {FormBuilder} from "@angular/forms";
import {UserManagementFacade} from "../../../core/facade";
import {Profile} from "../../../common/interface";
import {takeUntil} from "rxjs/operators";
import {NbUtil} from "../../../core/util";
import {LayoutDirection} from "../../../common/constant";

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
  profiles: Profile[];

  constructor(private formBuilder: FormBuilder,
              private userManagementFacade: UserManagementFacade,
              private nbUtil: NbUtil) {
  }

  ngOnInit(): void {
    this.layoutDirection = this.nbUtil.getDirection();

    this.userManagementFacade.getAllProfiles$()
      .pipe(takeUntil(this.destroy$))
      .subscribe(profiles => {
        this.profiles = profiles;
      });
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }




}
