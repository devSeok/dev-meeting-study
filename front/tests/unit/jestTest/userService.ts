const data = require("./data");

module.exports = {
  findAll() {
    return data.users;
  },
  create(user: any) {
    data.users.push(user);
  },
  destroy(id: any) {
    data.users.splice(
      data.users.findIndex((user: any) => user.id === id),
      1
    );
  },
  update(id: any, user: any) {
    data.users[data.users.findIndex((user: any) => user.id === id)] = user;
  },
};
