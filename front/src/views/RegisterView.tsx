import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { registerUser } from '../_actions/user_actions';
import StudyHeader from '../components/StudyHeader';
import { Main, Section, InputWrap, Input, InputTitle, Button } from '../elements';

function RegisterView() {
  const dispatch = useDispatch();
  const history = useHistory();

  const [inputs, setInputs] = useState({
    email: '',
    checkNum: '',
    password: '',
    nickname: '',
  });

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onRegister = async () => {
    const { email, password, nickname } = inputs;
    if (email !== '' && password !== '') {
      const response = await dispatch(registerUser({ email, password, nickname }));
      // response가 항상 true를 리턴한다고 ts에러 발생 해서 임시로 ignore 사용
      // @ts-ignore
      if (response) {
        history.push('/login');
      }
    }
  };

  return (
    <>
      <StudyHeader>회원가입</StudyHeader>
      <Main>
        <Section>
          <InputWrap>
            <InputTitle htmlFor="email">이메일</InputTitle>
            <Input id="email" name="email" type="email" onChange={onChange} placeholder="이메일" />
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="checkNum">인증번호 확인</InputTitle>
            <Input id="checkNum" name="checkNum" type="number" onChange={onChange} placeholder="인증번호" />
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
            >
              확인
            </Button>
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="password">비밀번호</InputTitle>
            <Input id="password" name="password" type="password" onChange={onChange} placeholder="비밀번호" />
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="nickname">닉네임</InputTitle>
            <Input id="nickname" name="nickname" type="text" onChange={onChange} placeholder="닉네임" />
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
