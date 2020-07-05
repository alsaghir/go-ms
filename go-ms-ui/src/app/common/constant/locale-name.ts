export class LocaleName {

  static instance = new LocaleName();

  readonly NOTIFICATION_TITLE = 'NOTIFICATION_TITLE';

  readonly THEME_DEFAULT = 'THEME_DEFAULT';
  readonly THEME_COSMIC = 'THEME_COSMIC';
  readonly THEME_CORPORATE = 'THEME_CORPORATE';
  readonly THEME_DARK = 'THEME_DARK';


  private constructor() {
  }

  static getInstance(): LocaleName {
    return this.instance;
  }
}
