import { Token, USER_TYPE } from './userType';

// Res: response

export interface ResRegister {
  data: {
    id: number;
    nickname: string;
    email: string;
    grade: number;
  };
  message: string;
  status: number;
}

export interface ResLogin {
  data: Token;
  message: string;
  status: number;
}

export interface PayloadSuccessType<T> {
  data: T;
}

export interface PayloadFailType {
  type: USER_TYPE;
  code: string;
  errors: {
    field: string;
    value: string;
    reason: string;
  }[];
  message: string;
  status: number;
}
