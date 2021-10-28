import { combineReducers } from '@reduxjs/toolkit';
import users from './ToolKit/user';
import message from './ToolKit/messages';

const rootReducer = combineReducers({
  users,
  message,
});

export type ReducerType = ReturnType<typeof rootReducer>;

export default rootReducer;
