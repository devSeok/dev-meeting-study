export interface SendMessageType {
  email: string;
  content: string;
}

export enum MESSAGE_TYPE {
  MESSAGE_SEND = 'MESSAGE_SEND',
  MESSAGE_LIST = 'MESSAGE_LIST',
}
