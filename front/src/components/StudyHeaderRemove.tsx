import React, { useEffect } from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { InitialState } from '../ToolKit/user';
import { Link } from 'react-router-dom';
import styled from 'styled-components';
import logo from '../asset/image/logo_out_book.png';
import { auth, logout } from '../ToolKit/user';

const HeaderWrap = styled.header`
  position: sticky;
  top: 20px;
  z-index: 0;
  @media screen and (max-width: 740px) {
    & {
      margin: 0;
    }
  }
`;

const Logo = styled.div`
  position: relative;
  z-index: 1;
  font-size: 2em;

  @media screen and (max-width: 740px) {
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
  display: inline-block;
  cursor: pointer;
  width: 100px;
  text-align: center;
  margin: 5px;
  padding: 15px;

  &:hover {
    background-color: #98bcda;
    opacity: 0.5;
  }
  @media screen and (max-width: 740px) {
    & {
      display: none;
    }
  }
`;

function StudyHeader() {
  const dispatch = useDispatch();
  const isAuth = useSelector(auth);

  const onLogout = () => {
    console.log('로그아웃');

    dispatch(logout());
  };

  console.log('isAuth', isAuth.success);

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
        {isAuth.success ? (
          <MenuItem onClick={onLogout}>로그아웃</MenuItem>
        ) : (
          <>
            <Link to="/login">
              <MenuItem>로그인</MenuItem>
            </Link>

            <Link to="/register">
              <MenuItem>회원가입</MenuItem>
            </Link>
          </>
        )}
      </MenuList>
    </HeaderWrap>
  );
}

export default StudyHeader;
