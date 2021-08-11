interface Payload {
  type: string;
  payload: object;
}

function action(state = {}, action: Payload) {
  switch (action.type) {
    default:
      return state;
  }
}

export default action;
