import styled from 'styled-components';

export const Section = styled.section`
  width: 740px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  @media screen and (max-width: 740px) {
    overflow-y: scroll;
    margin-bottom: 100px;
  }
`;
