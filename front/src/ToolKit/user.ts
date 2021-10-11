import { createSlice, createAsyncThunk, AsyncThunk } from '@reduxjs/toolkit';
import { listMessages, login_user, register_user, reissueToken, sendMessages } from '../API/index';
import { RegisterType, LoginType, Token, TokenCheck, USER_TYPE } from './userType';
import { ResRegister, ResLogin, PayloadSuccessType, PayloadFailType, ResSendMessage } from './axiosType';
import { ReducerType } from '../rootReducer';
import { MESSAGE_TYPE, SendMessageType } from './MessageTypes';

// 메세지 보내기
export const sendMessage = createAsyncThunk('sendMessage', async (message: SendMessageType, { rejectWithValue }) => {
  try {
    const { data }: PayloadSuccessType<ResSendMessage> = await sendMessages(message);
    console.log('data', data);
    return {
      type: MESSAGE_TYPE.MESSAGE_SEND,
      payload: data,
    };
  } catch (err: any) {
    const error: PayloadFailType = err.response.data;
    const messageObj = {
      ...error,
      type: MESSAGE_TYPE.MESSAGE_SEND,
    };
    console.log('Error:messageObj', messageObj);
    return rejectWithValue(messageObj);
  }
});

// 메세지 리스트
export const listMessage = createAsyncThunk('listMessage', async ({ rejectWithValue }: any) => {
  console.log('listMessage', listMessage);

  try {
    const { data }: any = await listMessages();
    console.log('listMessages', data);
    return {
      type: MESSAGE_TYPE.MESSAGE_LIST,
      payload: data,
    };
  } catch (err: any) {
    const error: PayloadFailType = err.response.data;
    const messageObj = {
      ...error,
      type: MESSAGE_TYPE.MESSAGE_LIST,
    };
    return rejectWithValue(messageObj);
  }
});

export const register = createAsyncThunk('user/REGISTER', async (user: RegisterType, { dispatch, rejectWithValue }) => {
  try {
    const { data }: PayloadSuccessType<ResRegister> = await register_user(user);

    return {
      type: USER_TYPE.USER_REGISTER,
      payload: data,
    };
  } catch (err: any) {
    const error: PayloadFailType = err.response.data;

    const obj = {
      ...error,
      type: USER_TYPE.USER_REGISTER,
    };

    // errorHandler(err.response.data.code);
    // console.log('Err', err.response.data.code);
    console.log(obj);

    return rejectWithValue(obj);
  }
});

export const login = createAsyncThunk('LOGIN', async (user: LoginType, { rejectWithValue }) => {
  try {
    const { data }: PayloadSuccessType<ResLogin> = await login_user(user);

    saveLocalStorage(data.data);

    return {
      type: USER_TYPE.USER_LOGIN,
      payload: data,
    };
  } catch (err: any) {
    const error: PayloadFailType = err.response.data;

    const obj = {
      ...error,
      type: USER_TYPE.USER_LOGIN,
    };
    return rejectWithValue(obj);
  }
});

export const checkToken = createAsyncThunk('CHECK_TOKEN', async (arg, { rejectWithValue }) => {
  const obj: TokenCheck = {
    type: USER_TYPE.USER_CHECK_TOKEN,
    success: false,
    message: '',
  };
  try {
    const date = new Date();
    const expiresIn = parseInt(localStorage.getItem('accessTokenExpiresIn') as string);
    const now = date.getTime();
    const diffTime = expiresIn - now;
    // const diffTime = 50000;

    window.addEventListener('storage', (e) => {
      if (e.isTrusted) {
        // accessTokenExpiresIn값 변경
        localStorage.setItem('accessTokenExpiresIn', '9999999999999999999999999999');
        // 페이지 리로드
        window.location.reload();
      }
    });

    if (expiresIn) {
      // 로컬 스토리지에 accessTokenExpiresIn 시간 조작하면 인증된 토큰으로 나옴
      if (diffTime > 1800000) {
        obj.message = '비정상적인 토큰';
        return obj;
      }
      if (diffTime < 59405 && diffTime > 0) {
        const accessToken = localStorage.getItem('accessToken') as string;
        const refreshToken = localStorage.getItem('refreshToken') as string;
        const { data }: PayloadSuccessType<ResLogin> = await reissueToken({ accessToken, refreshToken });

        saveLocalStorage(data.data);

        obj.success = true;
        obj.type = USER_TYPE.USER_REISSUE;
        obj.message = '토큰 갱신 성공';
        return obj;
      }
      if (diffTime <= 0) {
        logout();
        obj.message = '만료된 토큰';
        return obj;
      }
    } else {
      obj.message = '존재하지 않은 토큰';
      return obj;
    }
    obj.success = true;
    obj.message = '유효한 토큰';
    return obj;
  } catch (err: any) {
    const error: PayloadFailType = err.response.data;

    console.log('err', error);

    obj.err = error;
    return rejectWithValue(obj);
  }
});

