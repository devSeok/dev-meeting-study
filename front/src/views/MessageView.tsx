import React, { useState } from 'react';
import { useDispatch } from 'react-redux';
import { sendMessage } from '../ToolKit/user';
import StudyHeader from '../components/StudyHeader';
import { Input, InputTitle, InputWrap, Main, Section } from '../elements';

interface MessageViewProps {}

function MessageView({}: MessageViewProps) {
  const Dispatch = useDispatch();

  const [inputs, setInputs] = useState({
    email: '',
  });
  const { email } = inputs;
  const onClickMessage = () => {
    const obj = {
      content: '하이',
      email: 'test1@test.com',
    };

    Dispatch(sendMessage(obj));
    // console.log('온클릭', Dispatch(sendMessage(obj)));
  };
  return (
    <>
      <StudyHeader>메세지 보내기</StudyHeader>
      {/* <button onClick={onClickMessage}>버튼</button> */}
      <Main>
        <Section>
          <InputWrap>
            <InputTitle htmlFor="email">Message To:</InputTitle>
            <Input placeholder="받는사람을 입력하세요" id="email" name="email" type="email" value={email} />
          </InputWrap>
        </Section>
      </Main>
    </>
  );
}

export default MessageView;
