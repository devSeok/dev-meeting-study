import Send from './interceptors';
import { RegisterType, LoginType, Token } from '../ToolKit/userType';
import Pagination from '../components/Pagination';

enum Method {
  POST = 'POST',
  GET = 'GET',
  PUT = 'PUT',
  DELETE = 'DELETE',
}

// 회원가입
const register_user = (user: RegisterType) => {
  return Send({
    method: Method.POST,
    url: '/api/auth/signup',
    data: user,
  });
};

// 이메일 인증 번호 요청
const register_check_email = (email: string) => {
  return Send({
    method: Method.POST,
    url: '/api/auth/email',
    data: { email },
  });
};
// 이메일 인증 번호 인증
const register_check_num = (auth_number: string, email: string) => {
  return Send({
    method: Method.POST,
    url: '/api/auth/verifyCode',
    data: { auth_number, email },
  });
};

// 로그인
const login_user = (user: LoginType) => {
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

// const pagination = () => {}

export { register_user, register_check_email, register_check_num, login_user, reissueToken, myInfo, member };
