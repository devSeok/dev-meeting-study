import React, { useState, useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import { Item } from '../views/MainView';

interface PropsData {
  callDataCount: number;
}

interface ItemsType {
  id: number;
  title: string;
  type: string;
}
function Items({ callDataCount }: PropsData) {
  const [items, setItems] = useState<ItemsType[]>([
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
  ]);
  const [page, setPage] = useState(4);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  // 서버에서 가져오는 데이터
  const getItems = () => {
    const arr: ItemsType[] = [];
    for (let i = 1; i <= callDataCount; i++) {
      arr.push({ id: i, title: '자바 재미있어요', type: '온라인' });
    }
    setItems([...items, ...arr]);
  };

  // useEffect(() => {
  //   getItems();
  // }, [getItems]);

  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    if (inView && !loading) {
      setLoading(true);

      setTimeout(() => {
        getItems();
        setPage((prevState) => prevState + callDataCount);
        setLoading(false);
      }, 2000);
    }
  }, [inView, loading]);

  // 로딩 중 나오는 로딩 데이터
  const loadingDatas = [...Array(callDataCount)].map((item, index) => {
    return (
      <Item key={index}>
        <div
          style={{
            position: 'absolute',
            left: '20px',
            bottom: '10px',
          }}
        >
          <h2 style={{ marginBottom: '10px' }}>loading...</h2>
          <span>loading...</span>
        </div>
      </Item>
    );
  });

  return (
    <>
      {items.map((item: ItemsType, index: number) => {
        return (
          <>
            {items.length - 1 === index ? (
              <Item key={item.id} ref={ref}>
                <div
                  style={{
                    position: 'absolute',
                    left: '20px',
                    bottom: '10px',
                  }}
                >
                  <h2 style={{ marginBottom: '10px' }}>{item.title}</h2>
                  <span>{item.type}</span>
                </div>
              </Item>
            ) : (
              <Item key={item.id}>
                <div
                  style={{
                    position: 'absolute',
                    left: '20px',
                    bottom: '10px',
                  }}
                >
                  <h2 style={{ marginBottom: '10px' }}>{item.title}</h2>
                  <span>{item.type}</span>
                </div>
              </Item>
            )}
          </>
        );
      })}
      {loading && loadingDatas}
    </>
  );
}

export default Items;
