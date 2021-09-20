import React, { useState } from 'react';
import styled from 'styled-components';
import { Main, Section, Icon } from '../elements';
import SearchIcon from '@material-ui/icons/Search';
import StudyHeader from '../components/StudyHeaderRemove';
import StudyFooter from '../components/StudyFooter';
import Items from '../components/Items';
import Pagination from '../components/Pagination';

export const study = [
  {
    id: 1,
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    id: 2,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 3,
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    id: 4,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 5,
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    id: 6,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 7,
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    id: 8,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 9,
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    id: 10,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 11,
    title: '오픈 스터디',
    type: '온라인',
  },
  {
    id: 12,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 13,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 14,
    title: '오픈 스터디',
    type: '오프라인',
  },
  {
    id: 15,
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
  // const [item, setItems] = useState([]);
  // const [loading, setLoading] = useState(false); // 비동기 요소시에 loading 만들거 대비
  const [currentPage, setCurrentPage] = useState(1);
  const [postsPerPage] = useState(4);

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
  const indexOfLastPost = currentPage * postsPerPage; //페이지내 마지막 인덱스 값
  const indexOfFirstPost = indexOfLastPost - postsPerPage; //페이지내 첫번째 인덱스 값
  const currentPosts = study.slice(indexOfFirstPost, indexOfLastPost); // 페이지에 보여질 요소
  const paginate = (pageNumber: any) => setCurrentPage(pageNumber); //  page 번호 (누르면 들어가는 id 지정)

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
                <option value="offline">오프라인</option>
              </Select>
              <Select id="study-select" name="studyList" onChange={onChange}>
                <option value="1">종류1</option>
                <option value="2">종류2</option>
              </Select>

              {inputs.studyType === 'offline' && (
                <>
                  {/* innerwidth는 한번만 실행되므로 차후에 변경 예정 */}
                  {window.innerWidth < 740 && <br />}
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
                  </SidoGungu>
                </>
              )}
            </div>
            <Icon>
              <SearchIcon style={{ fontSize: '45px', marginRight: '25px' }} />
            </Icon>
          </div>
          <ItemList>
            <Items items={currentPosts} />
          </ItemList>
          <Pagination postsPerPage={postsPerPage} totalPosts={study.length} paginate={paginate} />
        </Section>
      </Main>
      <StudyFooter />
    </>
  );
}

export default OpenStudyView;
