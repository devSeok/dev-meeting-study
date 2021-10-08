import React from 'react';
import { useDispatch, useSelector } from 'react-redux';
import { listMessage, message } from '../ToolKit/user';

function MessageListView() {
  const Dispatch = useDispatch();
  const messageData = useSelector(message);

  const testSubject = (e: any) => {
    Dispatch(listMessage(''));
  };
  // console.log('testSubject', testSubject);
  //@ts-ignore
  console.log('messageData', messageData.payload.payload.data);

  return (
    <div>
      <button onClick={testSubject}>hi</button>
    </div>
  );
}
export default MessageListView;