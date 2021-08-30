import React, { useState } from 'react';
import styled, { css } from 'styled-components';
import StudyHeader from '../components/StudyHeader';
import StudyColumnList from '../components/StudyColumnList';
import { Main, Section, InputWrap, Input, InputTitle, Button, Icon } from '../elements';
import PermIdentityIcon from '@material-ui/icons/PermIdentity';
import MenuBookIcon from '@material-ui/icons/MenuBook';
import MailOutlineIcon from '@material-ui/icons/MailOutline';

import person from '../asset/image/person.png';

const SelectItem = styled.div`
  width: 500px;
  background-color: #f8f8f8;
`;

const Items = styled.ul`
  display: flex;
  flex-wrap: wrap;
  padding: 20px;
`;

const Item = styled.li`
  width: 110px;
  padding: 20px;
  cursor: pointer;

  &.selected {
    color: #8dc6fb;
  }
`;

const items = [
  {
    title: '개인정보',
    image: <PermIdentityIcon />,
  },
  {
    title: '스터디',
    image: <MenuBookIcon />,
  },
  {
    title: '쪽지',
    image: <MailOutlineIcon />,
  },
  {
    title: '회원탈퇴',
    image: <PermIdentityIcon />,
  },
];

const study = [
  {
    title: 'java spring',
    type: '오프라인',
    maxUser: 3,
    lang: ['java'],
  },
  {
    title: 'java 하실 분',
    type: '온라인',
    maxUser: 5,
    lang: ['java'],
  },
];

const post = [
  {
    title: 'A',
    sub: '회의 들어오세요',
  },
  {
    title: 'A',
    sub: '회의 들어오세요',
  },
];

function MyStudyView() {
  const [inputs, setInputs] = useState({
    email: '',
    nickname: '',
    password: '',
  });
  const [item, setItem] = useState(0);

  const onClick = (e: any, index: number) => {
    // 나중에 코드 수정해야 함
    const lis = document.querySelectorAll('li');
    lis.forEach((target) => {
      if (target === e) {
        target.classList.add('selected');
      } else {
        target.classList.remove('selected');
      }
    });
    setItem(index);

    // 이동 시 초기화
    setInputs({
      email: '',
      nickname: '',
      password: '',
    });
  };

  const onChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const { name, value } = e.target;

    setInputs({
      ...inputs,
      [name]: value,
    });
  };

  return (
    <>
      <StudyHeader>MY 스터디</StudyHeader>
      <Main>
        <Section>
          <SelectItem>
            <Items>
              {items.map(({ image, title }, index) => {
                return (
                  <Item
                    onClick={(e) => onClick(e.currentTarget, index)}
                    key={index}
                    className={index === 0 ? 'selected' : ''}
                  >
                    <Icon style={{ display: 'flex', flexDirection: 'column' }}>
                      {image}
                      <span>{title}</span>
                    </Icon>
                  </Item>
                );
              })}
            </Items>
          </SelectItem>
          <div style={{ marginTop: '50px' }}>
            {item === 0 && (
              <div style={{ width: '500px', display: 'flex' }}>
                <div style={{ padding: '10px 20px' }}>
                  <img src={person} alt="유저" />
                </div>
                <div>
                  <InputWrap>
                    <InputTitle htmlFor="email">이메일</InputTitle>
                    <Input id="email" name="email" type="email" onChange={onChange} placeholder="이메일" />
                  </InputWrap>
                  <InputWrap>
                    <InputTitle htmlFor="nickname">닉네임</InputTitle>
                    <Input id="nickname" name="nickname" type="text" onChange={onChange} placeholder="닉네임" />
                  </InputWrap>
                  <Button style={{ backgroundColor: '#51aafe' }}>수정하기</Button>
                </div>
              </div>
            )}
            {item === 1 && <StudyColumnList items={study} index={item} />}
            {item === 2 && <StudyColumnList items={post} index={item} />}
            {item === 3 && (
              <div>
                <h3 style={{ marginBottom: '30px', fontWeight: 'lighter' }}>정말 탈퇴하시겠습니까?</h3>
                <InputWrap>
                  <InputTitle htmlFor="password">비밀번호</InputTitle>
                  <Input id="password" name="password" type="password" onChange={onChange} placeholder="비밀번호" />
                </InputWrap>
                <Button style={{ backgroundColor: '#51aafe' }}>탈퇴</Button>
              </div>
            )}
          </div>
        </Section>
      </Main>
    </>
  );
}

export default MyStudyView;
