import { createAsyncThunk, createSlice } from '@reduxjs/toolkit';
import { deleteMessages, listMessages, readMessages, sendMessages } from '../API';
import { PayloadFailType, PayloadSuccessType, ResSendMessage } from './axiosType';
import { MESSAGE_TYPE, SendMessageType } from './MessageTypes';
import { ReducerType } from '../rootReducer';

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

export const listMessage = createAsyncThunk('listMessage', async (arg, { rejectWithValue }: any) => {
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

// 메세지 조회
export const readMessage = createAsyncThunk('readMessage', async (id: number, { rejectWithValue }: any) => {
  console.log('readMessage', id);
  try {
    const { data }: any = await readMessages(id);
    console.log('data', data);

    return {
      type: MESSAGE_TYPE.MESSAGE_READ,
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

// 메세지 삭제
export const deleteMessage = createAsyncThunk('deleteMessage', async (id: number, { rejectWithValue }: any) => {
  try {
    const { data }: any = await deleteMessages(id);
    return {
      type: MESSAGE_TYPE.MESSAGE_DELETE,
      payload: data,
    };
  } catch (err: any) {
    const error: PayloadFailType = err.response.data;
    const messageObj = {
      ...error,
      type: MESSAGE_TYPE.MESSAGE_DELETE,
    };
    return rejectWithValue(messageObj);
  }
});

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
const messages = createSlice({
  name: 'message',
  initialState: State,
  reducers: {},
  extraReducers: (builder) => {
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
    // 메세지 읽음 로딩
    builder.addCase(readMessage.pending, (state) => {
      state.status = 'loading';
    });
    // 메세지 읽음 성공
    builder.addCase(readMessage.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      state.message = { type, payload };
      console.log(payload);
    });
    // 메세지 읽음 실패
    builder.addCase(readMessage.rejected, (state, { type, payload }) => {
      state.status = 'failed';
      state.message = { type, payload };
    });
    // 메세지 삭제 로딩
    builder.addCase(deleteMessage.pending, (state) => {
      state.status = 'loading';
    });
    // 메세지 삭제 성공
    builder.addCase(deleteMessage.fulfilled, (state, { type, payload }) => {
      state.status = 'success';
      state.message = { type, payload };
      console.log(payload);
      // state.message.filter((list: any) => list.id !== payload);
    });
    // 메세지 삭제 실패
    builder.addCase(deleteMessage.rejected, (state, { type, payload }) => {
      state.status = 'failed';
      state.message = { type, payload };
    });
  },
});

export const userStatus = (state: ReducerType) => state.message.user;
export const auth = (state: ReducerType) => state.message.auth.payload;
export const message = (state: ReducerType) => state.message.message;
export default messages.reducer;
