import {NbAuthEmptyTokenError, NbAuthIllegalJWTTokenError, NbAuthSimpleToken, NbAuthTokenNotFoundError} from '@nebular/auth';

export class NbJwtToken extends NbAuthSimpleToken {

  constructor(protected readonly token: string,
              protected readonly ownerStrategyName: string,
              protected createdAt?: Date) {
    super(token, ownerStrategyName, createdAt);
    this.createdAt = this.prepareCreatedAt(createdAt);
  }

  /**
   * for JWT token, the iat (issued at) field of the token payload contains the creation Date
   */
  protected prepareCreatedAt(date: Date): Date {
    const decoded = this.getPayload();
    return decoded && decoded.iat ? new Date(Number(decoded.iat) * 1000) : super.prepareCreatedAt(date);
  }

  /**
   * Returns payload object
   * @returns any
   */
  protected parsePayload(): any {
    if (!this.token) {
      throw new NbAuthTokenNotFoundError('Token not found. ');
    }
    this.payload = this.decodeJwtPayload(this.token);
  }

  /**
   * Is data expired
   * @returns true or false based on token validity
   */
  isValid(): boolean {
    return super.isValid() && (!this.getTokenExpDate() || new Date() < this.getTokenExpDate());
  }

  private decodeJwtPayload(encoded: string): any {

    if (encoded.length === 0) {
      throw new NbAuthEmptyTokenError('Cannot extract from an empty payload.');
    }

    let decoded: string;
    try {
      decoded = this.parseJwt(encoded);
    } catch (e) {
      throw new NbAuthIllegalJWTTokenError(
        `The payload ${encoded} is not valid JWT payload and cannot be parsed.`);
    }

    if (!decoded) {
      throw new NbAuthIllegalJWTTokenError(
        `The payload ${encoded} is not valid JWT payload and cannot be decoded.`);
    }
    return JSON.parse(decoded);
  }

  /**
   * Returns expiration date
   * @returns Date
   */
  private getTokenExpDate(): Date {
    const decoded = this.getPayload();
    if (decoded && !decoded.hasOwnProperty('exp')) {
      return null;
    }
    const date = new Date(0);
    date.setUTCSeconds(decoded.exp); // 'cause jwt token are set in seconds
    return date;
  }

  getToken(): string {
    return this.token as string;
  }

  // https://stackoverflow.com/a/38552302/7054574
  private parseJwt(encoded: string): string {
    const base64Url = encoded.split('.')[1];
    const base64 = base64Url.replace(/-/g, '+').replace(/_/g, '/');
    return decodeURIComponent(atob(base64).split('').map((c) => {
      return '%' + ('00' + c.charCodeAt(0).toString(16)).slice(-2);
    }).join(''));
  }
}
