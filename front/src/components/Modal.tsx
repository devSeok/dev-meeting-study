import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import { delStudy, studyInfo as studyInfoFunc } from '../API/index';
import { Icon } from '../elements/index';
import { ItemsType } from './Items';
import { initalStudy } from '../views/MainView';
import CloseIcon from '@mui/icons-material/Close';

const ModalWrap = styled.div`
  width: 100%;
  height: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  z-index: 100;
`;

const StudyModal = styled.div`
  width: 500px;
  height: 500px;
`;

const ModalTop = styled.div`
  height: 200px;
  border-radius: 10px;
  background-color: #ffffff;
  position: relative;
  overflow: hidden;
  & > div {
    width: 90%;
    position: absolute;
    top: 30px;
    left: 30px;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  & > div > h2 {
    font-weight: bold;
  }

  & > .leader {
    position: absolute;
    bottom: 30px;
    left: 30px;
    display: flex;
    align-items: flex-end;
    font-size: 20px;
    font-weight: bold;
  }

  & > img {
    width: 100%;
    height: 100%;
  }
`;
const ModalBottom = styled.div`
  padding: 30px 40px;
  border-radius: 10px;
  background-color: lightgray;
`;

const MenuList = styled.ul`
  display: flex;
  justify-content: space-between;
  border-bottom: 1px solid black;
`;

const MemuItem = styled.li`
  display: flex;
  flex-direction: column;
  align-items: center;

  & > span:nth-child(2) {
    padding: 20px 10px 10px;
  }
`;

interface PropsType {
  study: ItemsType;
  modalStateChange: (open: boolean, study: ItemsType, del: boolean) => void;
}

function Modal({ study, modalStateChange }: PropsType) {
  const [studyInfo, setstudyInfo] = useState({});

  console.log('study', study);

  useEffect(() => {
    const infoStudy = async (studyId: number) => {
      const {
        data: { data },
      } = await studyInfoFunc(studyId);
      setstudyInfo(data);
    };

    infoStudy(study.id);
  }, []);

  const checkNull = (obj: object) => {
    if (obj === null) {
      return { null: null };
    }
    return obj;
  };

  const makeObjectQueryString = (obj: any | null) => {
    let url = '';

    const checkObj = checkNull(obj as object);

    for (let prop in checkObj) {
      // @ts-ignore
      url += `${prop}=${checkObj[prop]}&`;
    }

    return url;
  };

  const makeQueryString = (study: ItemsType) => {
    let url = '/study/modify/type=modify&';
    for (let prop in studyInfo) {
      if (prop === 'studyMembers') {
        continue;
      }
      // @ts-ignore
      url = url += `${prop}=${
        // @ts-ignore
        typeof study[prop] === 'object'
          ? // @ts-ignore
            encodeURIComponent(makeObjectQueryString(prop === 'files' ? studyInfo[prop][0] : studyInfo[prop]))
          : // @ts-ignore
            encodeURIComponent(studyInfo[prop])
      }&`;
    }

    // 마지막 & 제거
    url = url.substr(0, url.length - 1);

    console.log('url', url);
    return url;
  };

  interface MemberType {
    id: number;
    member: {
      email: string;
      grade: number;
      id: number;
      nickname: string;
    };
    studyAuth: string;
    studyStatus: string;
  }

  const leader: MemberType = study.studyMembers.filter((leader: MemberType) => leader.studyAuth === 'LEADER')[0];

  const sliceDate = (start: string, end: string) => {
    const startYear = start.slice(0, 4);
    const startMonth = start.slice(5, 7);
    const endYear = end.slice(0, 4);
    const endMonth = end.slice(5, 7);

    if (startYear === endYear) {
      if (startMonth === endMonth) {
        return `${start.slice(5)} ~ ${end.slice(8)}`;
      } else {
        return `${start.slice(5)} ~ ${end.slice(5)}`;
      }
    } else {
      return `${start} ~ ${end}`;
    }
  };

  const deleteStudy = async () => {
    try {
      console.log('l', study);

      const res = await delStudy(leader.member.id, leader.member.nickname, study.id);

      if (res.status === 204) {
        alert('삭제 성공!');
        modalStateChange(false, { ...initalStudy }, true);
      }
    } catch (err: any) {
      console.log('err', err);
      const error = err.response.data;

      if (error) {
        alert(error.message);
      } else {
        alert('서버 에러 발생');
      }
    }
  };

  return (
    <ModalWrap>
      <StudyModal>
        <ModalTop>
          <div>
            <h2 className="studyTitle">{study.title}</h2>
            <Icon onClick={() => modalStateChange(false, { ...initalStudy }, false)}>
              <CloseIcon />
            </Icon>
          </div>
          <div className="leader">
            <span>{leader.member.nickname}</span>
            <button type="button" onClick={deleteStudy}>
              삭제
            </button>
            <Link to={() => makeQueryString(study)}>수정</Link>
          </div>
          <img src={study.files[0].path} alt="스터디 사진" />
        </ModalTop>
        <ModalBottom>
          <MenuList>
            <MemuItem>
              <span>인원 수</span>
              <span>
                {study.studyMembers.length} / {study.maxMember}
              </span>
            </MemuItem>
            <MemuItem>
              <span>언어</span>
              <span>{study.subject.name}</span>
            </MemuItem>
            <MemuItem>
              <span>기간</span>
              <span>{sliceDate(study.startDate, study.endDate)}</span>
            </MemuItem>
          </MenuList>
          <div>
            <span>{/* {study.} */}</span>
          </div>
        </ModalBottom>
      </StudyModal>
    </ModalWrap>
  );
}

export default Modal;
