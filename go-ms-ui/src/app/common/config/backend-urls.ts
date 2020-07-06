import {API_BACKEND_HOST_ACTIVE} from './app-config';

export class BackendUrls {

  // End-points paths of API services
  static readonly API_PREFIX = '/api';
  static readonly API_LOGIN = '/login';

  /** Returns full end point url
   *
   * @param path api path to be concatenated
   */
  static API_ENDPOINT(path: string): string {
    return API_BACKEND_HOST_ACTIVE.API_FULL_HOST_URL() + BackendUrls.API_PREFIX + path;
  }

}
