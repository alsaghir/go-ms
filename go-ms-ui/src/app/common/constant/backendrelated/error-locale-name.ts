import {Helper} from '../../implementation';

export class ErrorLocaleName {

  private static reference = null;

  readonly UNEXPECTED_ERROR = 'UNEXPECTED_ERROR';
  readonly EXPECTED_ERROR = 'EXPECTED_ERROR';
  readonly BAD_CREDENTIALS = 'BAD_CREDENTIALS';
  readonly NOT_ALLOWED = 'NOT_ALLOWED';


  private constructor() {
  }

  static get instance(): ErrorLocaleName {
    if (ErrorLocaleName.reference == null) {
      ErrorLocaleName.reference = new ErrorLocaleName();
      Helper.instance.validateConstantProperties(ErrorLocaleName.reference);
    }
    return ErrorLocaleName.reference;
  }
}
