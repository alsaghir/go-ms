export class ErrorLocaleName {

  static instance = new ErrorLocaleName();

  readonly UNEXPECTED_ERROR = 'UNEXPECTED_ERROR';
  readonly EXPECTED_ERROR = 'EXPECTED_ERROR';


  private constructor() {
  }

  static getInstance(): ErrorLocaleName {
    return this.instance;
  }
}
