import React, { useState } from 'react';
import styled from 'styled-components';
import { Main, Section, Icon } from '../elements';
import StudyHeader from '../components/StudyHeaderRemove';
import SearchIcon from '@material-ui/icons/Search';
import StudyFooter from '../components/StudyFooter';
import Items from '../components/Items';
// import Pagination from '../components/Pagination';
// import axios from 'axios';

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
  // {
  //   id: 7,
  //   title: '오픈 스터디',
  //   type: '온라인',
  // },
  // {
  //   id: 8,
  //   title: '오픈 스터디',
  //   type: '오프라인',
  // },
  // {
  //   id: 9,
  //   title: '오픈 스터디',
  //   type: '온라인',
  // },
  // {
  //   id: 10,
  //   title: '오픈 스터디',
  //   type: '오프라인',
  // },
  // {
  //   id: 11,
  //   title: '오픈 스터디',
  //   type: '온라인',
  // },
  // {
  //   id: 12,
  //   title: '오픈 스터디',
  //   type: '오프라인',
  // },
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
`;

export const Item = styled.li`
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
  // const [posts, setPosts] = useState([]);
  // const [loading, setLoading] = useState(false);
  // const [currentPage, setCurrentPage] = useState(1);
  // const [postsPerPage, setPostsPerPage] = useState(4);

  // useEffect(() => {
  //   const fetchPosts = async () => {
  //     setLoading(true);
  //     const res = await axios.get(study); //json-server 안돌아가는거 알아봐야됌
  //   };
  // });

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
  // const indexOfLastPost = currentPage * postsPerPage;
  // const indexOfFirstPost = indexOfLastPost - postsPerPage;
  // const currentPosts = posts.slice(indexOfFirstPost, indexOfLastPost);
  // const paginate = (pageNumber: any) => setCurrentPage(pageNumber);

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
            <Items items={study} />
            {/* <Pagination postsPerPage={postsPerPage} totalPosts={posts.length} paginate={paginate} /> */}
          </ItemList>
        </Section>
      </Main>
      <StudyFooter />
    </>
  );
}

export default OpenStudyView;
