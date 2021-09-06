import { createSlice, createAsyncThunk } from '@reduxjs/toolkit';
import { login_user, register_user, reissueToken } from '../API/index';
import { RegisterType, LoginType, Token, TokenCheck, USER_TYPE } from './userType';
import { ResRegister, ResLogin, PayloadSuccessType, PayloadFailType } from './axiosType';

export const register = createAsyncThunk('REGISTER', async (user: RegisterType, { dispatch, rejectWithValue }) => {
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

const user = createSlice({
  name: 'user',
  initialState: {
    user: {},
    status: '',
  },
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
    builder.addCase(register.fulfilled, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(register.rejected, (state) => {
      state.status = 'loading';
      state.user = {};
    });

    // 로그인
    builder.addCase(login.pending, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(login.fulfilled, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(login.rejected, (state) => {
      state.status = 'loading';
      state.user = {};
    });

    // 토큰 체크
    builder.addCase(checkToken.pending, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(checkToken.fulfilled, (state) => {
      state.status = 'loading';
      state.user = {};
    });
    builder.addCase(checkToken.rejected, (state) => {
      state.status = 'loading';
      state.user = {};
    });
  },
});

export const { logout } = user.actions;

export default user.reducer;
