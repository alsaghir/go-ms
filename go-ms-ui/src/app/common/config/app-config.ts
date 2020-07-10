interface EnvironmentProfile {
  API_PROTOCOL: string;
  API_HOST: string;
  API_PORT: string;
  MOCK: boolean;
  API_FULL_HOST_URL: () => string;
}

const DEV: EnvironmentProfile = {
  API_PROTOCOL: 'http://',
  API_HOST: 'localhost',
  API_PORT: '8080',
  MOCK: false,
  API_FULL_HOST_URL: () => DEV.API_PROTOCOL + DEV.API_HOST + ':' + DEV.API_PORT
};

export const API_BACKEND_HOST_ACTIVE = DEV;

export const PASSWORD_ENCRYPTION_ENABLED = true;
