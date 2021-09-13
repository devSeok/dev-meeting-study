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
  align-items: center;
`;

const RightMenu = styled.li`
  margin-left: auto;
`;

const MenuItem = styled.li`
  cursor: pointer;
  width: 100px;
  text-align: center;
  margin: 5px;
  padding: 15px;
  /* background-color: white;
  box-shadow: 5px 5px 5px rgba(0, 0, 0, 0.1);
  border: 1px solid black; */

  &:hover {
    background-color: #98bcda;
    opacity: 0.5;
  }
  @media screen and (max-width: 992px) {
    & {
      /* text-align: center;
      padding: 0.5em 0;
      width: 100%; */
      visibility: hidden;
    }
  }
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
        <RightMenu />
        <MenuItem>
          <Link to="/login">로그인</Link>
        </MenuItem>
        <MenuItem>
          <Link to="/register">회원가입</Link>
        </MenuItem>
      </MenuList>
    </HeaderWrap>
  );
}

export default StudyHeader;
