import styled from 'styled-components';

export const Main = styled.main`
  height: calc(100vh - 70px);
  width: 100%;
  display: flex;
  justify-content: center;
  align-items: center;
  @media screen and (max-width: 992px) {
    & {
      margin-top: 100px;
    }
  }
`;
