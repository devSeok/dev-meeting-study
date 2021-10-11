import React, { useState, useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import { getStudty, FilterType } from '../API/index';
import { Item } from '../views/MainView';

// 나중에 interface 파일 만들어서 빼기
interface PropsData {
  inputs: {
    studyInstanceType: string;
    address1: string | null;
    lastId: number | null;
    studyType: string;
    sorted: string;
    subjectId: number | null;
    title: string | null;
    offset: number;
  };
  modalStateChange: (study: ItemsType) => void;
}
export interface ItemsType {
  createdDate: string;
  dtype: string;
  endDate: string;
  files: {
    id: number;
    name: string;
    path: string;
  }[];
  id: number;
  lastUpdateDate: string;
  maxMember: number;
  offline?: {
    id: number;
    address: {
      address1: string;
      address2: string;
      address3: string;
      id: number;
    };
  };
  online?: {
    id: number;
    link: string;
    onlineType: string;
  };
  startDate: string;
  studyMembers: {
    id: number;
    member: {
      email: string;
      grade: number;
      id: number;
      nickname: string;
    };
    studyAuth: string;
    studyStatus: string;
  }[];
  studyType: string;
  subject: {
    id: number;
    name: string;
  };
  title: string;
}

function Items({ inputs, modalStateChange }: PropsData) {
  const [items, setItems] = useState<ItemsType[]>([]);
  const [page, setPage] = useState(4);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  // 서버에서 가져오는 데이터
  const getItems = async (filter: FilterType) => {
    try {
      setLoading(true);

      const {
        data: { data },
      } = await getStudty(filter);

      setItems([...items, ...data]);

      setLoading(false);
    } catch (err) {
      alert('스터디 리스트 가져오기 실패');
    }
  };

  useEffect(() => {
    getItems(inputs);
  }, [inputs]);

  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니라면
    if (inView && !loading) {
      getItems(inputs);

      setPage((prevState) => prevState + inputs.offset);
    }
  }, [inView, loading]);

  // 로딩 중 나오는 로딩 데이터
  const loadingDatas = [...Array(inputs.offset)].map((item, index) => {
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
              <Item key={item.id} ref={ref} onClick={() => modalStateChange(item)}>
                <img src={item.files[0].path} alt="스터디 사진" />
                <div
                  style={{
                    position: 'absolute',
                    left: '20px',
                    bottom: '10px',
                  }}
                >
                  <h2 style={{ marginBottom: '10px' }}>{item.title}</h2>
                  <span>{item.studyType}</span>
                </div>
              </Item>
            ) : (
              <Item key={item.id} onClick={() => modalStateChange(item)}>
                <img src={item.files[0].path} alt="스터디 사진" />
                <div
                  style={{
                    position: 'absolute',
                    left: '20px',
                    bottom: '10px',
                  }}
                >
                  <span style={{ fontWeight: 'bold' }}>{item.dtype}</span>
                  <h2 style={{ marginTop: '10px' }}>{item.title}</h2>
                  {/* <span>{item.studyType}</span> */}
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
