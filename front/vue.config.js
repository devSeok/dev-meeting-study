//vue.config.js
module.exports = {
  css: {
    loaderOptions: {
      sass: {
        prependData: `
                    @import "@/assets/styles/setup/_base.scss";
                `,
      },
    },
  },
};
