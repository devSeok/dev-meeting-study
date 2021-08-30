import Send from './interceptors';
import { Register, Login } from '../_actions/user_actions';

interface Token {
  accessToken: string;
  refreshToken: string;
  accessTokenExpiresIn?: number;
}

enum Method {
  POST = 'POST',
  GET = 'GET',
}

// 회원가입
const register_user = (user: Register) => {
  return Send({
    method: Method.POST,
    url: '/api/auth/signup',
    data: user,
  });
};

// 로그인
const login_user = (user: Login) => {
  return Send({
    method: Method.POST,
    url: '/api/auth/login',
    data: user,
  });
};

// 토큰 갱신
const reissueToken = (token: Token) => {
  return Send({
    method: Method.POST,
    url: '/api/auth/reissue',
    data: token,
  });
};

// 내 정보
const myInfo = () => {
  return Send({
    method: Method.GET,
    url: '/api/member/me',
  });
};

// member
const member = (email: string) => {
  return Send({
    method: Method.GET,
    url: `/api/member/${email}`,
  });
};

export { register_user, login_user, reissueToken, myInfo, member };
