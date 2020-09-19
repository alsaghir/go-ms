import {Resource} from "./hal/resource";
import {BackendUrls} from "../../config";

export class Profile extends Resource {
  id: number;
  name: string;

  getApiResource(): string {
    return BackendUrls.API_RESOURCE_PROFILES;
  }
}
