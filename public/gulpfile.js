var gulp = require('gulp');
var nodemon = require('gulp-nodemon');
var less = require('gulp-less');
var uglify = require('gulp-uglify');
var csso = require('gulp-csso');
var ngAnnotate = require('gulp-ng-annotate');
var jshint = require('gulp-jshint');
var useref = require('gulp-useref');
var rev = require('gulp-rev');
var revReplace = require('gulp-rev-replace');
var filter = require('gulp-filter');
var del = require('del');
var addSrc = require('gulp-add-src');
var templateCache = require('gulp-angular-templatecache');
var concat = require('gulp-concat');
var autoprefixer = require('gulp-autoprefixer');
var order = require('gulp-order');
var browserSync = require('browser-sync');

gulp.task('serve', ['less'], function() {
	nodemon({
		script: 'server.js',
		env: {
			'NODE_ENV': 'development'
		}
	});
	gulp.watch(['public/**/*.js', 'public/**/*.html'], browserSync.reload);
	gulp.watch(['public/less/*.less'], ['less']);

	browserSync.init(null, {
		proxy: "localhost:8888"
	});

});

gulp.task('serveDist', ['build'], function() {
	nodemon({
		script: 'server.js',
		env: {
			'NODE_ENV': 'production'
		}
	});
});


gulp.task('less', function() {
	gulp.src('public/less/site.less')
		.pipe(less())
		.pipe(autoprefixer())
		.pipe(gulp.dest('public/less'))
		.pipe(browserSync.stream());
});

gulp.task('clean', function() {
	del.sync(['dist', '.tmp']);
});

gulp.task("angular-templates", function() {
	return gulp.src(['public/**/*.html', '!public/bower_components/**', '!public/index.html'])
		.pipe(templateCache('templates.js', {
			module: "app"
		}))
		.pipe(gulp.dest(".tmp/"));
});

gulp.task("build", ['clean'], function() {
	//copy assets that will not be build in the minification process
	gulp.src(['public/**/assets/**', 'public/favicon.ico']).pipe(gulp.dest('dist/public'));

	var jsFilter = filter("**/*.js", {
		restore: true
	});
	var appJsFilter = filter(['**/app.js'], {
		restore: true
	});
	var cssFilter = filter(["**/*.css"], {
		restore: true
	});

	var userefAssets = useref.assets();

	return gulp.src("public/index.html")
		.pipe(userefAssets) // Concatenate with gulp-useref
		.pipe(jsFilter)
		.pipe(ngAnnotate())
		.pipe(uglify())
		.pipe(jsFilter.restore)
		.pipe(appJsFilter)
		.pipe(addSrc('.tmp/templates.js'))
		.pipe(order(['public/js/app.js', '.tmp/templates.js']))
		.pipe(concat('js/app.js'))
		.pipe(appJsFilter.restore)
		.pipe(cssFilter)
		.pipe(csso())
		.pipe(cssFilter.restore)
		.pipe(rev())
		.pipe(userefAssets.restore())
		.pipe(useref())
		.pipe(revReplace())
		.pipe(gulp.dest('dist/public'));
});


gulp.task('test', function() {
	gulp.src(['public/**/assets/**', 'public/favicon.ico']).pipe(gulp.dest('dist/public'));
})

gulp.task('lint', function() {
	gulp.src('public/app/**/*.js')
		.pipe(jshint())
		.pipe(jshint.reporter('jshint-stylish'));
});