import Send from './interceptors';
import { SendMessageType } from '../ToolKit/MessageTypes';
import { RegisterType, LoginType, Token, Addresses, SubjectType, StudyType } from '../ToolKit/userType';

enum Method {
  POST = 'POST',
  GET = 'GET',
  PUT = 'PUT',
  DELETE = 'DELETE',
}

// 맞아요? 오 어디에요 안보여요 지리비
//
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

const register_check_nickname = (nickname: string) => {
  return Send({
    method: Method.GET,
    url: `auth/nickname/exists/${nickname}`,
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

// messages 보내기
const sendMessages = (message: SendMessageType) => {
  return Send({
    method: Method.POST,
    url: '/messages',
    data: message,
  });
};

// messages 목록
const listMessages = () => {
  return Send({
    method: Method.GET,
    url: '/messages',
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

export interface FilterType {
  studyInstanceType: string;
  address1: string | null;
  lastId: number | null;
  studyType: string;
  sorted: string;
  subjectId: number | null;
  title: string | null;
  offset: number;
}

const getStudty = (filter: FilterType) => {
  let url = `/studies${makeQueryParm(filter)}`;

  return Send({
    method: Method.GET,
    url,
  });
};

export {
  register_user,
  register_check_email,
  register_check_num,
  register_check_nickname,
  login_user,
  reissueToken,
  myInfo,
  member,
  sendMessages,
  listMessages,
  addAddresses,
  searchAddresses,
  getSubjects,
  addSubject,
  saveStudty,
  getStudty,
};

const makeQueryParm = (obj: object) => {
  let url = '?';

  for (let prop in obj) {
    // Element implicitly has an 'any' type because expression of type 'string' can't be used to index type '{}'.
    // No index signature with a parameter of type 'string' was found on type '{}'.ts(7053)
    // @ts-ignore
    if (obj[prop] === null) {
      continue;
    }
    // @ts-ignore
    url = url += `${prop}=${obj[prop]}&`;
  }

  // 마지막 & 제거
  url = url.substr(0, url.length - 1);

  return url;
};
