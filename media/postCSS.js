console.log("CSS changed. Processing...");

var postcss = require('postcss');
var fs = require('fs');

var postcss_media_variables = require('postcss-media-variables');
var postcss_partial_import = require('postcss-partial-import');
var postcss_css_variables = require('postcss-css-variables');
var postcss_nested = require('postcss-nested');
var postcss_discard_comments = require('postcss-discard-comments');
var postcss_autoprefixer = require('autoprefixer');

var options = {
    from: 'css/style.css',
    to: 'theMetaCityMedia/static/css/style.css',
    map: { inline: true }
};

var css = fs.readFileSync("css/style.css", "utf8");

postcss([
        postcss_partial_import,
        postcss_media_variables,
        postcss_css_variables,
        postcss_media_variables,
        postcss_nested,
        postcss_discard_comments,
        postcss_autoprefixer
    ])
    .process(css, options)
    .then(function (result) {
        fs.writeFileSync('css/tmc-style.css', result.css);
    });

console.log("CSS processed");
