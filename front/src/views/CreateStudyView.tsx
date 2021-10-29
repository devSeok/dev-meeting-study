import React, { useState, useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
import moment from 'moment';
import { getSubjects, saveStudty } from '../API/index';
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
  const [subjects, setSubjects] = useState([]);
  const [inputs, setInputs] = useState({
    title: '',
    studyType: 'FREE',
    maxMember: 2,
    dtype: 'ONLINE',
    onlineType: '',
    link: '',
    subjectId: 1,
    content: '',
    startDate: moment().format('YYYY-MM-DD'),
    endDate: '',
    file: {},
    sido: '',
    gun: '',
    gu: '',
  });

  const {
    title, // 타이틀
    studyType, // 유무료
    maxMember, // 최대 멤버 수
    dtype, // 온, 오프라인
    onlineType, // 프로그램
    link, // 링크
    subjectId, // 언어 id
    content, // 내용
    startDate, // 시작 일
    endDate, // 종료 일
    file, // 사진
    sido, // 시도
    gun, // 군
    gu, // 구
  } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    // console.log(`name ${name}`);
    // console.log(`value ${value}`);

    if (name === 'file') {
      setInputs({
        ...inputs,
        // @ts-ignore
        file: e.target.files[0],
      });
    } else if (name !== 'subject') {
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
      });
    }
  };

  useEffect(() => {
    const getSubject = async () => {
      const {
        data: { data },
      } = await getSubjects();
      setSubjects(data);
    };
    getSubject();
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
      e.preventDefault();
      if (checkInputs()) {
        let formData = new FormData();
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
        //@ts-ignore
        formData.append('file', file);

        //@ts-ignore
        await saveStudty(formData);

        alert('스터디 등록 성공!');
        history.push('/');
      }
    } catch (err: any) {
      const res = err.response;

      alert(`스터디 등록 중 에러 발생!`);
    }
  };

  return (
    <>
      <StudyHeader>스터디 등록</StudyHeader>
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
                {/* <option value="OFFLINE">오프라인</option> */}
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
              <Select id="subject-select" name="subject" onChange={onChange}>
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
              스터디 등록
            </Button>
          </form>
        </Section>
      </Main>
    </>
  );
}

export default CreateStudyView;
