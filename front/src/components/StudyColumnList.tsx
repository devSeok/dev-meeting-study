import React, { useState, useRef } from 'react';
import styled from 'styled-components';
import { useDispatch } from 'react-redux';
import { Icon } from '../elements';
import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';
import person from '../asset/image/person.png';
import moment from 'moment';
import { deleteMessage } from '../ToolKit/messages';

interface Study {
  title: string;
  type: string;
  maxUser: number;
  lang: string[];
  id: number;
}
interface PropsType {
  items: any;
  index: number;
}

const Contents = styled.div`
  max-width: 390px;
  height: max-content;
  word-break: break-all;
  display: none;

  &.open {
    display: block;
  }
`;

function StudyColumnList({ items, index }: PropsType) {
  const Dispatch = useDispatch();
  const [message, setMessage] = useState();
  // console.log('List:items', items);
  // console.log('List:index', index);
  const itemRef = useRef<HTMLLIElement>(null);
  const contentMaxRef = useRef<HTMLDivElement>(null);
  const contentSliceRef = useRef<HTMLDivElement>(null);
  // 2. onClick 함수에서 처리하기
  const onClick = (data: any, idx: number, e: any) => {
    setMessage(data); // data
    const parent = e.currentTarget.childNodes[0].childNodes[1];
    const contentSlice = parent.childNodes[2];
    const contentMax = parent.childNodes[3];

    contentSlice.classList.toggle('open');
    contentMax.classList.toggle('open');
  };

  const onDelete = (id: any) => {
    console.log(id);
    Dispatch(deleteMessage(id));
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
          items.map((item: any, idx: number) => {
            const date = moment(item.createdDate).format('YYYY-MM-DD HH:mm:ss');
            const contents = item.content.substring(0, 40);
            const contentsMax = item.content;
            return (
              <>
                <li
                  key={idx}
                  ref={itemRef}
                  style={{ margin: '20px 0px', cursor: 'pointer' }}
                  onClick={(e) => onClick(item, idx, e)}
                >
                  <div style={{ display: 'flex' }}>
                    <div style={{ paddingRight: '20px' }}>
                      <img src={person} alt="유저" />
                    </div>
                    <div>
                      <span>{date}</span>
                      <button onClick={onDelete}>Del</button>
                      <Contents ref={contentSliceRef} className="open">
                        {contents}
                      </Contents>
                      <Contents ref={contentMaxRef}>{contentsMax}</Contents>
                    </div>
                  </div>
                </li>
              </>
            );
          })}
      </ul>
    </div>
  );
}

export default StudyColumnList;
