import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { Link, useHistory } from 'react-router-dom';
import StudyHeader from '../components/StudyHeader';
import { Main, Section, InputWrap, Input, InputTitle, InputSub, InputSubLabel, Button } from '../elements';
import { login } from '../ToolKit/user';

function LoginView() {
  const dispatch = useDispatch();
  const history = useHistory();

  const [inputs, setInputs] = useState({
    email: '',
    password: '',
  });

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onLogin = async () => {
    const { email, password } = inputs;
    const obj = {
      email,
      password,
    };
    // 'AsyncThunkAction<{ type: USER_TYPE; payload: ResLogin; }, Login, {}>' 형식에 'then' 속성이 없습니다.ts(2339)
    // @ts-ignore
    await dispatch(login(obj)).then((res: { payload: { payload: { status: number } } }) => {
      console.log(res.payload.payload.status);
      if (res.payload.payload.status === 200) {
        history.push('/');
      }
    });
  };

  return (
    <>
      <StudyHeader>로그인</StudyHeader>
      <Main>
        <Section>
          <InputWrap>
            <InputTitle htmlFor="email">이메일</InputTitle>
            <Input id="email" name="email" type="email" onChange={onChange} placeholder="이메일" />
            <InputSub style={{ display: 'flex', alignItems: 'center' }}>
              <input id="save-id" type="checkbox" />
              <InputSubLabel htmlFor="save-id">아이디 저장</InputSubLabel>
            </InputSub>
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="password">비밀번호</InputTitle>
            <Input id="password" name="password" type="password" onChange={onChange} placeholder="비밀번호" />
            <InputSub>
              <InputSubLabel>비밀번호를 잊어버리셨나요?</InputSubLabel>
            </InputSub>
          </InputWrap>
          <Button style={{ marginTop: '20px', backgroundColor: '#51aafe' }} onClick={onLogin}>
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
