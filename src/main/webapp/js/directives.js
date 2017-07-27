'use strict';
var importBillsDirectives = angular.module('importBillsDirectives', []).config( [
'$compileProvider',
function( $compileProvider )
{
$compileProvider.imgSrcSanitizationWhitelist(/^\s*(https?|file|blob|cdvfile):|data:image\//);
}
]);

