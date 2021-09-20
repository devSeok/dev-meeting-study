import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch, useSelector } from 'react-redux';
import { checkToken, userStatus, logout } from '../ToolKit/user';

export default function auth(SpecificComponent: React.FC, option: boolean | null) {
  function AuthenticationCheck() {
    const dispatch = useDispatch();
    const history = useHistory();
    const user = useSelector(userStatus);

    useEffect(() => {
      const check = async () => {
        // 'AsyncThunkAction<TokenCheck, void, {}>' 형식에 'then' 속성이 없습니다.ts(2339)
        // @ts-ignore
        await dispatch(checkToken()).then((res: { payload: { message: string; success: boolean } }) => {
          if (res.payload.success) {
            if (option === false) {
              history.push('/');
            }
          } else {
            if (option) {
              history.push('/login');
            }
            if (res.payload.message === '만료된 토큰' || res.payload.message === '비정상적인 토큰') {
              dispatch(logout());
            }
          }
        });
      };
      check();
    }, [user]);
    return <SpecificComponent />;
  }
  return AuthenticationCheck;
}
