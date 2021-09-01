import { AxiosResponse } from 'axios';
import { Dispatch } from 'redux';
import { register_user, login_user, reissueToken } from '../API/index';

import { UserDispatchType, PayloadSuccessType, PayloadFailType, USER_TYPE } from './type';

export interface Register {
  email: string;
  password: string;
}

export interface Login {
  email: string;
  password: string;
}

interface Token {
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresIn?: number;
}

// 인터페이스 옮겨야함
interface Token {
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresIn?: number;
}

export interface ResLogin {
  data: Token;
  message: string;
}

export interface ResRegister {}

// 회원가입
const registerUser =
  (user: Register) =>
  async (dispatch: Dispatch<UserDispatchType>): Promise<boolean> => {
    try {
      const { data }: AxiosResponse<PayloadSuccessType> = await register_user(user);

      saveLocalStorage(data.data);

      dispatch({
        type: USER_TYPE.USER_REGISTER,
        payload: data,
      });
      return true;
    } catch (err) {
      console.log(err.response.data);
      const data: PayloadFailType = {
        type: USER_TYPE.USER_REGISTER,
        ...err.response.data,
      };
      dispatch({
        type: USER_TYPE.USER_FAIL,
        payload: data,
      });
      return false;
    }
  };

// 로그인
const loginUser =
  (user: Login) =>
  async (dispatch: Dispatch<UserDispatchType>): Promise<boolean> => {
    try {
      const { data }: AxiosResponse<PayloadSuccessType> = await login_user(user);

      saveLocalStorage(data.data);

      dispatch({
        type: USER_TYPE.USER_LOGIN,
        payload: data,
      });

      return true;
    } catch (err) {
      console.log(err.response.data);
      const data: PayloadFailType = {
        type: USER_TYPE.USER_LOGIN,
        ...err.response.data,
      };
      dispatch({
        type: USER_TYPE.USER_FAIL,
        payload: data,
      });
      return false;
    }
  };

// 로그아웃
const logoutUser = () => {
  localStorage.clear();
  return {
    type: USER_TYPE.USER_LOGOUT,
  };
};

// 토큰 체크
const checkToken =
  () =>
  async (dispatch: Dispatch<UserDispatchType>): Promise<boolean> => {
    try {
      const date = new Date();
      const expiresIn = parseInt(localStorage.getItem('accessTokenExpiresIn') as string);
      const now = date.getTime();
      const diffTime = expiresIn - now;
      // const diffTime = 50000;
      if (expiresIn) {
        if (diffTime < 59405 && diffTime > 0) {
          const accessToken = localStorage.getItem('accessToken') as string;
          const refreshToken = localStorage.getItem('refreshToken') as string;
          const { data }: AxiosResponse<PayloadSuccessType> = await reissueToken({ accessToken, refreshToken });
          saveLocalStorage(data.data);
          dispatch({
            type: USER_TYPE.USER_REISSUE,
            payload: data,
          });
          console.log('토큰 갱신 성공');
        }
        if (diffTime <= 0) {
          logoutUser();
          alert('로그아웃 되었습니다.');
          return false;
        }
        return true;
      } else {
        return false;
      }
    } catch (err) {
      console.log(err.response.data);
      alert('토큰 갱신 실패');
      const data: PayloadFailType = {
        type: USER_TYPE.USER_REISSUE,
        ...err.response.data,
      };
      dispatch({
        type: USER_TYPE.USER_FAIL,
        payload: data,
      });
      return false;
    }
  };

export { registerUser, loginUser, logoutUser, checkToken };

// 로컬 스토리지 저장
const saveLocalStorage = (token: Token) => {
  console.log('token', token);
  localStorage.setItem('accessToken', token.accessToken);
  localStorage.setItem('refreshToken', token.refreshToken);
  localStorage.setItem('accessTokenExpiresIn', JSON.stringify(token.accessTokenExpiresIn));
};
