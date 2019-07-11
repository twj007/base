require.config({
    paths: {
        jquery: '/static/js/lib/jquery/jquery-1.11.3.min',

    },
    map: {
        '*': {
            'css': '/static/js/lib/require-css/css'
        }
    },
    shim: {
        bootstrap: {
            deps: ['css!/webjars/bootstrap/3.3.5/css/bootstrap.min.css',
                'css!/webjars/font-awesome/4.5.0/css/font-awesome.min.css',
                'css!/webjars/ionicons/2.0.1/css/ionicons.min.css',
                'jquery']
        },
        bootstrapValidator: {
            deps: ['css!/webjars/bootstrapvalidator/0.5.3/css/bootstrapValidator.css', 'jquery']
        },
        bootstrapTable: {
            deps: ['css!/webjars/bootstrap-table/1.9.1/bootstrap-table.css', 'jquery']
        },
        'crypto.aes': {
            deps: [
                'crypto',
                '/webjars/cryptojs/3.1.2/components/cipher-core.js',
                '/webjars/cryptojs/3.1.2/components/mode-ecb.js'
            ]
        }
        // ecb: {
        //     deps: ['crypto-js']
        // }
    }
});