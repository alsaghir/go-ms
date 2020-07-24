import {Helper} from '../../implementation';

export class PrivilegeConstant {

  private static reference = null;

  readonly OP_CREATE_USER = 'OP_CREATE_USER';
  readonly OP_USERS_DELETE = 'OP_USERS_DELETE';

  private constructor() {

  }

  static get instance(): PrivilegeConstant {
    if (PrivilegeConstant.reference == null) {
      PrivilegeConstant.reference = new PrivilegeConstant();
      Helper.instance.validateConstantProperties(PrivilegeConstant.reference);
    }
    return PrivilegeConstant.reference;
  }
}
