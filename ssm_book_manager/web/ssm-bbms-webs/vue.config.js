// vue.config.js
const path = require('path')
function resolve (dir) {
  return path.join(__dirname, dir)
}

const port = 9527

module.exports = {
  pages: {
    index: {
      //入口
      entry: 'src/main.js',
    },
  },
  publicPath: '/',
  outputDir: 'dist',
  assetsDir: 'static',
  lintOnSave: process.env.NODE_ENV === 'development',
  productionSourceMap: false,
  devServer: {
    port: port,
    open: true,
    overlay: {
      warnings: false,
      errors: true
    },

    // history模式下的url会请求到服务器端，但是服务器端并没有这一个资源文件，就会返回404，所以需要配置这一项
    historyApiFallback: {
      index: '/index.html' //与output的publicPath
    },

/*
   pathRewrite如果不配置，去服务器拿数据的时候，5000端口服务器因为加上了前缀，会返回404.所以需要
   配置，当访问5000端口服务器时，会把前缀去掉。
   changeOrigin设置为true时，服务器收到的请求头中的host为：localhost:5000
   changeOrigin设置为false时，服务器收到的请求头中的host为：localhost:8080
   changeOrigin默认值为true
*/

    proxy: {
      [process.env.VUE_APP_BASE_API]: {
        target:'http://localhost:8080/',
        changeOrigin:true,
        pathRewrite:{
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    },

  },
  chainWebpack: config => {
    // 一个规则里的 基础Loader
    // svg是个基础loader
    const svgRule = config.module.rule('svg')

    // 清除已有的所有 loader。
    // 如果你不这样做，接下来的 loader 会附加在该规则现有的 loader 之后。
    svgRule.uses.clear()

    // 添加要替换的 loader
    svgRule
      .use('svg-sprite-loader')
      .loader('svg-sprite-loader')
      .options({
        symbolId: 'icon-[name]'
      })
  },
  // 当项目逐渐变大之后，文件与文件直接的引用关系会很复杂，这时候就需要使用alias 了
  // 改变src为@(import a from @/** */)
  configureWebpack: {
    // provide the app's title in webpack's name field, so that
    // it can be accessed in index.html to inject the correct title.
    /*name: name,*/
    resolve: {
      alias: {
        '@': resolve('src')
      }
    }
  }
}
