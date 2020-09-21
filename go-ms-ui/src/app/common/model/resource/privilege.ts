import {Resource} from "./hal/resource";
import {BackendUrls} from "../../config";

export class Privilege extends Resource {
  privilege: string;
  description: string;

  getApiResource(): string {
    return BackendUrls.API_RESOURCE_PRIVILEGES
  }
}
