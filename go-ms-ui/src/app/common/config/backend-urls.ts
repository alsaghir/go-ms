import {API_BACKEND_HOST_ACTIVE} from './app-config';

export class BackendUrls {

  // Metadata
  static readonly API_PREFIX = 'api';

  // RPC
  static readonly API_JWT = 'jwt';

  // Resources
  static readonly API_RESOURCE_PRIVILEGES = 'privileges';
  static readonly API_RESOURCE_PROFILES = 'profiles';
  static readonly API_RESOURCE_USERS = 'users';

  /** Returns full end point url
   *
   * @param path api path to be concatenated
   */
  static API_ENDPOINT(path: string): string {
    return API_BACKEND_HOST_ACTIVE.API_FULL_HOST_URL() + '/' + BackendUrls.API_PREFIX + '/' + path;
  }

}
