export interface AppConfig {
}

export interface User {
  username: string;
  password: string;
}

export interface JwtToken {
  token: string;
}

export interface ApiError {
  status: string;
  timestamp: string;
  errorCodes: {[code: string]: string}[];
}

export interface Theme {
  name: string;
  translatedName: string;
}
