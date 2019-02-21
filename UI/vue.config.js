// vue.config.js
module.exports = {
    devServer: {
        port: 8080,
        hot: true,
        hotOnly: true,
        lazy: false,
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
                secure: false,
                pathRewrite: {
                    '^/api': '/'
                }
            }
        }
    },
    runtimeCompiler: true
};