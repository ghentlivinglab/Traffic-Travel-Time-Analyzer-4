module.exports = {
    "env": {
        "browser": true,
        "es6": true
    },
    "extends": "eslint:recommended",
    "rules": {
        "indent": [2, 4, {"SwitchCase": 1}],
        "linebreak-style": [
            "error",
            "unix"
        ],
        "quotes": [
            "error",
            "double"
        ],
        "semi": [
            "error",
            "always"
        ]
    },
    "globals": {
        "$": false,
        "verkeer": false,
        "moment": false,
        "jQuery": false,
        "Highcharts": false
    }
};