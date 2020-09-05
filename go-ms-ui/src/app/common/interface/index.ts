export interface UserCredentials {
  email: string;
  password: string;
  passwordEncrypted?: boolean;
}

export interface JwtToken {
  token: string;
}

export interface ApiError {
  status: string;
  timestamp: string;
  apiSubErrors: { [code: string]: string }[];
}

export interface Profile {
  id: number;
  name: string;
  privileges: string[];
}

export interface UserDetails {
  email: string;
  firstName: string;
  lastName: string;
  profiles: Profile[];
}

export interface UserInfo {
  id: number;
  email: string;
  firstName: string;
  lastName: string;
}
