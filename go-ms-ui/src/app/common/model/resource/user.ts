import {Resource} from "./hal/resource";
import {BackendUrls} from "../../config";

export class User extends Resource {
  email: string;
  active: boolean;
  firstName: string;
  lastName: string;

  getApiResource(): string{
    return BackendUrls.API_RESOURCE_USERS
  }
}
