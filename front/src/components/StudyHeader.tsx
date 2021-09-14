import React from 'react';
import { useHistory } from 'react-router-dom';
import styled from 'styled-components';
import { Icon } from '../elements';
import ChevronLeftIcon from '@material-ui/icons/ChevronLeft';

const Header = styled.header`
  width: 100%;
  display: flex;
  justify-content: center;
  text-align: center;
  border-bottom: 1px solid #000;
  padding: 15px 0px;
  font-size: 25px;
  font-weight: bold;
  margin-bottom: 100px;
`;

// HeaderWidth
const HW740 = styled.div`
  width: 740px;
  display: flex;
  justify-content: space-between;
  align-items: center;
`;

function StudyHeader({ children }: any) {
  const history = useHistory();

  const onPushMainPage = () => {
    history.push('/');
  };
  return (
    <Header>
      <HW740>
        <Icon onClick={onPushMainPage}>
          <ChevronLeftIcon />
        </Icon>
        <h3 style={{ margin: '0', marginLeft: '-20px', fontWeight: 'lighter' }}>{children}</h3>
        <div></div>
      </HW740>
    </Header>
  );
}

export default StudyHeader;
