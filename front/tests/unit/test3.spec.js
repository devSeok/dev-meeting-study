import repeat from "./jestTest/snapShot";
describe("group6", () => {
  // test("repeats words three times", () => {
  //   expect(repeat("Test", 3)).toMatchInlineSnapshot(`"Test,Test,Test"`);
  // });

  test("repeats words three times", () => {
    expect(repeat("Test", 3)).toMatchSnapshot();
  });
});
