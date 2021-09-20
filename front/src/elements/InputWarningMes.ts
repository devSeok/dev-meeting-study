import styled from 'styled-components';

export const InputWarningMes = styled.span`
  position: absolute;
  bottom: 8px;
  display: none;
  font-size: 13px;
  color: red;

  &.failure {
    display: block;
  }
`;
