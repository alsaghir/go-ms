import {Link} from "./link";

export abstract class Resource {
  _links: {self: Link, profiles: Link, [resourceName: string]: Link};

  abstract getApiResource(): string;
}
