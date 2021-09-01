export enum USER_TYPE {
  USER_REGISTER = 'USER_REGISTER',
  USER_LOGIN = 'USER_LOGIN',
  USER_LOGOUT = 'USER_LOGOUT',
  USER_REISSUE = 'USER_REISSUE',
  USER_SUCCESS = 'USER_SUCCESS',
  USER_FAIL = 'USER_FAIL',
}

export interface PayloadSuccessType {
  data: SuccessPayload;
  message: string;
  status: number;
}

interface SuccessPayload {
  email: string;
  grade: string;
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresIn: number;
}

export interface PayloadFailType {
  type: string;
  code: string;
  errors: Error[];
  message: string;
  status: number;
}

interface Error {
  field: string;
  value: string;
  reason: string;
}

export interface UserSuccessDispatch {
  type: USER_TYPE;
  payload: PayloadSuccessType;
}

export interface UserFailDispatch {
  type: USER_TYPE.USER_FAIL;
  payload: PayloadFailType;
}

export type UserDispatchType = UserSuccessDispatch | UserFailDispatch;
