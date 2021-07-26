describe("group1", () => {
  test("1 is 1", () => {
    expect(1).toBe(1);
  });

  function getUser(id) {
    return {
      id,
      email: `user${id}@test.com`,
    };
  }

  test("return a user object", () => {
    expect(getUser(1)).toEqual({
      id: 1,
      email: `user1@test.com`,
    });
  });

  test("number 0 is falsy but string 0 is truthy", () => {
    expect(0).toBeFalsy();
    expect("0").toBeTruthy();
  });

  test("array", () => {
    const colors = ["Red", "Yellow", "Blue"];
    expect(colors).toHaveLength(3);
    expect(colors).toContain("Yellow");
    expect(colors).not.toContain("Green");
  });

  test("string", () => {
    expect(getUser(1).email).toBe("user1@test.com");
    expect(getUser(1).email).toMatch(/.*test.com$/);
  });

  function getUser2(id) {
    if (id <= 0) throw new Error("Invalid ID");
    return {
      id,
      email: "user${id}@test.com",
    };
  }

  test("throw when id is non negative", () => {
    //() => 검증대상을 함수로 한번 감싸줘야 함.
    // 감싸주지 않으면 실제로 예외 발생했다고 간주되어 실패하게 됨.
    expect(() => getUser2(-1)).toThrow();
    expect(() => getUser2(-1)).toThrow("Invalid ID");
  });
});

describe("group2", () => {
  //비동기 테스트//
  // function fetchUser(id,cb) {
  //   setTimeout(() => {
  //     console.log("wait 0.1 sec.");
  //     const user = {
  //       id:id,
  //       name:"User" + id,
  //       email: id + "@test.com"
  //     };
  //     cb(user);
  //   }, 100);
  // }

  // test("fetch a user", (done) => {
  //   fetchUser(2, (user) => {
  //     expect(user).toEqual({
  //       id: 1,
  //       name: "User1",
  //       email: "1@test.com",
  //     });
  //     done();
  //   });
  // });
  function fetchUser(id) {
    return new Promise((resolve) => {
      setTimeout(() => {
        console.log("wait 0.1 sec.");
        const user = {
          id: id,
          name: "User" + id,
          email: id + "@test.com",
        };
        resolve(user);
      }, 100);
    });
  }

  test("fetch a user", () => {
    return fetchUser(1).then((user) => {
      expect(user).toEqual({
        id: 1,
        name: "User1",
        email: "1@test.com",
      });
    });
  });

  test("fetch a user", async () => {
    const user = await fetchUser(1);
    expect(user).toEqual({
      id: 1,
      name: "User1",
      email: "1@test.com",
    });
  });

  function fetchUser2(id, cb) {
    setTimeout(() => {
      console.log("wait 0.1 sec.");
      const user = {
        id: id,
        name: "User" + id,
        email: id + "@test.com",
      };
      cb(user);
    }, 100);
  }

  test("fetch a user", (done) => {
    fetchUser2(1, (user) => {
      expect(user).toEqual({
        id: 1,
        name: "User1",
        email: "1@test.com",
      });
      done();
    });
  });
});

describe("group3", () => {
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const userService = require("./jestTest/userService");
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const data = require("./jestTest/data");
  test("find all users", () => {
    // data.users.push(
    //    {id: 1, email: "users1@test.com"},
    //    {id: 2, email: "users2@test.com"},
    //    {id: 3, email: "users3@test.com"}
    // );

    const users = userService.findAll();

    expect(users).toHaveLength(3);
    expect(users).toContainEqual({ id: 1, email: "users1@test.com" });
    expect(users).toContainEqual({ id: 2, email: "users2@test.com" });
    expect(users).toContainEqual({ id: 3, email: "users3@test.com" });
  });

  test("create a user", () => {
    const user = { id: "1", email: "user4@test.com" };

    userService.create(user);

    expect(data.users).toHaveLength(4);
    expect(data.users).toContainEqual(user);
  });

  afterEach(() => {
    //위치상관없는듯.
    data.users.splice(0);
  });

  test("destory a user", () => {
    // data.users.push(
    //    {id: 1, email: "users1@test.com"},
    //    {id: 2, email: "users2@test.com"},
    //    {id: 3, email: "users3@test.com"}
    // );

    const id = 3;
    const user = data.users.find((user) => user.id === id);

    userService.destroy(id);

    expect(data.users).toHaveLength(2);
    expect(data.users).not.toContainEqual(user);
  });

  beforeEach(() => {
    //위치상관없는듯.
    data.users.push(
      { id: 1, email: "users1@test.com" },
      { id: 2, email: "users2@test.com" },
      { id: 3, email: "users3@test.com" }
    );
  });

  beforeAll(() => {
    console.log("시작~~~");
  });
  afterAll(() => {
    console.log("끝~~~");
  });
});

