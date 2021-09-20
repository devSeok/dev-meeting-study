import React, { useState } from 'react';
import styled from 'styled-components';
import { Main, Section, InputWrap, Input, InputTitle, Button, Icon } from '../elements';
import StudyHeader from '../components/StudyHeader';

const Select = styled.select`
  width: 100%;
  height: 43px;
  margin-top: 13px;
  padding: 4px 10px;
  border: 1px solid #000;
  border-radius: 5px;
  background-color: #fff;
  outline: none;
`;

const WrapInput = styled(InputWrap)`
  width: 90%;
`;

const SelectOffline = styled(Select)`
  width: 32%;
`;

function CreateStudyView() {
  const [inputs, setInputs] = useState({
    title: '',
    studyList: '종류1',
    maxUser: 0,
    studyType: 'online',
    sido: '',
    gun: '',
    gu: '',
    contents: '',
    image: '',
  });

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  return (
    <>
      <StudyHeader>스터디 등록</StudyHeader>
      <Main>
        <Section>
          <WrapInput>
            <InputTitle htmlFor="title">제목</InputTitle>
            <Input id="title" name="title" type="text" onChange={onChange} placeholder="제목" />
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="study-list-select">스터디 종류</InputTitle>
            <Select id="study-list-select" name="studyList" onChange={onChange}>
              <option value="1">종류1</option>
              <option value="2">종류2</option>
            </Select>
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="max-user">최대 인원 수</InputTitle>
            <Input id="max-user" name="maxUser" type="number" onChange={onChange} placeholder="최대 인원 수" />
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="study-type">스터디 타입</InputTitle>
            <Select id="type-select" name="studyType" onChange={onChange}>
              <option value="online">온라인</option>
              <option value="offline">오프라인</option>
            </Select>
          </WrapInput>
          {inputs.studyType === 'offline' && (
            <WrapInput>
              <InputTitle>오프라인 장소</InputTitle>
              <SelectOffline id="si-select" name="sido" onChange={onChange}>
                <option value="si">시</option>
                <option value="sis">시s</option>
              </SelectOffline>
              <SelectOffline id="gun-select" name="gun" onChange={onChange} style={{ margin: '0px 14.8px' }}>
                <option value="gun">군</option>
                <option value="guns">군s</option>
              </SelectOffline>
              <SelectOffline id="gu-select" name="gu" onChange={onChange}>
                <option value="gu">구</option>
                <option value="gus">구s</option>
              </SelectOffline>
            </WrapInput>
          )}
          <WrapInput>
            <InputTitle htmlFor="contents">내용</InputTitle>
            <Input id="contents" name="contents" type="text" onChange={onChange} placeholder="내용" />
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="image">대표 이미지</InputTitle>
            <Input id="image" name="image" type="file" onChange={onChange} style={{ border: 'none', padding: '0px' }} />
          </WrapInput>
          <Button
            style={{
              width: '100%',
              marginTop: '20px',
              backgroundColor: '#51aafe',
            }}
          >
            스터디 등록
          </Button>
        </Section>
      </Main>
    </>
  );
}

export default CreateStudyView;
