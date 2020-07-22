import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';

import {EventFacade} from '../../core/facade';
import {LocaleName} from '../../common/constant';
import {LocaleHandlingUtil} from '../../core/util';

@Component({
  selector: 'app-locale-translate',
  templateUrl: 'locale-translate.component.html',
})
export class LocaleTranslateComponent implements OnInit, AfterViewInit {

  @ViewChildren('inputs') inputElements: QueryList<ElementRef<HTMLInputElement>>;

  LocaleName: LocaleName = LocaleName.instance;

  constructor(private localeHandlingUtil: LocaleHandlingUtil,
              private eventFacade: EventFacade) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.localeHandlingUtil.setParentElementBundle(this.inputElements);
    this.eventFacade.setLocaleViewRendered(true);
  }

}
