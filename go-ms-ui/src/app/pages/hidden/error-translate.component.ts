import {AfterViewInit, Component, ElementRef, OnInit, ViewChild} from '@angular/core';
import {ErrorHandlingUtil} from '../../core/util/error-handling-util.service';
import {ErrorName} from '../../common/constant';

@Component({
  selector: 'app-error-translate',
  templateUrl: 'error-translate.component.html',
})
export class ErrorTranslateComponent implements OnInit, AfterViewInit {

  @ViewChild('errorsParentElement') errorsParentElement: ElementRef<HTMLDivElement>;

  errorName: ErrorName = ErrorName.getInstance();

  constructor(private errorHandlingUtil: ErrorHandlingUtil) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.errorHandlingUtil.parentElementBundle(this.errorsParentElement);
  }
}
