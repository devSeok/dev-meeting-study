import React, { useState } from 'react';
import styled from 'styled-components';
import { Main, Section, Icon } from '../elements';
import SearchIcon from '@material-ui/icons/Search';
import StudyHeader from '../components/StudyHeaderRemove';
import StudyFooter from '../components/StudyFooter';
import Items from '../components/Items';

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
  display: inline-flex;
  align-content: flex-start;
  flex-wrap: wrap;
  margin-top: 30px;
  @media screen and (max-width: 740px) {
    width: 90%;
    flex-wrap: no-wrap;
  }
`;
export const Item = styled.li`
  width: 360px;
  height: 200px;
  position: relative;
  border: 1px solid #000;
  border-radius: 8px;
  margin-bottom: 20px;
  cursor: pointer;

  &:nth-child(odd) {
    margin-right: 16px;
  }
  @media screen and (max-width: 740px) {
    & {
      width: 100%;
      margin-right: 0px;
    }
    &:nth-child(odd) {
      margin-right: 0px;
    }
  }
`;
const SidoGungu = styled(Select)`
  @media screen and (max-width: 740px) {
    transform: translateX(35px);
    margin-top: 10px;
    white-space: pre-wrap;
  }
`;

function OpenStudyView() {
  const [inputs, setInputs] = useState({
    studyType: 'online',
    sido: '',
    gun: '',
    gu: '',
    studyList: '종류1',
    callDataCount: 4,
  });

  const onChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const { name, value } = e.target;
    setInputs({
      ...inputs,
      [name]: name === 'callDataCount' ? parseInt(value) : value,
    });
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
              position: 'sticky',
              top: '120px',
              backgroundColor: 'white',
              // '"1"' 형식은 'ZIndex | undefined' 형식에 할당할 수 없습니다.ts(2322)
              // @ts-ignore
              zIndex: '10',
            }}
          >
            <div>
              <span
                style={{
                  display: 'inline-block',
                  lineHeight: '40px',
                  marginRight: '30px',
                  marginLeft: '35px',
                  padding: '0px 10px',
                  borderRadius: '5px',
                  backgroundColor: '#51aafe',
                }}
              >
                전체
              </span>

              <Select id="type-select" name="studyType" onChange={onChange}>
                <option value="online">온라인</option>
                {/* 오프라인은 배포 이후 지원 */}
                {/* <option value="offline">오프라인</option> */}
              </Select>
              <Select id="study-select" name="studyList" onChange={onChange}>
                <option value="FREE">무료</option>
                <option value="PAY">유료</option>
              </Select>
              <Select id="call-data-count-select" name="callDataCount" onChange={onChange}>
                <option value={4}>4개</option>
                <option value={8}>8개</option>
                <option value={16}>16개</option>
              </Select>
              {inputs.studyType === 'offline' && (
                <>
                  {/* innerwidth는 한번만 실행되므로 차후에 변경 예정 */}
                  {/* 오프라인은 배포 이후 지원 */}
                  {/* {window.innerWidth < 740 && <br />}
                  <SidoGungu id="city-select" name="sido" onChange={onChange}>
                    <option value="sido1">시도1</option>
                    <option value="sido2">시도2</option>
                  </SidoGungu>
                  <SidoGungu id="city-select" name="gun" onChange={onChange}>
                    <option value="gun1">군1</option>
                    <option value="gun2">군2</option>
                  </SidoGungu>
                  <SidoGungu id="city-select" name="gu" onChange={onChange}>
                    <option value="gu1">구1</option>
                    <option value="gu2">구2</option>
                  </SidoGungu> */}
                </>
              )}
            </div>
            <Icon>
              <SearchIcon style={{ fontSize: '45px', marginRight: '25px' }} />
            </Icon>
          </div>
          <ItemList>
            <Items callDataCount={inputs.callDataCount} />
          </ItemList>
        </Section>
      </Main>
      <StudyFooter />
    </>
  );
}

export default OpenStudyView;
