'use strict'
// Template version: 1.3.1
// see http://vuejs-templates.github.io/webpack for documentation.

const path = require('path')

module.exports = {
    lintOnSave: false,
    devServer: {
        proxy: {
            '/logHom': {
                target: 'http://127.0.0.1:8080',
                changeOrigin: true, //是否跨域
                bypass: function(req, res, proxyOptions) {
                    if (req.headers.accept.indexOf("html") !== -1) {
                        console.log("Skipping proxy for browser request.");
                        return "/index.html";
                    }
                },
                pathRewrite: {
                    '^/logHom': '/'
                }
            }
        },
        // Various Dev Server settings
        host: '0.0.0.0', // can be overwritten by process.env.HOST
        port: 8080, // can be overwritten by process.env.PORT, if port is in use, a free one will be determined
        open: true,
        overlay: {
            warnings: false,
            errors: false
        },
        watchOptions: {
          aggregateTimeout: 300,
          poll: 1000
        }
    }
}