function repeat(word: any, times = 2) {
  const words: any = [];
  for (let i = 0; i < times; i++) {
    words.push(word);
  }
  return words;
}

export default repeat;
