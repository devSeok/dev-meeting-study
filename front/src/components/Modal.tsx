import React from 'react';
import styled from 'styled-components';
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
  modalStateChange: (study: ItemsType) => void;
}

function Modal({ study, modalStateChange }: PropsType) {
  console.log('study', study);

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

  return (
    <ModalWrap>
      <StudyModal>
        <ModalTop>
          <div>
            <h2 className="studyTitle">{study.title}</h2>
            <Icon onClick={() => modalStateChange({ ...initalStudy })}>
              <CloseIcon />
            </Icon>
          </div>
          <span className="leader">{leader.member.nickname}</span>
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
