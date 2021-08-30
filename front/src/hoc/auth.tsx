import React, { useEffect } from 'react';
import { useHistory } from 'react-router-dom';
import { useDispatch } from 'react-redux';
import { checkToken } from '../_actions/user_actions';

export default function auth(SpecificComponent: React.FC, option: boolean | null) {
  function AuthenticationCheck() {
    const dispatch = useDispatch();
    const history = useHistory();
    useEffect(() => {
      const check = async () => {
        const response = await dispatch(checkToken());

        console.log('Res', response);
        // response가 항상 true를 리턴한다고 ts에러 발생 해서 임시로 ignore 사용
        // @ts-ignore
        if (response) {
          if (option === false) {
            history.push('/');
          }
        } else {
          if (option) {
            history.push('/login');
          }
        }
      };
      check();
    }, []);
    return <SpecificComponent />;
  }
  return AuthenticationCheck;
}
