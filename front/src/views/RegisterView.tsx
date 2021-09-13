import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { useHistory } from 'react-router-dom';
import { register_check_email, register_check_num } from '../API/index';
import { register } from '../ToolKit/user';
import StudyHeader from '../components/StudyHeader';
import { Main, Section, InputWrap, Input, InputTitle, Button } from '../elements';

function RegisterView() {
  const dispatch = useDispatch();
  const history = useHistory();

  const [inputs, setInputs] = useState({
    email: '',
    checkNum: 0,
    password: '',
    nickname: '',
  });

  // 인증번호 요청
  const [checkEmailReq, setCheckEmailReq] = useState(false);
  // 인증번호 확인
  const [checkEmailRes, setCheckEmailRes] = useState(false);
  // 인증번호 버튼 상태
  const [checkEmailButtonValue, setCheckEmailButtonValue] = useState('요청');

  const { email, checkNum, password, nickname } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    // console.log('name', name);
    // console.log('value', value);

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  const onCheckEmail = async () => {
    try {
      if (checkEmailReq) {
        await register_check_num(checkNum, email);

        setCheckEmailRes(true);
        alert('이메일 인증 성공!');
      } else {
        if (email !== '') {
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
      const code = err.response.data.code;
      switch (code) {
        case 'M010':
          alert('인증번호가 다릅니다.');
          break;
        // register_check_email 실패 code가 뭔지 몰라서 이렇게 했습니다. (나중에 수정)
        case 'code':
          alert('인증번호 요청이 실패했습니다.');
          break;
        case 'C004':
          alert('서버 에러 Status 500');
          break;
        default:
          alert('default입니다.');
          break;
      }
      setCheckEmailButtonValue('요청');
    }
  };

  const onRegister = async () => {
    if (checkEmailRes) {
      if (email !== '' && checkNum !== 0 && password !== '' && nickname !== '') {
        const obj = {
          email,
          password,
          nickname,
        };
        // 'AsyncThunkAction<ResRegister, Register, {}>' 형식에 'then' 속성이 없습니다.ts(2339)
        // @ts-ignore
        await dispatch(register(obj)).then((res: { payload: { payload: { status: number } } }) => {
          if (res.payload.payload.status === 200) {
            history.push('/');
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
            <Input id="email" name="email" type="email" onChange={onChange} placeholder="이메일" />
          </InputWrap>
          <InputWrap>
            <InputTitle htmlFor="checkNum">인증번호 확인</InputTitle>
            {checkEmailRes ? (
              <Input value="인증 완료" placeholder="인증번호 완료" readOnly />
            ) : (
              <>
                <Input id="checkNum" name="checkNum" type="text" onChange={onChange} placeholder="인증번호" />
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
              </>
            )}
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
