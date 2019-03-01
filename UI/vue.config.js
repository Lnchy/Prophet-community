// vue.config.js
module.exports = {
    devServer: {
        disableHostCheck: true,
        port: 8080,
        hot: true,
        overlay: {
            warnings: false,
            errors: true
        },
        open: true,
        proxy: {
            '/api': {
                target: 'http://localhost:8090', // 你要代理的域名和端口号，要加上http
                ws: false,
                changeOrigin: true,
                pathRewrite: {
                    '^/api': '/'
                }
            }
        }
    }
};