//only 체크된것만 실행
//skip 체크된것만 실행 안함

describe("group4", () => {
  test("I am a mock!", () => {
    const mockFn = jest.fn();

    //인자넘기기
    // mockFn();
    // mockFn(1);
    // mockFn("a");
    // mockFn([1,2],{a:"b"});

    //값 반환하기
    //mockFn.mockReturnValue("I am a mock!");
    //console.log(mockFn());

    //비동기 목 mockReturnValue, mockResolvedValue 둘중하나만 사용가능한듯?
    // mockFn.mockReturnValue("I am a mock!");
    // mockFn.mockResolvedValue("I will be a mock!");
    // mockFn().then((result) =>{
    //   console.log(result);
    // });

    //목 함수 구현
    // mockFn.mockImplementation((name) => `I am ${name}!`);
    // console.log(mockFn("Dale")); // I am Dale!

    // mockFn("a");
    // mockFn(["b","c"]);

    // expect(mockFn).toBeCalledTimes(2);
    // expect(mockFn).toBeCalledWith("a");
    // expect(mockFn).toBeCalledWith(["b","c"]);

    // const calculator = {
    //   add: (a,b) => a + b,
    // }

    // const spyFn = jest.spyOn(calculator,"add");

    // const result =  calculator.add(2,3);

    // expect(spyFn).toBeCalledTimes(1);
    // expect(spyFn).toBeCalledWith(2,3);
    // expect(result).toBe(5);
  });

  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const axios = require("axios");
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const userService = require("./jestTest/apiTest");

  // test("findOne returns a user", async () => {
  //   const user = await userService.findOne(1);
  //   expect(user).toHaveProperty("id", 1);
  //   expect(user).toHaveProperty("name", "Leanne Graham");
  // });

  // test("findOne fetches data from the API endpoint", async () => {
  //   const spyGet = jest.spyOn(axios,"get");
  //   await userService.findOne(1);
  //   expect(spyGet).toHaveBeenCalledTimes(1);
  //   expect(spyGet).toHaveBeenCalledWith(`https://jsonplaceholder.typicode.com/users/1`);
  // });

  test("findOne returns what axios get returns", async () => {
    axios.get = jest.fn().mockResolvedValue({
      data: {
        id: 1,
        name: "Dale Seo",
      },
    });

    const user = await userService.findOne(1);
    expect(user).toHaveProperty("id", 1);
    expect(user).toHaveProperty("name", "Dale Seo");
  });
});

