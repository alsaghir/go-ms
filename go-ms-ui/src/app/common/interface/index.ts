export interface User {
  email: string;
  password: string;
}

export interface JwtToken {
  token: string;
}

export interface ApiError {
  status: string;
  timestamp: string;
  errorCodes: { [code: string]: string }[];
}
