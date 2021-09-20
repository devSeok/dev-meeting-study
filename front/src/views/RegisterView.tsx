import React, { useState, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { register_check_email, register_check_num } from '../API/index';
import { register } from '../ToolKit/user';
import { DispatchReduxFailRes } from '../ToolKit/axiosType';
import StudyHeader from '../components/StudyHeader';
import { Main, Section, InputWrap, Input, InputTitle, InputWarningMes, Button } from '../elements';
import { emailExp, emailCheckExp, passwordExp, nicknameExp, checkExp } from '../RegularExpressions/index';

function RegisterView() {
  const dispatch = useDispatch();
  const history = useHistory();
  // input ref
  const emailRef = useRef<HTMLInputElement>(null);
  const checkNumRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);
  const nicknameRef = useRef<HTMLInputElement>(null);
  // 경고 ref
  const emailWarningMesRef = useRef<HTMLInputElement>(null);
  const emailCheckWarningMesRef = useRef<HTMLInputElement>(null);
  const passwordWarningMesRef = useRef<HTMLInputElement>(null);
  const nicknameWarningMesRef = useRef<HTMLInputElement>(null);

  const [inputs, setInputs] = useState({
    email: '',
    checkNum: '',
    password: '',
    nickname: '',
  });

  const [expWarning, setExpWarning] = useState({
    email: true,
    checkNum: true,
    password: true,
    nickname: true,
  });

  // 인증번호 요청
  const [checkEmailReq, setCheckEmailReq] = useState(false);
  // 인증번호 확인
  const [checkEmailRes, setCheckEmailRes] = useState(false);
  // 인증번호 버튼 상태
  const [checkEmailButtonValue, setCheckEmailButtonValue] = useState('요청');

  const { email, checkNum, password, nickname } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement>, target: any) => {
    const { name, value } = e.target;

    showWarningMessage('', target);

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onBlur = (e: React.FocusEvent<HTMLInputElement>, exp: RegExp, target: any) => {
    const { value } = e.target;

    const res = checkExp(exp, value);

    showWarningMessage(res, target);
  };

  const showWarningMessage = (res: boolean | string, target: any) => {
    const span = target.current;
    const div = target.current.parentNode;
    const input = div.children[1];

    const elements = [span, input];
    if (res === '') {
      elements.forEach((item) => {
        item.classList.remove('success');
        item.classList.remove('failure');
      });
    } else {
      setExpWarning({
        ...expWarning,
        [input.id]: !res,
      });
      if (res) {
        elements.forEach((item) => {
          item.classList.add('success');
          item.classList.remove('failure');
        });
      } else {
        elements.forEach((item) => {
          item.classList.add('failure');
          item.classList.remove('success');
        });
      }
    }
  };

  const onCheckEmail = async () => {
    try {
      if (checkEmailReq) {
        if (!expWarning.checkNum) {
          await register_check_num(checkNum, email);

          setCheckEmailRes(true);
          alert('이메일 인증 성공!');
        }
      } else {
        if (!expWarning.email) {
          alert('인증번호 요청 (인터넷 상태에 따라 오래 걸릴 수 있습니다.)');

          setCheckEmailButtonValue('요청 중');

          await register_check_email(email);

          setCheckEmailReq(true);

          setCheckEmailButtonValue('인증');

          alert('인증번호 요청 완료!');
        } else {
          alert('이메일을 입력해주세요.');
        }
      }
    } catch (err: any) {
      setCheckEmailRes(false);

      const message = err.response.data.message;

      alert(message);

      setCheckEmailButtonValue('요청');
    }
  };

  // 본 코드
  const onRegister = async () => {
    if (checkEmailRes) {
      if (!expWarning.email && !expWarning.password && !expWarning.nickname) {
        const obj = {
          email,
          password,
          nickname,
        };
        // 'AsyncThunkAction<ResRegister, Register, {}>' 형식에 'then' 속성이 없습니다.ts(2339)
        await dispatch(register(obj))
          // @ts-ignore
          .then((res: DispatchReduxFailRes) => {
            if (res.payload.payload?.status === 200) {
              history.push('/login');
            } else {
              alert(res.payload.message);

              if (res.payload.message === '이미 가입되어 있는 유저 입니다.') {
                setInputs({
                  ...inputs,
                  email: '',
                  checkNum: '',
                });
                // email input에 포커싱
                emailRef.current?.focus();
              }
              setCheckEmailReq(false);
              setCheckEmailRes(false);
              setCheckEmailButtonValue('요청');
            }
          });
      } else {
        alert('모든 입력 창을 입력해주세요');
      }
    } else {
      alert('이메일 인증을 해주세요!');
    }
  };
  return (
    <>
      <StudyHeader>회원가입</StudyHeader>
      <Main>
        <Section>
          <InputWrap>
            <InputTitle htmlFor="email">이메일</InputTitle>
            <Input
              id="email"
              name="email"
              type="email"
              value={email}
              ref={emailRef}
              onChange={(e) => onChange(e, emailWarningMesRef)}
              onBlur={(e) => onBlur(e, emailExp, emailWarningMesRef)}
              placeholder="이메일"
            />
            <InputWarningMes ref={emailWarningMesRef}>이메일 형식에 맞지 않습니다.</InputWarningMes>
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="checkNum">인증번호 확인</InputTitle>
            {checkEmailRes ? (
              <Input value="인증 완료" className="success" placeholder="인증번호 완료" readOnly />
            ) : (
              <>
                <Input
                  id="checkNum"
                  name="checkNum"
                  type="text"
                  value={checkNum}
                  ref={checkNumRef}
                  onChange={(e) => onChange(e, emailCheckWarningMesRef)}
                  onBlur={(e) => onBlur(e, emailCheckExp, emailCheckWarningMesRef)}
                  placeholder="인증번호"
                />
                <Button
                  style={{
                    width: '50px',
                    height: '35px',
                    position: 'absolute',
                    top: '18px',
                    right: '-70px',
                    fontSize: '15px',
                    backgroundColor: '#51aafe',
                  }}
                  onClick={onCheckEmail}
                >
                  {checkEmailButtonValue}
                </Button>
                <InputWarningMes ref={emailCheckWarningMesRef}>인증번호가 조건에 해당되지 않습니다.</InputWarningMes>
              </>
            )}
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="password">비밀번호</InputTitle>
            <Input
              id="password"
              name="password"
              type="password"
              value={password}
              ref={passwordRef}
              onChange={(e) => onChange(e, passwordWarningMesRef)}
              onBlur={(e) => onBlur(e, passwordExp, passwordWarningMesRef)}
              placeholder="비밀번호"
            />
            <InputWarningMes ref={passwordWarningMesRef}>8~20자 영문 대 소문자, 특수문자 포함</InputWarningMes>
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="nickname">닉네임</InputTitle>
            <Input
              id="nickname"
              name="nickname"
              type="text"
              value={nickname}
              ref={nicknameRef}
              onChange={(e) => onChange(e, nicknameWarningMesRef)}
              onBlur={(e) => onBlur(e, nicknameExp, nicknameWarningMesRef)}
              placeholder="닉네임"
            />
            <InputWarningMes ref={nicknameWarningMesRef}>2~30자 한글 혹은 영어</InputWarningMes>
          </InputWrap>
          <Button style={{ backgroundColor: '#51aafe' }} onClick={onRegister}>
            회원가입
          </Button>
        </Section>
      </Main>
    </>
  );
}

export default RegisterView;