const saveLocalStorage = (token: Token) => {
  localStorage.setItem('accessToken', token.accessToken);
  localStorage.setItem('refreshToken', token.refreshToken);
  localStorage.setItem('accessTokenExpiresIn', JSON.stringify(token.accessTokenExpiresIn));
};

interface AuthType {
  type: string;
  payload: {
    type: string;
    success: boolean;
    message: string;
  };
}

export interface InitialState {
  user: {};
  status: string;
  auth: AuthType;
  message: any;
}

const State: InitialState = {
  user: {},
  status: '',
  auth: {
    type: '',
    payload: {
      type: '',
      success: false,
      message: '',
    },
  },
  message: {
    type: '',
    payload: {
      type: '',
      message: '',
      number: 0,
      payload: {
        message: '',
        status: 0,
        data: [],
      },
    },
  },
};

const user = createSlice({
  name: 'user',
  initialState: State,
  reducers: {
    // 로그아웃
    logout(state) {
      alert('로그아웃 되었습니다.');
      console.log('로그아웃');

      localStorage.clear();
      state.user = { type: USER_TYPE.USER_LOGOUT, payload: '로그아웃되었습니다.' };
    },
  },
  extraReducers: (builder) => {
    // 회원가입
    builder.addCase(register.pending, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(register.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      state.user = { type, payload };
    });
    builder.addCase(register.rejected, (state, { type, payload }) => {
      state.status = 'failed';
      state.user = { type, payload };
    });

    // 로그인
    builder.addCase(login.pending, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(login.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      state.user = { type, payload };
    });
    builder.addCase(login.rejected, (state, { type, payload }) => {
      state.status = 'failed';
      state.user = { type, payload };
    });

    // 토큰 체크
    builder.addCase(checkToken.pending, (state) => {
      state.status = 'loading';
      // state.auth = {};
    });
    builder.addCase(checkToken.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      const data = payload as { type: string; success: boolean; message: string };
      state.auth.type = type;
      state.auth.payload = data;
    });
    builder.addCase(checkToken.rejected, (state, { type, payload }) => {
      const data = payload as AuthType;
      state.status = 'failed';
      state.auth.type = type;
      state.auth.payload = data.payload;
    });

    // 메세지 로딩
    builder.addCase(sendMessage.pending, (state) => {
      state.status = 'loading';
    });
    // 메세지 성공
    builder.addCase(sendMessage.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      state.message = { type, payload };
      console.log(payload);
    });
    // 메세지 실패
    builder.addCase(sendMessage.rejected, (state, { type, payload }) => {
      state.status = 'failed';
      state.message = { type, payload };
    });
    // 메세지 리스트 로딩
    builder.addCase(listMessage.pending, (state) => {
      state.status = 'loading';
    });
    // 메세지 리스트 성공
    builder.addCase(listMessage.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      state.message = { type, payload };
      console.log(payload);
    });
    // 메세지 리스트 실패
    builder.addCase(listMessage.rejected, (state, { type, payload }) => {
      state.status = 'failed';
      state.message = { type, payload };
    });
  },
});

export const { logout } = user.actions;
export const userStatus = (state: ReducerType) => state.users.user;
export const auth = (state: ReducerType) => state.users.auth.payload;
export const message = (state: ReducerType) => state.users.message;
export default user.reducer;
