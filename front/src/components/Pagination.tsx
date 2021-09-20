import React from 'react';
import styled from 'styled-components';

interface PaginationProps {
  postsPerPage: number;
  totalPosts: number;
  paginate: (page: number) => void;
}

const PageNumberUl = styled.ul`
  float: left;
  list-style: none;
  text-align: center;
  border-radius: 3px;
  color: white;
  padding: 1px;
  border-top: 3px solid #186ead;
  border-bottom: 3px solid #186ead;
  background-color: rgba(0, 0, 0, 0.4);
`;
const PageNumberLi = styled.li`
  display: inline-block;
  font-size: 17px;
  font-weight: 600;
  padding: 5px;
  border-radius: 5px;
  width: 25px;
  &:hover {
    cursor: pointer;
    color: white;
    background-color: #263a6c;
  }
  &:focus::after {
    color: white;
    background-color: #263a6c;
  }
`;
const PageNumberSpan = styled.div`
  &:hover::after {
  }
  &:focus::after {
    border-radius: 100%;
    color: white;
    background-color: #263a6c;
  }
`;
function Pagination({ postsPerPage, totalPosts, paginate }: PaginationProps) {
  const pageNumbers: number[] = [];
  for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
    pageNumbers.push(i);
  }
  return (
    <div>
      <div>
        <PageNumberUl>
          {pageNumbers.map((number) => (
            <PageNumberLi key={number}>
              <PageNumberSpan onClick={() => paginate(number)}>{number}</PageNumberSpan>
            </PageNumberLi>
          ))}
        </PageNumberUl>
      </div>
    </div>
  );
}

export default Pagination;
