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

export interface DispatchReduxFailRes {
  error?: {
    message: string;
    name: string;
    stack: string;
  };
  meta: {
    aborted: boolean;
    arg: {
      email: string;
      password: string;
    };
    condition: boolean;
    rejectedWithValue: boolean;
    requestId: string;
    requestStatus: string;
  };
  payload: {
    code: string;
    errors: {
      field: string;
      reason: string;
      value: string;
    }[];
    message: string;
    statsu: number;
    type: string;
  };
  type: string;
}

export interface ResSendMessage {
  data: {
    content: string;
    createdDate: Date;
    delflg: string;
    id: number;
    lastUpdateDate: Date;
    member: {
      email: string;
      grade: number;
      id: number;
      nickname: string;
    };
    sender: {
      email: string;
      grade: number;
      id: number;
      nickname: string;
    };
    status: string;
  };
  message: string;
  status: number;
}
