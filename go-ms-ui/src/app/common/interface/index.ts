export interface User {
  email: string;
  password: string;
  encryptedPassword?: boolean;
}

export interface JwtToken {
  token: string;
}

export interface ApiError {
  status: string;
  timestamp: string;
  apiSubErrors: { [code: string]: string }[];
}
