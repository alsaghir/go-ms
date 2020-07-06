export class LocaleName {

  static instance = new LocaleName();

  readonly NOTIFICATION_TITLE = 'NOTIFICATION_TITLE';
  readonly LOGIN_SUCCESS = 'LOGIN_SUCCESS';



  private constructor() {
  }

  static getInstance(): LocaleName {
    return this.instance;
  }
}
