import Send from './interceptors';
import { RegisterType, LoginType, Token, Addresses, SubjectType, StudyType } from '../ToolKit/userType';

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
    url: '/auth/signup',
    data: user,
  });
};

// 이메일 인증 번호 요청
const register_check_email = (email: string) => {
  return Send({
    method: Method.POST,
    url: '/auth/email',
    data: { email },
  });
};
// 이메일 인증 번호 인증
const register_check_num = (auth_number: string, email: string) => {
  return Send({
    method: Method.POST,
    url: '/auth/verifyCode',
    data: { auth_number, email },
  });
};

// 로그인
const login_user = (user: LoginType) => {
  return Send({
    method: Method.POST,
    url: '/auth/login',
    data: user,
  });
};

// 토큰 갱신
const reissueToken = (token: Token) => {
  return Send({
    method: Method.POST,
    url: '/auth/reissue',
    data: token,
  });
};

// 내 정보
const myInfo = () => {
  return Send({
    method: Method.GET,
    url: '/member/me',
  });
};

// member
const member = (email: string) => {
  return Send({
    method: Method.GET,
    url: `/member/${email}`,
  });
};

// 주소 추가
const addAddresses = (addresses: Addresses) => {
  return Send({
    method: Method.POST,
    url: '/addresses',
    data: addresses,
  });
};

// 주소 검색
const searchAddresses = (id: string) => {
  return Send({
    method: Method.GET,
    url: `/addresses/${id}`,
  });
};

// subjects 가져오기
const getSubjects = () => {
  return Send({
    method: Method.GET,
    url: '/subjects',
  });
};

// subject 추가하기
const addSubject = (subject: SubjectType) => {
  return Send({
    method: Method.POST,
    url: '/subjects',
    data: subject,
  });
};

// 스터디 저장
const saveStudty = (study: StudyType) => {
  return Send({
    method: Method.POST,
    url: '/studies',
    data: study,
  });
};

export {
  register_user,
  register_check_email,
  register_check_num,
  login_user,
  reissueToken,
  myInfo,
  member,
  addAddresses,
  searchAddresses,
  getSubjects,
  addSubject,
  saveStudty,
};
