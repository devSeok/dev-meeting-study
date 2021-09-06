import React from 'react';
import { Icon } from '../elements';
import ArrowForwardIosIcon from '@material-ui/icons/ArrowForwardIos';
import person from '../asset/image/person.png';



interface Study {
  title: string;
  type: string;
  maxUser: number;
  lang: string[];
}

interface Post {
  title: string;
  sub: string;
}

function StudyColumnList({ items, index }: any) {
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
            return (
              <li key={idx} style={{ margin: '20px 0px', cursor: 'pointer' }}>
                <div style={{ display: 'flex' }}>
                  <div style={{ paddingRight: '20px' }}>
                    <img src={person} alt="유저" />
                  </div>
                  <div>
                    <h2>{item.title}</h2>
                    <span>{item.sub}</span>
                  </div>
                </div>
              </li>
            );
          })}
      </ul>
    </div>
  );
}

export default StudyColumnList;
