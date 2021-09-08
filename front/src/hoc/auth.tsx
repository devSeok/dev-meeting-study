import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { checkToken } from '../ToolKit/user';
import { logout } from '../ToolKit/user';

export default function auth(SpecificComponent: React.FC, option: boolean | null) {
  function AuthenticationCheck() {
    const dispatch = useDispatch();
    const history = useHistory();
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
            if (res.payload.message === '만료된 토큰') {
              dispatch(logout());
            }
          }
        });
      };
      check();
    }, []);
    return <SpecificComponent />;
  }
  return AuthenticationCheck;
}
