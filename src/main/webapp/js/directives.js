'use strict';
var importBillsDirectives = angular.module('importBillsDirectives', []);

importBillsDirectives.directive('dropZone', function () {
    return {
        scope: {
            action: "@",
            file : '=',
            fileName : '='            
        },
        link: function (scope, element, attrs) {
            console.log("Creating dropzone");

            // Autoprocess the form
            if (scope.autoProcess != null && scope.autoProcess == "false") {
                scope.autoProcess = false;
            } else {
                scope.autoProcess = true;
            }

            // Max file size
            if (scope.dataMax == null) {
                scope.dataMax = Dropzone.prototype.defaultOptions.maxFilesize;
            } else {
                scope.dataMax = parseInt(scope.dataMax);
            }

            // Message for the uploading
            if (scope.message == null) {
                scope.message = Dropzone.prototype.defaultOptions.dictDefaultMessage;
            }

            element.dropzone({
                url: scope.action,
                maxFilesize: scope.dataMax,
                paramName: "file",
                acceptedFiles: scope.mimetypes,
                maxThumbnailFilesize: scope.dataMax,
                dictDefaultMessage: scope.message,
                autoProcessQueue: scope.autoProcess,
                success: function (file, response) {
                	console.log(file);
                    if (scope.callBack != null) {
                        scope.callBack({response: response});
                    }
                }
            });
        }
    }
});

