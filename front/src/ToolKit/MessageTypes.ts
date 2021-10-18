export interface SendMessageType {
  email: string;
  content: string;
}
export interface ReadMessageType {
  id: number;
}

export enum MESSAGE_TYPE {
  MESSAGE_SEND = 'MESSAGE_SEND',
  MESSAGE_LIST = 'MESSAGE_LIST',
  MESSAGE_READ = 'MESSAGE_READ',
  MESSAGE_DELETE = 'MESSAGE_DELETE',
}
