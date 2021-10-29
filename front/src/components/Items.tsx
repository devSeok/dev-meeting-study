import React, { useState, useEffect } from 'react';
import { useInView } from 'react-intersection-observer';
import { getStudty, FilterType } from '../API/index';
import { initalStudy, Item } from '../views/MainView';

// 나중에 interface 파일 만들어서 빼기
interface PropsData {
  inputs: {
    dtype: string;
    address1: string | null;
    lastId: number | null;
    studyType: string;
    sorted: string;
    subjectId: number | null;
    title: string | null;
    offset: number;
  };
  modalStateChange: (open: boolean, study: ItemsType, del: boolean) => void;
  modal: {
    open: boolean;
    study: ItemsType;
    del: boolean;
  };
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

function Items({ inputs, modalStateChange, modal }: PropsData) {
  const [items, setItems] = useState<ItemsType[]>([]);
  const [lastItem, setlastItem] = useState(false);
  const [page, setPage] = useState(inputs.offset);
  const [loading, setLoading] = useState(false);
  const [ref, inView] = useInView();

  // 서버에서 가져오는 데이터
  const getItems = async (filter: FilterType, changeInputs: boolean) => {
    try {
      setLoading(true);

      // state 상태 업데이트 안 돼서
      // 임시로 사용
      let pages = page;

      if (changeInputs) {
        pages = 0;
        setPage((page) => page + inputs.offset);
        setlastItem(false);
      }

      const param = {
        ...filter,
        lastId: items[pages - 1] ? items[pages - 1].id : null,
      };

      const {
        data: { data },
      } = await getStudty(param);

      if (changeInputs) {
        setItems([...data]);
      } else {
        setItems([...items, ...data]);
      }
      if (data.length < inputs.offset) {
        setlastItem(true);
      }
      setLoading(false);
    } catch (err: any) {
      console.log(err);

      alert('스터디 리스트 가져오기 실패');
    }
  };

  useEffect(() => {
    if (inputs) {
      setPage((page) => page * 0);
      getItems(inputs, true);
    } else {
      getItems(inputs, false);
      console.log('useEffect', inputs);
    }
    if (modal.del) {
      modalStateChange(false, { ...initalStudy }, false);
    }
  }, [inputs, modal.del]);

  useEffect(() => {
    // 사용자가 마지막 요소를 보고 있고, 로딩 중이 아니고, 마지막 아이템이 아니면
    if (inView && !loading && !lastItem) {
      setPage((prevState) => prevState + inputs.offset);

      getItems(inputs, false);
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
      {items.length === 0 && !loading ? (
        <h1>데이터가 없어요 🤦‍♂️</h1>
      ) : (
        <>
          {items.map((item: ItemsType, index: number) => {
            return (
              <React.Fragment key={index}>
                {items.length - 1 === index ? (
                  <Item key={item.id} ref={ref} onClick={() => modalStateChange(true, item, false)}>
                    <img src={item.files[0].path} alt="스터디 사진" />
                    <div
                      style={{
                        position: 'absolute',
                        left: '20px',
                        bottom: '10px',
                      }}
                    >
                      {/* <h1>InView</h1> */}
                      <span style={{ fontWeight: 'bold' }}>{item.dtype}</span>
                      <h2 style={{ marginBottom: '10px' }}>{item.title}</h2>
                      {/* <span>{item.studyType}</span> */}
                    </div>
                  </Item>
                ) : (
                  <Item key={item.id} onClick={() => modalStateChange(true, item, false)}>
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
              </React.Fragment>
            );
          })}
        </>
      )}
      {loading && loadingDatas}
    </>
  );
}

export default Items;
