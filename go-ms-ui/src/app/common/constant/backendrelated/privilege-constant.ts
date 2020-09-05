import {Helper} from '../../implementation';

export class PrivilegeConstant {

  private static reference = null;

  readonly OP_USERS_MANAGEMENT = 'OP_USERS_MANAGEMENT';

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
