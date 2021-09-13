import React from 'react';
import styled from 'styled-components';
import { Icon } from '../elements/Icon';
import PersonOutlineIcon from '@material-ui/icons/PersonOutline';
import BookmarkIcon from '@material-ui/icons/Bookmark';
import BookmarksIcon from '@material-ui/icons/Bookmarks';
import HomeIcon from '@material-ui/icons/Home';
import { Link } from 'react-router-dom';

interface StudyFooterProps {}

const FooterBox = styled.div`
  bottom: 0;
  position: fixed;
  background: #f6f6f6;
  width: 100%;
  display: flex;
  justify-content: space-around;
  align-items: center;
  opacity: 0.9;
`;

// eslint-disable-next-line no-empty-pattern
function StudyFooter({}: StudyFooterProps) {
  return (
    <>
      <FooterBox>
        <Icon>
          <Link to="/">
            <HomeIcon style={{ fontSize: 70 }} />
          </Link>
        </Icon>
        <Icon>
          <Link to="/my/info">
            <BookmarkIcon style={{ fontSize: 70 }} />
          </Link>
        </Icon>
        <Icon>
          <Link to="/study/create">
            <BookmarksIcon style={{ fontSize: 70 }} />
          </Link>
        </Icon>
        <Icon>
          <PersonOutlineIcon style={{ fontSize: 70 }} />
        </Icon>
      </FooterBox>
    </>
  );
}

export default StudyFooter;
