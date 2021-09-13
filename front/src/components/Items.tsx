import React from 'react';
import { Item } from '../views/MainView';

interface ItemsProps {
  items: {
    id: number;
    title: string;
    type: string;
  }[];
}

function Items({ items }: ItemsProps) {
  return (
    <>
      {items.map((item: any) => {
        return (
          <Item key={item.id}>
            <div
              style={{
                position: 'absolute',
                left: '20px',
                bottom: '10px',
              }}
            >
              <h2 style={{ marginBottom: '10px' }}>{item.title}</h2>
              <span>{item.type}</span>
            </div>
          </Item>
        );
      })}
    </>
  );
}

export default Items;
