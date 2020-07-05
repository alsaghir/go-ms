export class BackendUrls {

  static readonly API_PROTOCOL = 'http://';
  static readonly API_HOST = 'localhost';
  static readonly API_PORT = '8080';

  // End-points paths of API services
  static readonly API_PREFIX = '/api';
  static readonly API_LOGIN = '/login';

  /**
   * Full host URL & Port
   *
   */
  static get API_URL(): string {
    return BackendUrls.API_PROTOCOL + BackendUrls.API_HOST  + ':' + BackendUrls.API_PORT;
  }

  /** Returns full end point url
   *
   * @param path api path to be concatenated
   */
  static API_ENDPOINT(path: string): string {
    return BackendUrls.API_URL + BackendUrls.API_PREFIX + path;
  }

}
