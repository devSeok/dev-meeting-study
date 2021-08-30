import React from 'react';

import notImage from '../asset/image/404.png';

function NotFountView() {
  return (
    <div
      style={{
        width: '100%',
        height: '100vh',
        display: 'flex',
        justifyContent: 'center',
        alignItems: 'center',
        color: '#51aafe',
      }}
    >
      <div style={{ display: 'flex', flexDirection: 'column' }}>
        <img src={notImage} alt="" />
        <h2>이런 페이지는 확인할 수 없어요</h2>
      </div>
    </div>
  );
}

export default NotFountView;
