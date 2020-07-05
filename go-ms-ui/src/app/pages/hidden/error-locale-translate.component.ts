import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';
import {ErrorLocaleHandlingUtil} from '../../core/util';
import {ErrorLocaleName} from '../../common/constant';

@Component({
  selector: 'app-error-locale-translate',
  templateUrl: 'error-locale-translate.component.html',
})
export class ErrorTranslateComponent implements OnInit, AfterViewInit {

  @ViewChildren('inputs') errorInputElements: QueryList<ElementRef<HTMLInputElement>>;

  errorLocaleName: ErrorLocaleName = ErrorLocaleName.getInstance();

  constructor(private errorLocaleHandlingUtil: ErrorLocaleHandlingUtil) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.errorLocaleHandlingUtil.setParentElementBundle(this.errorInputElements);
  }
}
