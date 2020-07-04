import {AfterViewInit, Component, ElementRef, OnInit, QueryList, ViewChildren} from '@angular/core';

import {LocaleWord} from '../../common/constant/locale-word';
import {LocaleHandlingUtil} from '../../core/util';
import {EventFacade} from '../../core/facade/event-facade.service';

@Component({
  selector: 'app-other-translate',
  templateUrl: 'other-translate.component.html',
})
export class OtherTranslateComponent implements OnInit, AfterViewInit {

  @ViewChildren('inputs') inputElements: QueryList<ElementRef<HTMLInputElement>>;

  localeWord: LocaleWord = LocaleWord.getInstance();

  constructor(private localeHandlingUtil: LocaleHandlingUtil,
              private eventFacade: EventFacade) {
  }

  ngOnInit(): void {
  }

  ngAfterViewInit(): void {
    this.localeHandlingUtil.parentElementBundle(this.inputElements);
    this.eventFacade.localeViewRendered(true);
  }

}
