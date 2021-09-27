import React, { useState, useEffect } from 'react';
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
  const [subjects, setSubjects] = useState([]);
  const [inputs, setInputs] = useState({
    title: '',
    studyType: 'FREE',
    maxMember: 2,
    studyInstanceType: 'ONLINE',
    subject: {
      id: 1,
      subjectName: '자바',
    },
    contents: '',
    startDate: moment().format('YYYY-MM-DD'),
    endDate: '',
    files: [null],
    sido: '',
    gun: '',
    gu: '',
  });

  const {
    title,
    studyType,
    maxMember,
    studyInstanceType,
    subject,
    contents,
    startDate,
    endDate,
    files,
    sido,
    gun,
    gu,
  } = inputs;

  const onChange = (e: React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>) => {
    const { name, value } = e.target;
    console.log(`name ${name}`);
    console.log(`value ${value}`);

    if (name !== 'subject') {
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
        subject: {
          id: parseInt(idx[0].id),
          subjectName: value,
        },
      });
    }
  };

  const getSubject = async () => {
    const {
      data: { data },
    } = await getSubjects();
    setSubjects(data);
  };

  useEffect(() => {
    getSubject();
  }, []);

  const checkInouts = () => {
    if (title !== '' && contents !== '' && endDate !== '') {
      if (studyInstanceType === 'ONLINE') {
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

  const onSaveStudy = async () => {
    try {
      if (checkInouts()) {
        alert('스터디 생성 중');

        const study: StudyType = {
          title,
          studyType,
          maxMember,
          studyInstanceType,
          subject: {
            id: subject.id,
            subjectName: subject.subjectName,
          },
          contents,
          startDate,
          endDate,
          files,
          address: {
            address1: sido,
            address2: gun,
            address3: gu,
          },
        };

        // console.log(study);

        const res = await saveStudty(study);

        console.log(res);

        if (res.status === 200) {
          alert('스터디 등록 성공!');
        } else {
          alert('뭔지 모를 에러로 인해 실패');
        }
      }
    } catch (err: any) {
      const res = err.response;

      alert(`스터디 등록 중 에러 발생! ${res.data.message}`);
    }
  };

  return (
    <>
      <StudyHeader>스터디 등록</StudyHeader>
      <Main>
        <Section>
          <WrapInput>
            <InputTitle htmlFor="title">제목</InputTitle>
            <Input id="title" name="title" type="text" onChange={onChange} value={title} placeholder="제목" />
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="study-list-select">스터디 종류</InputTitle>
            <Select id="study-list-select" name="studyInstanceType" value={studyInstanceType} onChange={onChange}>
              <option value="FREE">무료</option>
              <option value="PAY">유료</option>
            </Select>
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="max-user">최대 인원 수</InputTitle>
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
            <InputTitle htmlFor="study-type">스터디 타입</InputTitle>
            <Select id="type-select" name="studyType" value={studyType} onChange={onChange}>
              <option value="ONLINE">온라인</option>
              {/* 오프라인은 배포 이후 지원 */}
              {/* <option value="OFFLINE">오프라인</option> */}
            </Select>
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="study-type">언어</InputTitle>
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
            <InputTitle htmlFor="contents" style={{ top: '-20px' }}>
              내용
            </InputTitle>
            <Textarea name="contents" id="contents" value={contents} placeholder="내용" onChange={onChange}></Textarea>
          </WrapInput>
          <WrapInput style={{ display: 'flex' }}>
            <WrapInput style={{ width: '50%' }}>
              <InputTitle htmlFor="startDate">시작</InputTitle>
              <Input name="startDate" id="startDate" type="date" value={startDate} onChange={onChange}></Input>
            </WrapInput>
            <div style={{ margin: '0px 6%' }}></div>
            <WrapInput style={{ width: '50%' }}>
              <InputTitle htmlFor="endDate">종료</InputTitle>
              <Input name="endDate" id="endDate" type="date" value={endDate} onChange={onChange}></Input>
            </WrapInput>
          </WrapInput>
          <WrapInput>
            <InputTitle htmlFor="image">대표 이미지</InputTitle>
            <Input id="files" name="files" type="file" onChange={onChange} style={{ border: 'none', padding: '0px' }} />
          </WrapInput>
          <Button
            onClick={onSaveStudy}
            style={{
              width: '100%',
              marginTop: '20px',
              backgroundColor: '#51aafe',
            }}
          >
            스터디 등록
          </Button>
        </Section>
      </Main>
    </>
  );
}

export default CreateStudyView;
