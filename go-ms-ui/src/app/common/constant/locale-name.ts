import {Helper} from '../implementation';

export class LocaleName {

  private static reference = new LocaleName();

  readonly ERROR = 'ERROR';
  readonly LOGIN_SUCCESS = 'LOGIN_SUCCESS';
  readonly LOGOUT = 'LOGOUT';

  private constructor() {
  }

  static get instance(): LocaleName {
    if (LocaleName.reference == null) {
      LocaleName.reference = new LocaleName();
      Helper.instance.validateConstantProperties(LocaleName.reference);
    }
    return LocaleName.reference;
  }
}
