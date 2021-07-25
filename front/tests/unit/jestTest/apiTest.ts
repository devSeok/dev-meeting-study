// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-ignore
import axios from "axios";
const API_ENDPOINT = "https://jsonplaceholder.typicode.com";

module.exports = {
  findOne(id: any) {
    return axios
      .get(`${API_ENDPOINT}/users/${id}`)
      .then((response: any) => response.data);
  },
};
