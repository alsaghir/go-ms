import {Component, Input, OnDestroy, OnInit} from '@angular/core';
import {Subject} from 'rxjs';


@Component({
  selector: 'app-table',
  styleUrls: ['./table.component.scss'],
  templateUrl: './table.component.html',
})
export class TableComponent implements OnInit, OnDestroy {

  private destroy$: Subject<void> = new Subject<void>();

  @Input() dataObjects: any[];
  @Input() columnsData: {header: string, field: string}[];

  public constructor() {
  }

  ngOnInit(): void {
  }

  ngOnDestroy(): void {
    this.destroy$.next();
    this.destroy$.complete();
  }
}
