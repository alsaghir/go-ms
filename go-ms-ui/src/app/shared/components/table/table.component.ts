import {Component, EventEmitter, Input, OnDestroy, OnInit, Output} from '@angular/core';
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

  @Output() onEditRowClick = new EventEmitter<any>();
  @Output() onDeleteRowClick = new EventEmitter<any>();

  public constructor() {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }

  editRowData(dataObject) {
    this.onEditRowClick.emit(dataObject);
  }

  deleteRowData(dataObject) {
    this.onDeleteRowClick.emit(dataObject);
  }
}
