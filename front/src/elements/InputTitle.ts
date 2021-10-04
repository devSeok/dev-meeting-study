import styled from 'styled-components';

export const InputTitle = styled.label`
  position: absolute;
  top: -8px;
  left: 0px;
  font-weight: bold;

  &.required {
    &:after {
      content: '*';
      margin-left: 5px;
      color: red;
    }
  }
`;
