import { USER_TYPE, UserDispatchType } from '../_actions/type';

function action(state = {}, action: UserDispatchType) {
  switch (action.type) {
    case USER_TYPE.USER_REGISTER: {
      const { data, message } = action.payload;
      return { ...state, payload: { data, message }, type: action.type };
    }
    case USER_TYPE.USER_LOGIN: {
      const { data, message } = action.payload;
      return { ...state, payload: { data, message }, type: action.type };
    }
    case USER_TYPE.USER_LOGOUT: {
      return { ...state, payload: '로그아웃 되었습니다', type: action.type };
    }
    case USER_TYPE.USER_FAIL: {
      return { ...state, type: action.type };
    }
    default:
      return { ...state };
  }
}

export default action;
