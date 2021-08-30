import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';

const HeaderWrap = styled.header`
  position: relative;
  z-index: 0;

  @media screen and (max-width: 992px) {
    & {
      margin: 0;
    }
  }
`;

const Logo = styled.div`
  position: absolute;
  top: 0;
  left: 5%;
  z-index: 1;
  font-size: 2em;

  @media screen and (max-width: 992px) {
    & {
      display: none;
    }
  }
`;

const MenuList = styled.ul`
  display: flex;
  justify-content: center;
  align-items: stretch;
  flex-wrap: wrap;
  flex-direction: row;
  margin: 1em auto;
  width: calc(100% - 2px);
`;

const MenuItem = styled.li`
  width: 350px;
  height: 35px;
  text-align: center;
  line-height: 35px;
  background-color: white;
  border: 1px solid black;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
  cursor: pointer;

  &:hover {
    background-color: #98bcda;
    opcity: 0.8;
  }

  @media screen and (max-width: 992px) {
    & {
      text-align: center;
      padding: 0.5em 0;
      width: 100%;
    }
  }
`;

function StudyHeader() {
  return (
    <HeaderWrap>
      <Logo>
        <p>
          <Link to="/">#</Link>
        </p>
      </Logo>
      <MenuList>
        <MenuItem>
          <Link to="/login" style={{ width: '100%', height: '100%', display: 'inline-block' }}>
            로그인
          </Link>
        </MenuItem>
        <MenuItem>
          <Link to="/register" style={{ width: '100%', height: '100%', display: 'inline-block' }}>
            회원가입
          </Link>
        </MenuItem>
        <MenuItem>
          <Link to="/study/create" style={{ width: '100%', height: '100%', display: 'inline-block' }}>
            스터디 등록
          </Link>
        </MenuItem>
        <MenuItem>
          <Link to="/my/info" style={{ width: '100%', height: '100%', display: 'inline-block' }}>
            내 스터디
          </Link>
        </MenuItem>
      </MenuList>
    </HeaderWrap>
  );
}

export default StudyHeader;
