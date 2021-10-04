import styled from 'styled-components';

export const TextAreaInput = styled.textarea`
  width: 100%;
  min-width: 100%;
  height: 300px;
  min-height: 300px;
  position: absolute;
  top: 15px;
  padding: 4px 10px;
  border: 1px solid #000;
  border-radius: 5px;
  font-size: 1em;
  outline: none;

  &.success {
    border: 1px solid blue;
  }

  &.failure {
    border: 1px solid red;
  }
`;
