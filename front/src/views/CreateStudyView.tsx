import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
import moment from 'moment';
import queryString from 'query-string';
import { getSubjects, saveStudty, modifyStudy } from '../API/index';
import { StudyType } from '../ToolKit/userType';
import { Main, Section, InputWrap, Input, InputTitle, Button } from '../elements';
import StudyHeader from '../components/StudyHeader';

const Select = styled.select`
  width: 100%;
  height: 43px;
  margin-top: 13px;
  padding: 4px 10px;
  border: 1px solid #000;
  border-radius: 5px;
  background-color: #fff;
  outline: none;
`;

const WrapInput = styled(InputWrap)`
  width: 100%;
`;

const SelectOffline = styled(Select)`
  width: 32%;
`;

const Textarea = styled.textarea`
  min-width: 100%;
  max-width: 100%;
  min-height: 200px;
  max-height: 200px;
  border-radius: 5px;
  font-size: 20px;
`;

function CreateStudyView() {
  const history = useHistory();
  const [pathName, setPathName] = useState('create');
  const [studyId, setStudyId] = useState(1);
  const [subjects, setSubjects] = useState([]);
  const [inputs, setInputs] = useState({
    title: '',
    studyType: 'FREE',
    maxMember: 2,
    dtype: 'ONLINE',
    onlineId: 0,
    offlineId: 0,
    onlineType: '',
    link: '',
    subjectName: '',
    subjectId: 1,
    content: '',
    startDate: moment().format('YYYY-MM-DD'),
    endDate: '',
    file: {
      id: 0,
      name: '',
      path: '',
    },
    studyFileId: 0,
    sido: '',
    gun: '',
    gu: '',
  });

  const {
    title, // 타이틀
    studyType, // 유무료
    maxMember, // 최대 멤버 수
    dtype, // 온, 오프라인
    onlineId, // 온라인 id
    offlineId, // 오프라인 id
    onlineType, // 프로그램
    link, // 링크
    subjectName, // 언어 이름
    subjectId, // 언어 id
    content, // 내용
    startDate, // 시작 일
    endDate, // 종료 일
    file, // 사진
    studyFileId, // 파일 Id
    sido, // 시도
    gun, // 군
    gu, // 구
  } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;

    console.log('name', name);
    console.log('value', value);
    console.log('inputs', inputs);

    if (name === 'file') {
      setInputs({
        ...inputs,
        // @ts-ignore
        file: e.target.files[0],
      });
    } else if (name === 'dtype') {
      console.log('dtype', dtype);

      if (value === 'ONLINE') {
        setInputs({
          ...inputs,
          dtype: value,
          onlineType: '',
          link: '',
        });
      } else {
        setInputs({
          ...inputs,
          dtype: value,
        });
      }
    } else if (name !== 'subjectName') {
      setInputs({
        ...inputs,
        [name]: value,
      });
    } else {
      const options = Array.from(e.target.children);

      const idx = options.filter((item: Element) => {
        if (item.innerHTML === value) {
          return item;
        }
      });

      console.log('idx', idx);

      setInputs({
        ...inputs,
        subjectId: parseInt(idx[0].id),
        subjectName: idx[0].innerHTML,
      });
    }
  };

  useEffect(() => {
    // body style hidden 없애기
    document.body.style.overflow = 'initial';
  }, []);

  useEffect(() => {
    const getSubject = async () => {
      const {
        data: { data },
      } = await getSubjects();
      setSubjects(data);
    };
    getSubject();
  }, []);

  useEffect(() => {
    interface QueryStringType {
      id: string;
      createdDate: string;
      dtype: string;
      endDate: string;
      files: string;
      studyFileId: string;
      lastUpdateDate: string;
      maxMember: string;
      offline: string;
      online: string;
      onlineId: string;
      startDate: string;
      studyMembers: string;
      studyType: string;
      subject: string;
      title: string;
      content: string;
    }

    const slicePathName = history.location.pathname.slice(7, 13);
    setPathName(slicePathName);

    if (slicePathName === 'modify') {
      // @ts-ignore
      const query: QueryStringType = queryString.parse(history.location.pathname);

      const obj = {
        id: query.id,
        title: query.title,
        studyType: query.studyType,
        maxMember: parseInt(query.maxMember),
        dtype: query.dtype,
        onlineType: queryString.parse(query.online).onlineType as string,
        link: queryString.parse(query.online).link as string,
        offline: queryString.parse(query.offline).null as string,
        onlineId: parseInt(queryString.parse(query.online).id as string),
        subjectName: queryString.parse(query.subject).name as string,
        subjectId: parseInt(queryString.parse(query.subject).id as string),
        content: query.content,
        startDate: query.startDate,
        endDate: query.endDate,
        file: {
          id: parseInt(queryString.parse(query.files).id as string),
          name: queryString.parse(query.files).name as string,
          path: queryString.parse(query.files).path as string,
        },
        studyFileId: parseInt(queryString.parse(query.files).id as string),
        // sido,
        // gun,
        // gu,
      };

      setStudyId(parseInt(query.id as string));
      setInputs({
        ...inputs,
        ...obj,
      });
    }
  }, []);

  const checkInputs = () => {
    if (title !== '' && endDate !== '') {
      if (dtype === 'ONLINE') {
        return true;
      } else {
        if (sido !== '' && gun !== '' && gu !== '') {
          alert('시도, 군, 구를 입력해주세요.');
          return false;
        } else {
          return true;
        }
      }
    } else {
      alert('모든 입력 칸을 입력해주세요.');
      return false;
    }
  };

  const onSaveStudy = async (e: any) => {
    try {
      console.log('저장');
      e.preventDefault();
      if (checkInputs()) {
        console.log('true');

        let formData = new FormData();

        console.log('inputs', inputs);

        const id = 1;

        formData.append('addressId', id.toString());
        formData.append('title', title);
        formData.append('studyType', studyType);
        formData.append('maxMember', maxMember.toString());
        formData.append('dtype', dtype);
        formData.append('onlineType', onlineType);
        formData.append('link', link);
        formData.append('subjectId', subjectId.toString());
        formData.append('content', content);
        formData.append('startDate', startDate);
        formData.append('endDate', endDate);
        formData.append('studyFileId', studyFileId.toString());

        // @ts-ignore
        formData.append('file', file);

        console.log('file', file);

        dtype === 'ONLINE'
          ? formData.append('onlineId', onlineId.toString())
          : formData.append('offlineId', offlineId.toString());
        if (pathName === 'create') {
          console.log('create');
          // @ts-ignore
          await saveStudty(formData);
          alert('스터디 등록 성공!');
        } else {
          console.log('modify');
          console.log('file', file);

          // @ts-ignore
          await modifyStudy(studyId, formData);
          alert('스터디 수정 성공!');
        }
      }
      history.push('/');
      console.log('false');
    } catch (err: any) {
      const res = err.response;

      // if (res.data !== undefined) {
      //   alert(`${res.data.message}`);
      // } else {
      //   alert('에러 발생!');
      // }
      alert('에러 발생!');
    }
  };

  return (
    <>
      <StudyHeader>{pathName === 'create' ? '스터디 생성' : '스터디 수정'}</StudyHeader>
      <Main>
        <Section>
          <form id="myForm" name="myForm" style={{ width: '100%' }}>
            <WrapInput>
              <InputTitle htmlFor="title" className="required">
                제목
              </InputTitle>
              <Input id="title" name="title" type="text" onChange={onChange} value={title} placeholder="제목" />
            </WrapInput>
            <WrapInput>
              <InputTitle htmlFor="study-list-select" className="required">
                스터디 종류
              </InputTitle>
              <Select id="study-list-select" name="studyType" value={studyType} onChange={onChange}>
                <option value="FREE">무료</option>
                <option value="PAY">유료</option>
              </Select>
            </WrapInput>
            <WrapInput>
              <InputTitle htmlFor="max-user" className="required">
                최대 인원 수
              </InputTitle>
              <Input
                id="max-user"
                name="maxMember"
                type="number"
                onChange={onChange}
                value={maxMember}
                placeholder="최대 인원 수"
              />
            </WrapInput>
            <WrapInput>
              <InputTitle htmlFor="study-type" className="required">
                스터디 타입
              </InputTitle>
              <Select id="type-select" name="dtype" value={dtype} onChange={onChange}>
                <option value="ONLINE">온라인</option>
                {/* 오프라인은 배포 이후 지원 */}
                <option value="OFFLINE">오프라인</option>
              </Select>
            </WrapInput>
            {dtype === 'ONLINE' && (
              <WrapInput style={{ height: '130px' }}>
                <InputTitle htmlFor="study-type">온라인</InputTitle>
                <Input
                  id="online-type"
                  name="onlineType"
                  type="text"
                  onChange={onChange}
                  value={onlineType}
                  placeholder="프로그램"
                />
                <Input
                  id="link"
                  name="link"
                  type="text"
                  onChange={onChange}
                  value={link}
                  placeholder="링크"
                  style={{ top: '75px' }}
                />
              </WrapInput>
            )}
            <WrapInput>
              <InputTitle htmlFor="study-type" className="required">
                언어
              </InputTitle>
              <Select id="subject-select" name="subjectName" value={subjectName} onChange={onChange}>
                {subjects.map((item: { id: number; name: string }) => (
                  <option key={item.id} id={item.id.toString()} value={item.name}>
                    {item.name}
                  </option>
                ))}
              </Select>
            </WrapInput>
            {inputs.studyType === 'OFFLINE' && (
              // 오프라인은 배포 이후 지원
              <WrapInput>
                {/* 오프라인은 배포 이후 지원 */}
                {/* <InputTitle>오프라인 장소</InputTitle>
              <SelectOffline id="si-select" name="sido" onChange={onChange}>
                <option value="si">시</option>
                <option value="sis">시s</option>
              </SelectOffline>
              <SelectOffline id="gun-select" name="gun" onChange={onChange} style={{ margin: '0px 14.8px' }}>
                <option value="gun">군</option>
                <option value="guns">군s</option>
              </SelectOffline>
              <SelectOffline id="gu-select" name="gu" onChange={onChange}>
                <option value="gu">구</option>
                <option value="gus">구s</option>
              </SelectOffline> */}
              </WrapInput>
            )}
            <WrapInput style={{ height: '210px' }}>
              <InputTitle htmlFor="contents" style={{ top: '-22px' }} className="required">
                내용
              </InputTitle>
              <Textarea name="content" id="content" value={content} placeholder="내용" onChange={onChange}></Textarea>
            </WrapInput>
            <WrapInput style={{ display: 'flex' }}>
              <WrapInput style={{ width: '50%' }}>
                <InputTitle htmlFor="startDate" className="required">
                  시작
                </InputTitle>
                <Input name="startDate" id="startDate" type="date" value={startDate} onChange={onChange}></Input>
              </WrapInput>
              <div style={{ margin: '0px 6%' }}></div>
              <WrapInput style={{ width: '50%' }}>
                <InputTitle htmlFor="endDate" className="required">
                  종료
                </InputTitle>
                <Input name="endDate" id="endDate" type="date" value={endDate} onChange={onChange}></Input>
              </WrapInput>
            </WrapInput>
            <WrapInput>
              <InputTitle htmlFor="image">대표 이미지</InputTitle>
              <Input id="file" name="file" type="file" onChange={onChange} style={{ border: 'none', padding: '0px' }} />
            </WrapInput>
            <Button
              onClick={onSaveStudy}
              style={{
                width: '100%',
                marginTop: '20px',
                backgroundColor: '#51aafe',
              }}
              type="submit"
            >
              {pathName === 'create' ? '스터디 등록' : '스터디 수정'}
            </Button>
          </form>
        </Section>
      </Main>
    </>
  );
}

export default CreateStudyView;
