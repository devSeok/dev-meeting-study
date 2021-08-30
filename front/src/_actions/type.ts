export enum USER_TYPE {
  USER_REGISTER = 'USER_REGISTER',
  USER_LOGIN = 'USER_LOGIN',
  USER_LOGOUT = 'USER_LOGOUT',
  USER_REISSUE = 'USER_REISSUE',
  USER_SUCCESS = 'USER_SUCCESS',
  USER_FAIL = 'USER_FAIL',
}

export interface PayloadType {
  data: SuccessPayload;
  message: string;
  status: number;
}

export interface SuccessPayload {
  email: string;
  grade: string;
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresIn: number;
}

export interface UserSuccessDispatch {
  type: USER_TYPE;
  payload: PayloadType;
}

export interface UserFailDispatch {
  type: USER_TYPE.USER_FAIL;
}

export type UserDispatchType = UserSuccessDispatch | UserFailDispatch;
