
const HOST_URL: string = "http://localhost:8400";
const API_URL_CONTEXT_V1: string = "/api/v1";
const USERNAME_AUTH_HEADER: string = "UsernameAuth";

export const environment = {
  staging: false,
  preprod: false,
  production: false,
  API_URL: HOST_URL + API_URL_CONTEXT_V1,
};

