import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs';
import {LayoutDirection} from "../../../common/constant";

@Component({
  selector: 'app-table',
  styleUrls: ['./table.component.scss'],
  templateUrl: './table.component.html',
})
export class TableComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();

  readonly layoutDirections = LayoutDirection;

  @Input() dataObjects: any[];
  @Input() columnsData: {header: string, field: string}[];
  @Input() direction: LayoutDirection = LayoutDirection.LTR;

  public constructor() {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  /*editRowData(dataObject: Profile) {
    console.log(dataObject);
  }

  deleteRowData(dataObject: Profile) {
    console.log(dataObject);
  }*/
}