import { register, deregister } from "./jestTest/userServiceMd";
import * as messageService from "./jestTest/messageService";
describe("group5-1", () => {
  // Object.assign(messageService.sendEmail, jest.fn());
  // Object.assign(messageService.sendSMS, jest.fn());

  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  messageService.sendEmail = jest.fn();
  // eslint-disable-next-line @typescript-eslint/ban-ts-comment
  // @ts-ignore
  messageService.sendSMS = jest.fn();

  const sendEmail = messageService.sendEmail;
  const sendSMS = messageService.sendSMS;

  beforeEach(() => {
    sendEmail.mockClear();
    sendSMS.mockClear();
  });

  const user = {
    email: "test@email.com",
    phone: "012-345-6789",
  };

  test("register sends messages", () => {
    //변경된 messageService를 사용하는듯.
    register(user);

    expect(sendEmail).toHaveBeenCalledTimes(1);
    expect(sendEmail).toHaveBeenCalledWith(
      user.email,
      "회원 가입을 환영합니다!"
    );

    expect(sendSMS).toHaveBeenCalledTimes(1);
    expect(sendSMS).toHaveBeenCalledWith(user.phone, "회원 가입을 환영합니다!");
  });

  test("deregister sends messages", () => {
    //변경된 messageService를 사용하는듯.
    deregister(user);

    expect(sendEmail).toHaveBeenCalledTimes(1);
    expect(sendEmail).toHaveBeenCalledWith(user.email, "탈퇴 처리 되었습니다.");

    expect(sendSMS).toHaveBeenCalledTimes(1);
    expect(sendSMS).toHaveBeenCalledWith(user.phone, "탈퇴 처리 되었습니다.");
  });
});
import { sendEmail, sendSMS } from "./jestTest/messageService";
describe("gropu5-2", () => {
  //jest.mock()을 이용한 모듈 모킹
  jest.mock("./jestTest/messageService");
  // const mockComposeMessage = jest.fn();
  // jest.mock("./jestTest/messageService", () => {
  //   return jest.fn().mockImplementation(() => ({
  //     composeMessage: mockComposeMessage,
  //   }));
  // });

  beforeEach(() => {
    sendEmail.mockClear();
    sendSMS.mockClear();
  });

  const user = {
    email: "test@email.com",
    phone: "012-345-6789",
  };

  test("register sends messages", () => {
    //변경된 messageService를 사용하는듯.
    register(user);

    expect(sendEmail).toHaveBeenCalledTimes(1);
    expect(sendEmail).toHaveBeenCalledWith(
      user.email,
      "회원 가입을 환영합니다!"
    );

    expect(sendSMS).toHaveBeenCalledTimes(1);
    expect(sendSMS).toHaveBeenCalledWith(user.phone, "회원 가입을 환영합니다!");
  });

  test("deregister sends messages", () => {
    //변경된 messageService를 사용하는듯.
    deregister(user);

    expect(sendEmail).toHaveBeenCalledTimes(1);
    expect(sendEmail).toHaveBeenCalledWith(user.email, "탈퇴 처리 되었습니다.");

    expect(sendSMS).toHaveBeenCalledTimes(1);
    expect(sendSMS).toHaveBeenCalledWith(user.phone, "탈퇴 처리 되었습니다.");
  });
});

describe("group5-3", () => {
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const axios = require("axios");
  // eslint-disable-next-line @typescript-eslint/no-var-requires
  const userService = require("./jestTest/apiTest");

  test("findOne fetches data from the API endpoint", async () => {
    const spyGet = jest.spyOn(axios, "get");
    spyGet.mockClear(); //위에서 사용했기때문에 2로 테스트값이 나옴. 클리어처리.
    await userService.findOne(1);
    expect(spyGet).toHaveBeenCalledTimes(1);
    expect(spyGet).toHaveBeenCalledWith(
      "https://jsonplaceholder.typicode.com/users/1"
    );
  });

  test("findOne returns what axios get returns", async () => {
    axios.get = jest.fn().mockResolvedValue({
      data: {
        id: 1,
        name: "Dale Seo",
      },
    });
    const user = await userService.findOne(1);
    expect(user).toHaveProperty("id", 1);
    expect(user).toHaveProperty("name", "Dale Seo");
  });
});
// const axios = require("axios"); //describe에 import하면 에러 위에 올리면 성공 대신 다른곳에서 참조하는듯..?
// const userService = require("./jestTest/apiTest");
// jest.mock("axios");
// describe.only('group5-4', () => {
//   test('findOne fetches data from the API endpoint and returns what axios get returns ', async () => {
//     axios.get.mockResolvedValue({
//       data: {
//         id: 1,
//         name: "Dale Seo",
//       },
//     });

//     const user = await userService.findOne(1);

//     expect(user).toHaveProperty("id", 1);
//     expect(user).toHaveProperty("name", "Dale Seo");
//     expect(axios.get).toHaveBeenCalledTimes(1);
//     expect(axios.get).toHaveBeenCalledWith(
//       `https://jsonplaceholder.typicode.com/users/1`
//     );

//   });

// });

//https://www.daleseo.com/jest-fn-spy-on/
//https://www.js2uix.com/frontend/jest-study-step3/
//https://velog.io/@noyo0123/jest-test%EC%97%90%EC%84%9C-import-%EB%A5%BC-%EB%AA%BB-%EC%93%B0%EB%84%A4%EC%9A%94-pik230v1hp
//https://velog.io/@haebin/React-regeneratorRuntime-is-not-defined-%EC%97%90%EB%9F%AC-%ED%95%B4%EA%B2%B0

/*
01. display

02. overflow

03. float

04. position

05. z-index

06. width & height

07. margin & padding

08. border

09. font

10. background

11. etc (ex. opacity, transform, transition, animation, text 관련 등)
출처: https://rgy0409.tistory.com/3051 [친절한효자손 취미생활]
*/
