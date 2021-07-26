describe("group5-4", () => {
  const axios = require("axios");
  const userService = require("./jestTest/apiTest");
  jest.mock("axios"); //import와 동일한 범위에 있어야함 범위의 상단으로 올려지기 때문
  test("findOne fetches data from the API endpoint and returns what axios get returns ", async () => {
    axios.get.mockResolvedValue({
      data: {
        id: 1,
        name: "Dale Seo",
      },
    });

    const user = await userService.findOne(1);

    expect(user).toHaveProperty("id", 1);
    expect(user).toHaveProperty("name", "Dale Seo");
    expect(axios.get).toHaveBeenCalledTimes(1);
    expect(axios.get).toHaveBeenCalledWith(
      `https://jsonplaceholder.typicode.com/users/1`
    );
  });
});
