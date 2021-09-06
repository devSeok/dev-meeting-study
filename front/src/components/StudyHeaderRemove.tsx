import React from 'react';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import logo from '../asset/image/logo_out_book.png';

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
  position: relative;
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

const MenuItemParent = styled.li`
  cursor: pointer;
  line-height: 100px;

  &:hover {
    background-color: #98bcda;
    opacity: 0.5;
  }

  @media screen and (max-width: 992px) {
    & {
      text-align: center;
      padding: 0.5em 0;
      width: 100%;
    }
  }
`;

const MenuItem = styled(MenuItemParent)`
  width: 350px;
  height: 100dpx;
  text-align: center;
  background-color: white;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
  border: 1px solid black;
`;
const MenuUser = styled(MenuItemParent)`
  margin: 3px;
  padding-left: 5px;
  padding-right: 5px;
`;

function StudyHeader() {
  return (
    <HeaderWrap>
      <MenuList>
        <Logo>
          <p>
            <Link to="/">
              <img src={logo} alt="Logo" style={{ width: '100px' }} />
            </Link>
          </p>
        </Logo>
        {/* 로그인 위치 수정 예정 */}
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
        <MenuUser>
          <Link to="/login" style={{ width: '100%', height: '100%', display: 'inline-block' }}>
            로그인
          </Link>
        </MenuUser>
        <MenuUser>
          <Link to="/register" style={{ width: '100%', height: '100%', display: 'inline-block' }}>
            회원가입
          </Link>
        </MenuUser>
      </MenuList>
    </HeaderWrap>
  );
}

export default StudyHeader;
