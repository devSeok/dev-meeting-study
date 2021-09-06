import { combineReducers } from '@reduxjs/toolkit';
import users from './ToolKit/user';

const rootReducer = combineReducers({
  users,
});

export type ReducerType = ReturnType<typeof rootReducer>;

export default rootReducer;
