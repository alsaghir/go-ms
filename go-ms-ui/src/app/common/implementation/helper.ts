export class Helper {

  private static reference = null;

  private constructor() {
  }

  static get instance(): Helper {
    if (!Helper.reference) {
      Helper.reference = new Helper();
    }
    return Helper.reference;
  }

  validateConstantProperties(object: any): void {
    const keys = Object.keys(object);
    for (const key of keys) {
      if (object.hasOwnProperty(key) && object[key] !== key) {
        throw new TypeError(`key ${key} has unexpected value ${object[key]}`);
      }
    }
  }
}
