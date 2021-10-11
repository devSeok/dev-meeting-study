import React, { useState, useRef } from 'react';
import { useDispatch } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import { login } from '../ToolKit/user';
import { DispatchReduxFailRes } from '../ToolKit/axiosType';
import StudyHeader from '../components/StudyHeader';
import { Main, Section, InputWrap, Input, InputTitle, InputSub, InputSubLabel, Button } from '../elements';

function LoginView() {
  const dispatch = useDispatch();
  const history = useHistory();
  const emailRef = useRef<HTMLInputElement>(null);
  const passwordRef = useRef<HTMLInputElement>(null);

  const [inputs, setInputs] = useState({
    email: '',
    password: '',
  });

  const { email, password } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    const { code } = e;

    if (code === 'Enter') {
      checkInputs();
    }
  };

  const checkInputs = () => {
    if (email !== '' && password !== '') {
      onLogin();
    } else {
      const obj = {
        email: emailRef,
        password: passwordRef,
      };
      for (let prop in inputs) {
        // @ts-ignore
        if (obj[prop].current.value === '') {
          // @ts-ignore
          obj[prop].current?.focus();
          break;
        }
      }
    }
  };

  const onLogin = async () => {
    const obj = {
      email,
      password,
    };

    // 'AsyncThunkAction<{ type: USER_TYPE; payload: ResLogin; }, Login, {}>' 형식에 'then' 속성이 없습니다.ts(2339)
    // @ts-ignore
    const response: DispatchReduxFailRes = await dispatch(login(obj));

    if (response.error) {
      if (response.payload === undefined) {
        alert('서버 에러입니다. 계속 지속될 시 문의 해주세요.');
      } else {
        alert('로그인 실패');
      }
      console.log(response);

      return;
    }
    history.push('/');
  };

  return (
    <>
      <StudyHeader>로그인</StudyHeader>
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
              onChange={onChange}
              onKeyDown={onEnter}
              placeholder="이메일"
            />
            <InputSub style={{ display: 'flex', alignItems: 'center' }}>
              <input id="save-id" type="checkbox" />
              <InputSubLabel htmlFor="save-id">아이디 저장</InputSubLabel>
            </InputSub>
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="password">비밀번호</InputTitle>
            <Input
              id="password"
              name="password"
              type="password"
              value={password}
              ref={passwordRef}
              onChange={onChange}
              onKeyDown={onEnter}
              placeholder="비밀번호"
            />
            <InputSub>
              <InputSubLabel>비밀번호를 잊어버리셨나요?</InputSubLabel>
            </InputSub>
          </InputWrap>
          <Button style={{ marginTop: '20px', backgroundColor: '#51aafe' }} onClick={checkInputs}>
            로그인
          </Button>
          <div
            style={{
              display: 'flex',
              alignItems: 'center',
              margin: '15px 0px 13px',
            }}
          >
            <div style={{ width: '146px', borderBottom: '1px solid #000' }}></div>
            <span style={{ padding: '0px 10px' }}>or</span>
            <div style={{ width: '147px', borderBottom: '1px solid #000' }}></div>
          </div>
          <Button>
            <Link
              to="/register"
              style={{
                width: '100%',
                height: '100%',
                borderRadius: '5px',
                display: 'inline-block',
                lineHeight: '43px',
                backgroundColor: '#f8f8f8',
              }}
            >
              회원가입
            </Link>
          </Button>
        </Section>
      </Main>
    </>
  );
}

export default LoginView;
