import {Resource} from "./resource";
import {Link} from "./link";
import {Profile} from "../profile";

export class Collection<T extends Resource> {
  _embedded: { [resourceName: string]: T[] };
  _links: { self: Link, profile: Link, search: Link };
  page: {
    size: number,
    totalElements: number,
    totalPages: number,
    number: number
  };

  getModels(resourceName: string): T[] {
    return this._embedded[resourceName].map(resource => resource as T);
  }
}
