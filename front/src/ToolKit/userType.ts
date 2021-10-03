import { PayloadFailType } from './axiosType';

export interface RegisterType {
  nickname: string;
  email: string;
  password: string;
}

export interface LoginType {
  email: string;
  password: string;
}

export interface Token {
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresIn?: number;
  grantType?: string;
}

export interface TokenCheck {
  type: USER_TYPE.USER_CHECK_TOKEN | USER_TYPE.USER_REISSUE;
  success: boolean;
  message: string;
  err?: PayloadFailType;
}

export interface Addresses {
  address1: string;
  address2: string;
  address3: string;
}

export interface SubjectType {
  id: number;
  subjectName: string;
}

export interface StudyType {
  address: Addresses;
  endDate: string;
  file: object;
  maxMember: number;
  startDate: string;
  contents: string;
  studyInstanceType: string;
  onlineType: string;
  link: string;
  studyType: string;
  subjectId: number;
  title: string;
}

export enum USER_TYPE {
  USER_REGISTER = 'USER_REGISTER',
  USER_LOGIN = 'USER_LOGIN',
  USER_LOGOUT = 'USER_LOGOUT',
  USER_CHECK_TOKEN = 'USER_CHECK_TOKEN',
  USER_REISSUE = 'USER_REISSUE',
  USER_SUCCESS = 'USER_SUCCESS',
  USER_FAIL = 'USER_FAIL',
}
