import React, { useState } from 'react';
import { Icon } from '../elements';
import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';
import person from '../asset/image/person.png';
import moment from 'moment';
import MessageContents from './MessageContents';

interface Study {
  title: string;
  type: string;
  maxUser: number;
  lang: string[];
  id: number;
}

interface Post {
  content: string;
  createdDate: string;
  delflg: string;
  id: number;
  lastUpdateDate: string;
  // member:
  // email: "test1@test.com"
  // grade: 0
  // id: 7
  // nickname: "jun"

  // sender:
  // email: "test1@test.com"
  // grade: 0
  // id: 7
  // nickname: "jun"
  // [[Prototype]]: Object
  // status: "NOT_READ"
}
interface ReadeonlyPost {}

function StudyColumnList({ items, index }: any) {
  const [message, setMessage] = useState<Post>();
  const [openMessage, setOpenMessage] = useState(false);
  const [clicked, setClicked] = useState(false);
  // console.log('List:items', items);
  // console.log('List:index', index);

  // 1. 파라미터 타입 바꾸기
  // 2. onClick 함수에서 처리하기

  const onClick = (data: Post) => {
    console.log('onClick!');
    setMessage(data);
    setOpenMessage(!openMessage);
  };

  const onDel = () => {
    console.log('삭제!');
  };

  // JSX
  return (
    <div style={{ width: '500px', justifyContent: 'flex-start' }}>
      <h3>총 {items.length}개</h3>
      <hr />
      <ul style={{ padding: '0px 10px' }}>
        {index === 1 &&
          items.map((item: Study, idx: number) => {
            return (
              <li key={idx} style={{ margin: '20px 0px', cursor: 'pointer' }}>
                <div className="study-top">
                  <div
                    style={{
                      display: 'flex',
                      justifyContent: 'space-between',
                      alignItems: 'center',
                    }}
                  >
                    <h2>
                      {item.title}{' '}
                      <span
                        style={{
                          fontSize: '18px',
                          fontWeight: 'lighter',
                        }}
                      >
                        - {item.type}
                      </span>
                    </h2>
                    <Icon>
                      <ArrowForwardIosIcon />
                    </Icon>
                  </div>
                  <div className="item-bottom" style={{ marginTop: '10px' }}>
                    <div>
                      <span>최대 참여 인원 : {item.maxUser}</span>
                    </div>
                    <div>
                      <span>언어 : {item.lang}</span>
                    </div>
                  </div>
                </div>
              </li>
            );
          })}
        {index === 2 &&
          items.map((item: Post, idx: number) => {
            const date = moment(item.createdDate).format('YYYY-MM-DD HH:mm:ss');
            const contents = item.content.substring(0, 40);
            // console.log(clicked);
            return (
              <>
                <li key={idx} style={{ margin: '20px 0px', cursor: 'pointer' }} onClick={() => onClick(item)}>
                  <div style={{ display: 'flex' }}>
                    <div style={{ paddingRight: '20px' }}>
                      <img src={person} alt="유저" />
                    </div>
                    <div>
                      <span>{date}</span>
                      <button onClick={onDel}>Del</button>
                      <h2>{contents}</h2>
                    </div>
                  </div>
                </li>
              </>
            );
          })}
        {openMessage && <span>TURE!</span>}
      </ul>
    </div>
  );
}

export default StudyColumnList;
