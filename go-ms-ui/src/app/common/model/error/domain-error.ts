export class DomainError extends Error {

  private _errorsCodes: string[];
  private _errorsNames: string[];

  constructor(errorsCodes?: string[], errorsNames?: string[], message?: string) {
    super(message);
    this._errorsCodes = errorsCodes || [];
    this._errorsNames = errorsNames || [];
  }

  addErrorCode(...errorCode: string[]): DomainError {
    this._errorsCodes.push(...errorCode);
    return this;

  }

  addErrorName(...errorName: string[]): DomainError {
    this._errorsNames.push(...errorName);
    return this;
  }


  getErrorsCodes(): string[] {
    return this._errorsCodes;
  }

  setErrorsCodes(value: string[]): DomainError {
    this._errorsCodes = value;
    return this;
  }

  getErrorsNames(): string[] {
    return this._errorsNames;
  }

  setErrorsNames(value: string[]): DomainError {
    this._errorsNames = value;
    return this;
  }
}
