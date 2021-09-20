// 이메일 정규식
const emailExp = /^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$/i;

// 인증번호 정규식
const emailCheckExp = /^\d{3}-\d{3}$/;

// 비밀번호 정규식 - 8~20자 영문 대 소문자, 특수문자 포함
const passwordExp = /^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,20}$/;

// 닉네임 정규식 - 2~30자 한글, 영어 가능
const nicknameExp = /^.{2,30}$/;

// 정규식 체크
const checkExp = (exp: RegExp, value: string) => {
  return exp.test(value);
};

export { emailExp, emailCheckExp, passwordExp, nicknameExp, checkExp };
