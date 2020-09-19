export * from './locale-name';

export const THEMES: {[key: string]: {name: string, value: string}} = {
  DEFAULT: {name: 'Default', value: 'default'},
  COSMIC: {name: 'Cosmic', value: 'cosmic'},
  CORPORATE: {name: 'Corporate', value: 'corporate'},
  DARK: {name: 'Dark', value: 'dark'},
  AQUAMARINE_COLORED: {name: 'Aquamarine', value: 'aquamarine-colored'},
  MATERIAL_LIGHT: {name: 'Material Light', value: 'material-light'},
  MATERIAL_DARK: {name: 'material Dark', value: 'material-dark'}
};


export enum LayoutDirection {
  LTR = 'ltr',
  RTL = 'rtl'
}
