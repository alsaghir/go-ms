export interface ApiError {
  status: string;
  timestamp: string;
  apiSubErrors: { [code: string]: string }[];
}
