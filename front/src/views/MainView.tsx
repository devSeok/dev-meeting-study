import React, { useState } from 'react';
import styled from 'styled-components';
import { Main, Section, Icon } from '../elements';
import StudyHeader from '../components/StudyHeaderRemove';
import SearchIcon from '@material-ui/icons/Search';

const study = [
  {
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    title: '오픈 스터디',
    type: '오프라인',
  },
];

const Select = styled.select`
  height: 40px;
  border: none;
  outline: none;
  padding: 10px;
  border-radius: 5px;
  margin-right: 30px;
  background-color: #f8f8f8;
  cursor: pointer;
`;

const ItemList = styled.ul`
  display: flex;
  flex-wrap: wrap;
  margin-top: 30px;
`;

const Item = styled.li`
  width: 360px;
  height: 200px;
  position: relative;
  border: 1px solid #000;
  border-radius: 8px;
  margin-bottom: 30px;
  cursor: pointer;

  &:nth-child(odd) {
    margin-right: 16px;
  }
`;

function OpenStudyView() {
  const [inputs, setInputs] = useState({
    studyType: 'online',
    sido: '',
    gun: '',
    gu: '',
    studyList: '종류1',
  });

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: value,
    });
    console.log(inputs);
  };

  return (
    <>
      <StudyHeader />
      <Main>
        <Section>
          <div
            style={{
              width: '100%',
              display: 'flex',
              justifyContent: 'space-between',
              alignItems: 'center',
            }}
          >
            <div>
              <span
                style={{
                  display: 'inline-block',
                  lineHeight: '40px',
                  marginRight: '30px',
                  padding: '0px 10px',
                  borderRadius: '5px',
                  backgroundColor: '#51aafe',
                }}
              >
                전체
              </span>
              <Select id="type-select" name="studyType" onChange={onChange}>
                <option value="online">온라인</option>
                <option value="offline">오프라인</option>
              </Select>
              <Select id="study-select" name="studyList" onChange={onChange}>
                <option value="1">종류1</option>
                <option value="2">종류2</option>
              </Select>
              {inputs.studyType === 'offline' && (
                <>
                  <Select id="city-select" name="sido" onChange={onChange}>
                    <option value="sido1">시도1</option>
                    <option value="sido2">시도2</option>
                  </Select>
                  <Select id="city-select" name="gun" onChange={onChange}>
                    <option value="gun1">군1</option>
                    <option value="gun2">군2</option>
                  </Select>
                  <Select id="city-select" name="gu" onChange={onChange}>
                    <option value="gu1">구1</option>
                    <option value="gu2">구2</option>
                  </Select>
                </>
              )}
            </div>
            {/* 검색 아이콘 로그인 옆으로 이동 예정 */}
            <Icon>
              <SearchIcon />
            </Icon>
          </div>
          <ItemList>
            {study.map((item, index) => {
              return (
                <Item key={index}>
                  <div
                    style={{
                      position: 'absolute',
                      left: '20px',
                      bottom: '10px',
                    }}
                  >
                    <h2>{item.title}</h2>
                    <span>{item.type}</span>
                  </div>
                </Item>
              );
            })}
          </ItemList>
        </Section>
      </Main>
    </>
  );
}

export default OpenStudyView;
